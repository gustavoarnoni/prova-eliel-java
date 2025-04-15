package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabalhoDTO {
    
    private Long id;
    
    @NotBlank(message = "Cargo é obrigatório")
    private String cargo;
    
    @NotBlank(message = "Empresa é obrigatória")
    private String empresa;
    
    private Double salario;
    
    private Long pessoaId;
} 