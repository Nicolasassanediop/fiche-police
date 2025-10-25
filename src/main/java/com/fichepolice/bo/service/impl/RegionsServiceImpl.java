package com.fichepolice.bo.service.impl;

import com.fichepolice.bo.dto.RegionDto;
import com.fichepolice.bo.entity.Regions;
import com.fichepolice.bo.mapper.RegionsMapper;
import com.fichepolice.bo.repository.RegionsRepository;
import com.fichepolice.bo.service.RegionsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegionsServiceImpl implements RegionsService {

    private final RegionsRepository repository;
    private final RegionsMapper mapper;

    public RegionsServiceImpl(RegionsRepository repository, RegionsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RegionDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Région non trouvée"));
    }

    @Override
    public RegionDto create(RegionDto dto) {
        Regions entity = mapper.toEntity(dto);
        Regions saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public RegionDto update(Long id, RegionDto dto) {
        Regions entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Région non trouvée"));
        mapper.updateEntityFromDto(dto, entity);
        Regions saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        Regions entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Région non trouvée"));
        entity.setDelete(true);
        repository.save(entity);
    }
}