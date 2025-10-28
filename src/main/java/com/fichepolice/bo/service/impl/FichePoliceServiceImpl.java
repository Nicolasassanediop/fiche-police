package com.fichepolice.bo.service.impl;

import com.fichepolice.bo.dto.AccompagnantDto;
import com.fichepolice.bo.dto.FichePoliceDto;
import com.fichepolice.bo.dto.FichePoliceSearchCriteria;
import com.fichepolice.bo.entity.Accompagnant;
import com.fichepolice.bo.entity.FichePolice;
import com.fichepolice.bo.entity.Hotel;
import com.fichepolice.bo.entity.Pays;
import com.fichepolice.bo.mapper.FichePoliceMapper;
import com.fichepolice.bo.repository.FichePoliceRepository;
import com.fichepolice.bo.repository.HotelRepository;
import com.fichepolice.bo.repository.PaysRepository;
import com.fichepolice.bo.service.FichePoliceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class FichePoliceServiceImpl implements FichePoliceService {
    @PersistenceContext
    private EntityManager em;

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

    @Override
    public List<FichePoliceDto> searchFichePolice(FichePoliceSearchCriteria c) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FichePolice> cq = cb.createQuery(FichePolice.class);
        Root<FichePolice> root = cq.from(FichePolice.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isFalse(root.get("deleted")));

        if (StringUtils.hasText(c.getPrenom())) {
            predicates.add(cb.like(cb.lower(root.get("prenom")), "%" + c.getPrenom().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(c.getNom())) {
            predicates.add(cb.like(cb.lower(root.get("nom")), "%" + c.getNom().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(c.getNumeroPiece())) {
            predicates.add(cb.like(cb.lower(root.get("numeroPiece")), "%" + c.getNumeroPiece().toLowerCase() + "%"));
        }
        if (c.getDateArriveeFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dateArrivee"), c.getDateArriveeFrom()));
        }
        if (c.getDateArriveeTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("dateArrivee"), c.getDateArriveeTo()));
        }
        if (c.getDateDepartFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dateDepart"), c.getDateDepartFrom()));
        }
        if (c.getDateDepartTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("dateDepart"), c.getDateDepartTo()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(root.get("dateArrivee")));

        List<FichePolice> entities = em.createQuery(cq).getResultList();
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}