package com.example.demo.service;

import com.example.demo.dto.TrabalhoDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EntityMapper;
import com.example.demo.model.Pessoa;
import com.example.demo.model.Trabalho;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.repository.TrabalhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrabalhoService {

    private final TrabalhoRepository trabalhoRepository;
    private final PessoaRepository pessoaRepository;
    private final EntityMapper mapper;

    @Autowired
    public TrabalhoService(TrabalhoRepository trabalhoRepository, 
                          PessoaRepository pessoaRepository,
                          EntityMapper mapper) {
        this.trabalhoRepository = trabalhoRepository;
        this.pessoaRepository = pessoaRepository;
        this.mapper = mapper;
    }

    // Get all trabalhos
    public List<TrabalhoDTO> getAllTrabalhos() {
        List<Trabalho> trabalhos = trabalhoRepository.findAll();
        return mapper.toTrabalhoDTOList(trabalhos);
    }

    // Get trabalho by id
    public TrabalhoDTO getTrabalhoById(Long id) {
        Trabalho trabalho = trabalhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trabalho não encontrado com id: " + id));
        return mapper.toDTO(trabalho);
    }

    // Get trabalhos by pessoa id
    public List<TrabalhoDTO> getTrabalhosByPessoaId(Long pessoaId) {
        List<Trabalho> trabalhos = trabalhoRepository.findByPessoaId(pessoaId);
        return mapper.toTrabalhoDTOList(trabalhos);
    }

    // Create new trabalho for a pessoa
    @Transactional
    public TrabalhoDTO createTrabalho(TrabalhoDTO trabalhoDTO) {
        // Verify pessoa exists
        Pessoa pessoa = pessoaRepository.findById(trabalhoDTO.getPessoaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id: " + trabalhoDTO.getPessoaId()));
        
        Trabalho trabalho = mapper.toEntity(trabalhoDTO);
        trabalho.setPessoa(pessoa);
        trabalho = trabalhoRepository.save(trabalho);
        
        return mapper.toDTO(trabalho);
    }

    // Update existing trabalho
    @Transactional
    public TrabalhoDTO updateTrabalho(Long id, TrabalhoDTO trabalhoDTO) {
        Trabalho existingTrabalho = trabalhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trabalho não encontrado com id: " + id));
        
        // If pessoaId is changing, verify the new pessoa exists
        if (trabalhoDTO.getPessoaId() != null && 
           (existingTrabalho.getPessoa() == null || !trabalhoDTO.getPessoaId().equals(existingTrabalho.getPessoa().getId()))) {
            Pessoa pessoa = pessoaRepository.findById(trabalhoDTO.getPessoaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id: " + trabalhoDTO.getPessoaId()));
            existingTrabalho.setPessoa(pessoa);
        }
        
        mapper.updateEntityFromDTO(trabalhoDTO, existingTrabalho);
        existingTrabalho = trabalhoRepository.save(existingTrabalho);
        
        return mapper.toDTO(existingTrabalho);
    }

    // Delete trabalho
    @Transactional
    public void deleteTrabalho(Long id) {
        if (!trabalhoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trabalho não encontrado com id: " + id);
        }
        trabalhoRepository.deleteById(id);
    }
} 