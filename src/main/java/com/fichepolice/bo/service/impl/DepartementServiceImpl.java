package com.fichepolice.bo.service.impl;

import com.fichepolice.bo.dto.DepartementDto;
import com.fichepolice.bo.entity.Departement;
import com.fichepolice.bo.mapper.DepartementMapper;
import com.fichepolice.bo.repository.DepartementRepository;
import com.fichepolice.bo.service.DepartementService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository repository;
    private final DepartementMapper mapper;

    public DepartementServiceImpl(DepartementRepository repository, DepartementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartementDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartementDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé"));
    }

    @Override
    public DepartementDto create(DepartementDto dto) {
        Departement entity = mapper.toEntity(dto);
        Departement saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public DepartementDto update(Long id, DepartementDto dto) {
        Departement entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé"));
        mapper.updateEntityFromDto(dto, entity);
        Departement saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        Departement entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département non trouvé"));
        entity.setDelete(true);
        repository.save(entity);
    }
}