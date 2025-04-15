package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private Integer idade;
    
    @Column
    private String endereco;
    
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trabalho> trabalhos = new ArrayList<>();
    
    // Helper method to add trabalho to pessoa
    public void addTrabalho(Trabalho trabalho) {
        trabalhos.add(trabalho);
        trabalho.setPessoa(this);
    }
    
    // Helper method to remove trabalho from pessoa
    public void removeTrabalho(Trabalho trabalho) {
        trabalhos.remove(trabalho);
        trabalho.setPessoa(null);
    }
} 