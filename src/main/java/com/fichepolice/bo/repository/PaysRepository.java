package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaysRepository extends JpaRepository<Pays, String> {
}