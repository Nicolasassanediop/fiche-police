package com.fichepolice.bo.controller;

import com.fichepolice.bo.dto.PaysDto;
import com.fichepolice.bo.service.PaysService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pays")
public class PaysController {

    private final PaysService service;

    public PaysController(PaysService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PaysDto>> all() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<PaysDto> get(@PathVariable String code) {
        PaysDto dto = service.findByCode(code);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaysDto> create(@RequestBody PaysDto dto) {
        PaysDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/pays/" + created.getCode())).body(created);
    }

    @PutMapping("/{code}")
    public ResponseEntity<PaysDto> update(@PathVariable String code, @RequestBody PaysDto dto) {
        PaysDto updated = service.update(code, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }
}