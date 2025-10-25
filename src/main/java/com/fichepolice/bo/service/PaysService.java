package com.fichepolice.bo.service;

import com.fichepolice.bo.dto.PaysDto;

import java.util.List;

public interface PaysService {
    List<PaysDto> findAll();
    PaysDto findByCode(String code);
    PaysDto create(PaysDto dto);
    PaysDto update(String code, PaysDto dto);
    void delete(String code);
}