package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.Regions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionsRepository extends JpaRepository<Regions, Long> {
}