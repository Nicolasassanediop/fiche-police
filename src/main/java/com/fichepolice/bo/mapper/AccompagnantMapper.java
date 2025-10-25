package com.fichepolice.bo.mapper;

import com.fichepolice.bo.dto.AccompagnantDto;
import com.fichepolice.bo.entity.Accompagnant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccompagnantMapper {
    AccompagnantDto toDto(Accompagnant entity);
    Accompagnant toEntity(AccompagnantDto dto);
}