package com.fichepolice.bo.service;

import com.fichepolice.bo.entity.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> findAll();
    Hotel findById(Long id);
    Hotel create(Hotel hotel, String baseUrl);
    Hotel update(Long id, Hotel hotel);
    void delete(Long id);
}