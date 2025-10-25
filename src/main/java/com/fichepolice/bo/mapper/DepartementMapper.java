package com.fichepolice.bo.mapper;

import com.fichepolice.bo.dto.DepartementDto;
import com.fichepolice.bo.entity.Departement;
import com.fichepolice.bo.entity.Regions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartementMapper {

    @Mapping(source = "region.id", target = "regionId")
    DepartementDto toDto(Departement entity);

    @Mapping(source = "regionId", target = "region")
    Departement toEntity(DepartementDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "regionId", target = "region")
    void updateEntityFromDto(DepartementDto dto, @MappingTarget Departement entity);

    default Regions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Regions r = new Regions();
        r.setId(id);
        return r;
    }
}