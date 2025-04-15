package com.example.demo.mapper;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.dto.TrabalhoDTO;
import com.example.demo.model.Pessoa;
import com.example.demo.model.Trabalho;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    // Convert Pessoa entity to DTO
    public PessoaDTO toDTO(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }
        
        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setIdade(pessoa.getIdade());
        dto.setEndereco(pessoa.getEndereco());
        
        if (pessoa.getTrabalhos() != null) {
            dto.setTrabalhos(pessoa.getTrabalhos().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    // Convert Trabalho entity to DTO
    public TrabalhoDTO toDTO(Trabalho trabalho) {
        if (trabalho == null) {
            return null;
        }
        
        TrabalhoDTO dto = new TrabalhoDTO();
        dto.setId(trabalho.getId());
        dto.setCargo(trabalho.getCargo());
        dto.setEmpresa(trabalho.getEmpresa());
        dto.setSalario(trabalho.getSalario());
        
        if (trabalho.getPessoa() != null) {
            dto.setPessoaId(trabalho.getPessoa().getId());
        }
        
        return dto;
    }
    
    // Convert PessoaDTO to entity
    public Pessoa toEntity(PessoaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Pessoa pessoa = new Pessoa();
        updateEntityFromDTO(dto, pessoa);
        return pessoa;
    }
    
    // Update Pessoa entity from DTO
    public void updateEntityFromDTO(PessoaDTO dto, Pessoa pessoa) {
        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setEndereco(dto.getEndereco());
    }
    
    // Convert TrabalhoDTO to entity
    public Trabalho toEntity(TrabalhoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Trabalho trabalho = new Trabalho();
        updateEntityFromDTO(dto, trabalho);
        return trabalho;
    }
    
    // Update Trabalho entity from DTO
    public void updateEntityFromDTO(TrabalhoDTO dto, Trabalho trabalho) {
        trabalho.setCargo(dto.getCargo());
        trabalho.setEmpresa(dto.getEmpresa());
        trabalho.setSalario(dto.getSalario());
    }
    
    // Convert list of Pessoa entities to list of DTOs
    public List<PessoaDTO> toPessoaDTOList(List<Pessoa> pessoas) {
        return pessoas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    // Convert list of Trabalho entities to list of DTOs
    public List<TrabalhoDTO> toTrabalhoDTOList(List<Trabalho> trabalhos) {
        return trabalhos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
} 