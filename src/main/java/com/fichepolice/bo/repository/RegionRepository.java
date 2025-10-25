package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Regions, Long> {
    Optional<Regions> findById(Long regionCode);
}
