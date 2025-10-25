package com.fichepolice.bo.repository;

import com.fichepolice.bo.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByCodeHotel(String codeHotel);
}