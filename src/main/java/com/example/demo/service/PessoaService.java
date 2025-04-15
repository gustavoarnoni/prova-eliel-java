package com.example.demo.service;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EntityMapper;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EntityMapper mapper;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, EntityMapper mapper) {
        this.pessoaRepository = pessoaRepository;
        this.mapper = mapper;
    }

    // Get all pessoas
    public List<PessoaDTO> getAllPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return mapper.toPessoaDTOList(pessoas);
    }

    // Get pessoa by id
    public PessoaDTO getPessoaById(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id: " + id));
        return mapper.toDTO(pessoa);
    }

    // Create new pessoa
    @Transactional
    public PessoaDTO createPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = mapper.toEntity(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return mapper.toDTO(pessoa);
    }

    // Update existing pessoa
    @Transactional
    public PessoaDTO updatePessoa(Long id, PessoaDTO pessoaDTO) {
        Pessoa existingPessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id: " + id));
        
        mapper.updateEntityFromDTO(pessoaDTO, existingPessoa);
        existingPessoa = pessoaRepository.save(existingPessoa);
        
        return mapper.toDTO(existingPessoa);
    }

    // Delete pessoa
    @Transactional
    public void deletePessoa(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com id: " + id);
        }
        pessoaRepository.deleteById(id);
    }
} 