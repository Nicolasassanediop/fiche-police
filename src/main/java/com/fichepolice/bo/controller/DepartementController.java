package com.fichepolice.bo.controller;

import com.fichepolice.bo.dto.DepartementDto;
import com.fichepolice.bo.service.DepartementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departements")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementService departementService;

    @GetMapping
    public List<DepartementDto> getAll() {
        return departementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartementDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departementService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DepartementDto> create(@RequestBody @Valid DepartementDto dto) {
        DepartementDto created = departementService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartementDto> update(@PathVariable Long id, @RequestBody @Valid DepartementDto dto) {
        return ResponseEntity.ok(departementService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        departementService.delete(id);
    }
}