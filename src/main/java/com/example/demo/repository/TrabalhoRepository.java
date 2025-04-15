package com.example.demo.repository;

import com.example.demo.model.Trabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabalhoRepository extends JpaRepository<Trabalho, Long> {
    List<Trabalho> findByPessoaId(Long pessoaId);
} 