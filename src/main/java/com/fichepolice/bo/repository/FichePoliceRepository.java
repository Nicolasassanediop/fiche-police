package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.Departement;
import com.fichepolice.bo.entity.FichePolice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichePoliceRepository extends JpaRepository<FichePolice, Long> {
}
