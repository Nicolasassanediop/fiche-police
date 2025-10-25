package com.fichepolice.bo.service.impl;

import com.fichepolice.bo.entity.Hotel;
import com.fichepolice.bo.repository.HotelRepository;
import com.fichepolice.bo.service.HotelService;
import com.fichepolice.bo.util.QrCodeUtil;
import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;

    public HotelServiceImpl(HotelRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hotel> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Hotel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé"));
    }

    @Override
    public Hotel create(Hotel hotel, String baseUrl) {
        // Assure la date de création si nécessaire
        if (hotel.getDateCreation() == null) {
            hotel.setDateCreation(java.time.LocalDate.now());
        }

        // Compose le contenu du QR : URL + '|' + codeHotel
        String url = baseUrl.endsWith("/") ? baseUrl + "hotels/" + hotel.getCodeHotel() : baseUrl + "/hotels/" + hotel.getCodeHotel();
        String content = url + "|" + hotel.getCodeHotel();

        try {
            String base64 = QrCodeUtil.toBase64Png(content, 300);
            hotel.setQrcode(base64);
        } catch (WriterException | IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossible de générer le QR code");
        }

        return repository.save(hotel);
    }

    @Override
    public Hotel update(Long id, Hotel hotel) {
        Hotel existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé"));
        // mappez les champs nécessaires (exemple simple)
        existing.setNom(hotel.getNom());
        existing.setTelephoneHotel(hotel.getTelephoneHotel());
        existing.setEmailHotel(hotel.getEmailHotel());
        // ne pas changer qrcode ici automatiquement
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Hotel h = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hôtel non trouvé"));
        h.setDeleted(true);
        repository.save(h);
    }
}