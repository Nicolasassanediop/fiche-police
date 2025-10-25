package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
}