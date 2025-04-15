package com.example.demo.controller;

import com.example.demo.dto.TrabalhoDTO;
import com.example.demo.service.TrabalhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabalhos")
public class TrabalhoController {

    private final TrabalhoService trabalhoService;

    @Autowired
    public TrabalhoController(TrabalhoService trabalhoService) {
        this.trabalhoService = trabalhoService;
    }

    @GetMapping
    public ResponseEntity<List<TrabalhoDTO>> getAllTrabalhos() {
        return ResponseEntity.ok(trabalhoService.getAllTrabalhos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabalhoDTO> getTrabalhoById(@PathVariable Long id) {
        return ResponseEntity.ok(trabalhoService.getTrabalhoById(id));
    }

    @PostMapping
    public ResponseEntity<TrabalhoDTO> createTrabalho(@Valid @RequestBody TrabalhoDTO trabalhoDTO) {
        return new ResponseEntity<>(trabalhoService.createTrabalho(trabalhoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabalhoDTO> updateTrabalho(@PathVariable Long id, @Valid @RequestBody TrabalhoDTO trabalhoDTO) {
        return ResponseEntity.ok(trabalhoService.updateTrabalho(id, trabalhoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrabalho(@PathVariable Long id) {
        trabalhoService.deleteTrabalho(id);
        return ResponseEntity.noContent().build();
    }
} 