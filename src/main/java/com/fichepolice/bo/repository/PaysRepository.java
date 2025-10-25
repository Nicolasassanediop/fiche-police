package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaysRepository extends JpaRepository<Pays, String> {
    Optional<Pays> findByCode(String code);
}