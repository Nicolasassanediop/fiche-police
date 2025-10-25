package com.fichepolice.bo.mapper;

import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.entity.*;
import org.mapstruct.*;
import java.util.Base64;

@Mapper(componentModel = "spring", uses = {AccompagnantMapper.class})
public interface FichePoliceMapper {

    @Mapping(target = "nationaliteCode", expression = "java(entity.getNationalite() == null ? null : entity.getNationalite().getCode())")
    @Mapping(target = "hotelCode", expression = "java(entity.getHotel() == null ? null : entity.getHotel().getCodeHotel())")
    @Mapping(target = "documentScanne", expression = "java(entity.getDocumentScanne() == null ? null : java.util.Base64.getEncoder().encodeToString(entity.getDocumentScanne()))")
    @Mapping(target = "signatureClient", expression = "java(entity.getSignatureClient() == null ? null : java.util.Base64.getEncoder().encodeToString(entity.getSignatureClient()))")
    FichePoliceDto toDto(FichePolice entity);

    @Mapping(source = "nationaliteCode", target = "nationalite")
    @Mapping(source = "hotelCode", target = "hotel")
    @Mapping(target = "documentScanne", expression = "java(dto.getDocumentScanne() == null ? null : java.util.Base64.getDecoder().decode(dto.getDocumentScanne()))")
    @Mapping(target = "signatureClient", expression = "java(dto.getSignatureClient() == null ? null : java.util.Base64.getDecoder().decode(dto.getSignatureClient()))")
    FichePolice toEntity(FichePoliceDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "nationaliteCode", target = "nationalite")
    @Mapping(source = "hotelCode", target = "hotel")
    @Mapping(target = "documentScanne", expression = "java(dto.getDocumentScanne() == null ? entity.getDocumentScanne() : java.util.Base64.getDecoder().decode(dto.getDocumentScanne()))")
    @Mapping(target = "signatureClient", expression = "java(dto.getSignatureClient() == null ? entity.getSignatureClient() : java.util.Base64.getDecoder().decode(dto.getSignatureClient()))")
    void updateEntityFromDto(FichePoliceDto dto, @MappingTarget FichePolice entity);

    // utilitaires pour MapStruct: construire Pays/Hotel à partir d'un code
    default Pays fromCodeToPays(String code) {
        if (code == null) return null;
        Pays p = new Pays();
        p.setCode(code);
        return p;
    }

    default Hotel fromCodeToHotel(String code) {
        if (code == null) return null;
        Hotel h = new Hotel();
        h.setCodeHotel(code);
        return h;
    }
}