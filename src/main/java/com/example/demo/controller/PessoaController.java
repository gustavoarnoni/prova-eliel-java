package com.example.demo.controller;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.dto.TrabalhoDTO;
import com.example.demo.service.PessoaService;
import com.example.demo.service.TrabalhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    private final TrabalhoService trabalhoService;

    @Autowired
    public PessoaController(PessoaService pessoaService, TrabalhoService trabalhoService) {
        this.pessoaService = pessoaService;
        this.trabalhoService = trabalhoService;
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> getAllPessoas() {
        return ResponseEntity.ok(pessoaService.getAllPessoas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.getPessoaById(id));
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> createPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        return new ResponseEntity<>(pessoaService.createPessoa(pessoaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(@PathVariable Long id, @Valid @RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok(pessoaService.updatePessoa(id, pessoaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }

    // Get trabalhos for a person
    @GetMapping("/{id}/trabalhos")
    public ResponseEntity<List<TrabalhoDTO>> getTrabalhosByPessoaId(@PathVariable Long id) {
        // Check if pessoa exists
        pessoaService.getPessoaById(id);
        return ResponseEntity.ok(trabalhoService.getTrabalhosByPessoaId(id));
    }
} 