package com.fichepolice.bo.controller;

import com.fichepolice.bo.dto.RegionDto;
import com.fichepolice.bo.service.RegionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionsController {

    private final RegionsService regionsService;

    @GetMapping("list-regions")
    public List<RegionDto> getAll() {
        return regionsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(regionsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RegionDto> create(@RequestBody @Valid RegionDto dto) {
        RegionDto created = regionsService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDto> update(@PathVariable Long id, @RequestBody @Valid RegionDto dto) {
        return ResponseEntity.ok(regionsService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        regionsService.delete(id);
    }
}