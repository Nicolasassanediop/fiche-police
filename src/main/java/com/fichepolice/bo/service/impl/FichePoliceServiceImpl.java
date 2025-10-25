package com.fichepolice.bo.service.impl;

import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.entity.FichePolice;
import com.fichepolice.bo.entity.Hotel;
import com.fichepolice.bo.entity.Pays;
import com.fichepolice.bo.mapper.FichePoliceMapper;
import com.fichepolice.bo.repository.FichePoliceRepository;
import com.fichepolice.bo.repository.HotelRepository;
import com.fichepolice.bo.repository.PaysRepository;
import com.fichepolice.bo.service.FichePoliceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FichePoliceServiceImpl implements FichePoliceService {

    private final FichePoliceRepository repository;
    private final HotelRepository hotelRepository;
    private final PaysRepository paysRepository;
    private final FichePoliceMapper mapper;

    public FichePoliceServiceImpl(FichePoliceRepository repository, FichePoliceMapper mapper, HotelRepository hotelRepository, PaysRepository paysRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.hotelRepository = hotelRepository;
        this.paysRepository = paysRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichePoliceDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FichePoliceDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FichePolice non trouvée"));
    }

    @Override
    public FichePoliceDto create(FichePoliceDto dto) {
        FichePolice entity = mapper.toEntity(dto);

        if (dto.getHotelCode() != null) {
            Hotel hotel = hotelRepository.findByCodeHotel(dto.getHotelCode())
                    .orElseThrow(() -> new IllegalArgumentException("Hotel introuvable: " + dto.getHotelCode()));
            entity.setHotel(hotel);
        } else {
            entity.setHotel(null);
        }

        if (dto.getNationaliteCode() != null) {
            Pays pays = paysRepository.findByCode(dto.getNationaliteCode())
                    .orElseThrow(() -> new IllegalArgumentException("Pays introuvable: " + dto.getNationaliteCode()));
            entity.setNationalite(pays);
        } else {
            entity.setNationalite(null);
        }

        FichePolice saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public FichePoliceDto update(Long id, FichePoliceDto dto) {
        FichePolice entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FichePolice introuvable: " + id));

        mapper.updateEntityFromDto(dto, entity);

        if (dto.getHotelCode() != null) {
            Hotel hotel = hotelRepository.findByCodeHotel(dto.getHotelCode())
                    .orElseThrow(() -> new IllegalArgumentException("Hotel introuvable: " + dto.getHotelCode()));
            entity.setHotel(hotel);
        } else {
            entity.setHotel(null);
        }

        if (dto.getNationaliteCode() != null) {
            Pays pays = paysRepository.findByCode(dto.getNationaliteCode())
                    .orElseThrow(() -> new IllegalArgumentException("Pays introuvable: " + dto.getNationaliteCode()));
            entity.setNationalite(pays);
        } else {
            entity.setNationalite(null);
        }

        FichePolice saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FichePolice non trouvée");
        }
        repository.deleteById(id);
    }
}