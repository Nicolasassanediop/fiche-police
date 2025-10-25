package com.fichepolice.bo.mapper;

import com.fichepolice.bo.dto.PaysDto;
import com.fichepolice.bo.entity.Pays;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaysMapper {

    PaysDto toDto(Pays entity);

    Pays toEntity(PaysDto dto);

    void updateEntityFromDto(PaysDto dto, @MappingTarget Pays entity);
}

