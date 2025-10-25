package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.FichePolice;
import com.fichepolice.enums.StatutFiche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FichePoliceRepository extends JpaRepository<FichePolice, Long> {

    // Lister toutes les fiches d'un hôtel donné
    List<FichePolice> findByHotelCode(String hotelCode);

    // Lister toutes les fiches en attente pour un hôtel
    List<FichePolice> findByHotelCodeAndStatut(String hotelCode, StatutFiche statut);

    // Vérifier si une personne avec un numéro de pièce a déjà une fiche dans cet hôtel
    boolean existsByHotelCodeAndNumeroPiece(String hotelCode, String numeroPiece);
}
