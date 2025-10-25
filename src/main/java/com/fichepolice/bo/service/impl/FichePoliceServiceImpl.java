package com.fichepolice.bo.service.impl;

import com.fichepolice.bo.dto.AccompagnantDto;
import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.entity.Accompagnant;
import com.fichepolice.bo.entity.FichePolice;
import com.fichepolice.bo.entity.Hotel;
import com.fichepolice.bo.entity.Pays;
import com.fichepolice.bo.mapper.FichePoliceMapper;
import com.fichepolice.bo.repository.FichePoliceRepository;
import com.fichepolice.bo.repository.HotelRepository;
import com.fichepolice.bo.repository.PaysRepository;
import com.fichepolice.bo.service.FichePoliceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FichePoliceServiceImpl implements FichePoliceService {

    private final FichePoliceRepository repository;
    private final HotelRepository hotelRepository;
    private final PaysRepository paysRepository;
    private final FichePoliceMapper mapper;

    public FichePoliceServiceImpl(FichePoliceRepository repository,
                                  FichePoliceMapper mapper,
                                  HotelRepository hotelRepository,
                                  PaysRepository paysRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.hotelRepository = hotelRepository;
        this.paysRepository = paysRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichePoliceDto> findAll() {
        return repository.findAllByDeletedFalse()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FichePoliceDto findById(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FichePolice non trouvée"));
    }

    @Override
    public FichePoliceDto create(FichePoliceDto dto) {
        FichePolice entity = mapper.toEntity(dto);

        resolveHotel(dto, entity);
        resolveNationalite(dto, entity);

        // rattacher les accompagnants via une boucle pour éviter capture de variable mutable
        if (entity.getAccompagnants() != null && !entity.getAccompagnants().isEmpty()) {
            List<Accompagnant> copy = new ArrayList<>(entity.getAccompagnants());
            entity.getAccompagnants().clear();
            for (Accompagnant a : copy) {
                entity.addAccompagnant(a);
            }
        }

        // résoudre les nationalités des accompagnants après le mapping
        if (dto.getAccompagnants() != null && !dto.getAccompagnants().isEmpty() && entity.getAccompagnants() != null) {
            for (int i = 0; i < dto.getAccompagnants().size() && i < entity.getAccompagnants().size(); i++) {
                AccompagnantDto adto = dto.getAccompagnants().get(i);
                String codePays = adto == null ? null : adto.getNationaliteCode();
                if (codePays != null) {
                    Pays p = paysRepository.findByCode(codePays)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pays introuvable: " + codePays));
                    entity.getAccompagnants().get(i).setNationalite(p);
                } else {
                    entity.getAccompagnants().get(i).setNationalite(null);
                }
            }
        }

        FichePolice saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public FichePoliceDto update(Long id, FichePoliceDto dto) {
        FichePolice entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FichePolice introuvable: " + id));

        // update simple fields via mapper
        mapper.updateEntityFromDto(dto, entity);

        resolveHotel(dto, entity);
        resolveNationalite(dto, entity);

        // gérer correctement la collection d'accompagnants : reconstruction via boucle
        if (entity.getAccompagnants() != null) {
            List<Accompagnant> copy = new ArrayList<>(entity.getAccompagnants());
            entity.getAccompagnants().clear();
            for (Accompagnant a : copy) {
                entity.addAccompagnant(a);
            }
        }

        // associer les nationalités aux accompagnants selon le DTO
        if (dto.getAccompagnants() != null && !dto.getAccompagnants().isEmpty() && entity.getAccompagnants() != null) {
            for (int i = 0; i < dto.getAccompagnants().size() && i < entity.getAccompagnants().size(); i++) {
                AccompagnantDto adto = dto.getAccompagnants().get(i);
                String codePays = adto == null ? null : adto.getNationaliteCode();
                if (codePays != null) {
                    Pays p = paysRepository.findByCode(codePays)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pays introuvable: " + codePays));
                    entity.getAccompagnants().get(i).setNationalite(p);
                } else {
                    entity.getAccompagnants().get(i).setNationalite(null);
                }
            }
        }

        FichePolice saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        FichePolice entity = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FichePolice non trouvée"));
        // corriger le nom du setter pour la suppression logique
        entity.setDeleted(true);
        repository.save(entity);
    }

    // helpers

    private void resolveHotel(FichePoliceDto dto, FichePolice entity) {
        if (dto.getHotelCode() != null) {
            Hotel hotel = hotelRepository.findByCodeHotel(dto.getHotelCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel introuvable: " + dto.getHotelCode()));
            entity.setHotel(hotel);
        } else {
            entity.setHotel(null);
        }
    }

    private void resolveNationalite(FichePoliceDto dto, FichePolice entity) {
        if (dto.getNationaliteCode() != null) {
            Pays pays = paysRepository.findByCode(dto.getNationaliteCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pays introuvable: " + dto.getNationaliteCode()));
            entity.setNationalite(pays);
        } else {
            entity.setNationalite(null);
        }
    }
}