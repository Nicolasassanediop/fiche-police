package com.fichepolice.bo.service;

import com.fichepolice.bo.dto.RegionDto;

import java.util.List;

public interface RegionsService {
    List<RegionDto> findAll();
    RegionDto findById(Long id);
    RegionDto create(RegionDto dto);
    RegionDto update(Long id, RegionDto dto);
    void delete(Long id);
}