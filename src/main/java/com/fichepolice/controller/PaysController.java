package com.fichepolice.bo.controller;

import com.fichepolice.bo.dto.PaysDto;
import com.fichepolice.bo.service.PaysService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pays")
@RequiredArgsConstructor
public class PaysController {

    private final PaysService paysService;

    @GetMapping
    public List<PaysDto> getAll() {
        return paysService.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<PaysDto> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(paysService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<PaysDto> create(@RequestBody @Valid PaysDto dto) {
        PaysDto created = paysService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(created.getCode())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{code}")
    public ResponseEntity<PaysDto> update(@PathVariable String code, @RequestBody @Valid PaysDto dto) {
        return ResponseEntity.ok(paysService.update(code, dto));
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        paysService.delete(code);
    }
}