package com.fichepolice.bo.service.impl;

import com.fichepolice.bo.dto.PaysDto;
import com.fichepolice.bo.entity.Pays;
import com.fichepolice.bo.mapper.PaysMapper;
import com.fichepolice.bo.repository.PaysRepository;
import com.fichepolice.bo.service.PaysService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaysServiceImpl implements PaysService {

    private final PaysRepository repository;
    private final PaysMapper mapper;

    public PaysServiceImpl(PaysRepository repository, PaysMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaysDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PaysDto findByCode(String code) {
        return repository.findById(code)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pays non trouvé"));
    }

    @Override
    public PaysDto create(PaysDto dto) {
        if (dto.getCode() == null || dto.getCode().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le code est requis");
        }
        if (repository.existsById(dto.getCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pays déjà existant");
        }
        Pays entity = mapper.toEntity(dto);
        Pays saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public PaysDto update(String code, PaysDto dto) {
        Pays entity = repository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pays non trouvé"));
        String originalCode = entity.getCode();
        mapper.updateEntityFromDto(dto, entity);
        entity.setCode(originalCode); // empêcher la modification du code
        Pays saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public void delete(String code) {
        Pays entity = repository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pays non trouvé"));
        entity.setDelete(true); // soft-delete
        repository.save(entity);
    }
}