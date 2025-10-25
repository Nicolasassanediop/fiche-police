//package com.fichepolice.bo.mapper;
//
//import com.fichepolice.bo.entity.FichePolice;
//import com.fichepolice.bo.entity.Hotel;
//import com.fichepolice.bo.entity.Pays;
//import com.fichepolice.bo.dto.Accompagnant;
//import com.fichepolice.bo.dto.FichePoliceRequest;
//import com.fichepolice.bo.dto.FichePoliceResponse;
//
//import java.util.Base64;
//import java.util.List;
//
//public class FichePoliceMapper {
//
//    public static FichePolice toEntity(FichePoliceRequest dto, Hotel hotel, Pays nationalite, Pays prochaineDestination) {
//        FichePolice f = new FichePolice();
//        f.setNom(dto.nom());
//        f.setPrenom(dto.prenom());
//        f.setDateNaissance(dto.dateNaissance());
//        f.setLieuNaissance(dto.lieuNaissance());
//        f.setSexe(dto.sexe());
//        f.setNationalite(nationalite);
//        f.setTypePiece(dto.typePiece());
//        f.setNumeroPiece(dto.numeroPiece());
//        f.setDateDelivrancePiece(dto.dateDelivrancePiece());
//        f.setLieuDelivrancePiece(dto.lieuDelivrancePiece());
//        f.setDateExpirationPiece(dto.dateExpirationPiece());
//        f.setTelephone(dto.telephone());
//        f.setEmail(dto.email());
//        f.setAdresse(dto.adresse());
//        f.setProfession(dto.profession());
//        f.setDateArriveePays(dto.dateArriveePays());
//        f.setDateArrivee(dto.dateArrivee());
//        f.setDateDepart(dto.dateDepart());
//        f.setMotifSejour(dto.motifSejour());
//        f.setNumeroChambre(dto.numeroChambre());
//        f.setHotel(hotel);
//        f.setNatureVoyage(dto.natureVoyage());
//        f.setVenantDe(dto.venantDe());
//        f.setPartantPour(dto.partantPour());
//        f.setProchaineDestination(prochaineDestination);
//        // dateSortiePays à mapper si présent dans le DTO
//        if (dto.dateSortiePays() != null) {
//            f.setDateSortiePays(dto.dateSortiePays());
//        }
//        if (dto.documentBase64() != null) {
//            f.setDocumentScanne(Base64.getDecoder().decode(dto.documentBase64()));
//        }
//        if (dto.signatureBase64() != null) {
//            f.setSignatureClient(Base64.getDecoder().decode(dto.signatureBase64()));
//        }
//        if (dto.accompagnants() != null && !dto.accompagnants().isEmpty()) {
//            List<Accompagnant> list = dto.accompagnants().stream().map(a -> {
//                Accompagnant ac = new Accompagnant();
//                ac.setNom(a.nom());
//                ac.setPrenom(a.prenom());
//                ac.setDateNaissance(a.dateNaissance());
//                ac.setFichePolice(f);
//                return ac;
//            }).toList();
//            f.setAccompagnants(list);
//        }
//        return f;
//    }
//
//    public static FichePoliceResponse toResponse(FichePolice f) {
//        List<String> acc = f.getAccompagnants() == null ? List.of()
//                : f.getAccompagnants().stream()
//                .map(a -> a.getNom() + " " + a.getPrenom() + " (" + a.getDateNaissance() + ")")
//                .toList();
//
//        return new FichePoliceResponse(
//                f.getId(),
//                f.getNom(),
//                f.getPrenom(),
//                f.getDateNaissance(),
//                f.getLieuNaissance(),
//                f.getSexe(),
//                f.getNationalite() != null ? f.getNationalite().getOfficialName() : null,
//                f.getTypePiece(),
//                f.getNumeroPiece(),
//                f.getDateDelivrancePiece(),
//                f.getLieuDelivrancePiece(),
//                f.getDateExpirationPiece(),
//                f.getTelephone(),
//                f.getEmail(),
//                f.getAdresse(),
//                f.getProchaineDestination() != null ? f.getProchaineDestination().getOfficialName() : null,
//                f.getProfession(),
//                f.getDateArriveePays(),
//                f.getDateArrivee(), // correspondance avec le champ de l'entité
//                f.getDateDepart(), // correspondance avec le champ de l'entité
//                f.getMotifSejour(),
//                f.getNumeroChambre(),
//                f.getHotel() != null ? f.getHotel().getCode() : null,
//                f.getStatut(),
//                f.getStatutTransmission(),
//                f.getValiderPar(),
//                f.getValiderdAt(),
//                acc
//        );
//    }
//}
