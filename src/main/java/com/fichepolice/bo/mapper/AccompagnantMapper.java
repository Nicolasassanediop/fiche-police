package com.fichepolice.bo.mapper;

import com.fichepolice.bo.dto.AccompagnantDto;
import com.fichepolice.bo.entity.Accompagnant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccompagnantMapper {
    @Mapping(source = "nationalite.code", target = "nationaliteCode")
    AccompagnantDto toDto(Accompagnant entity);
    @Mapping(target = "nationalite", ignore = true)
    Accompagnant toEntity(AccompagnantDto dto);
}