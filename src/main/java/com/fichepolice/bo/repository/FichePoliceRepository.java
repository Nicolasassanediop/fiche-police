package com.fichepolice.bo.repository;

import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.dto.FichePoliceSearchCriteria;
import com.fichepolice.bo.entity.FichePolice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FichePoliceRepository extends JpaRepository<FichePolice, Long> {
    List<FichePolice> findAllByDeletedFalse();
    Optional<FichePolice> findByIdAndDeletedFalse(Long id);
    boolean existsByIdAndDeletedFalse(Long id);
}
