package com.fichepolice.bo.mapper;

import com.fichepolice.bo.dto.RegionDto;
import com.fichepolice.bo.entity.Pays;
import com.fichepolice.bo.entity.Regions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RegionsMapper {

    @Mapping(source = "pays.code", target = "paysCode")
    RegionDto toDto(Regions entity);

    @Mapping(source = "paysCode", target = "pays")
    Regions toEntity(RegionDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "paysCode", target = "pays")
    void updateEntityFromDto(RegionDto dto, @MappingTarget Regions entity);

    default Pays fromCode(String code) {
        if (code == null) return null;
        Pays p = new Pays();
        p.setCode(code);
        return p;
    }
}