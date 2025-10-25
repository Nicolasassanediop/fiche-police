package com.fichepolice.bo.controller;

import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.service.FichePoliceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fiche-police")
@RequiredArgsConstructor
public class FichePoliceController {

    private final FichePoliceService fichePoliceService;

    @GetMapping
    public List<FichePoliceDto> getAll() {
        return fichePoliceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichePoliceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fichePoliceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FichePoliceDto> create(@RequestBody @Valid FichePoliceDto dto) {
        FichePoliceDto created = fichePoliceService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FichePoliceDto> update(@PathVariable Long id, @RequestBody @Valid FichePoliceDto dto) {
        FichePoliceDto updated = fichePoliceService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fichePoliceService.delete(id);
    }
}