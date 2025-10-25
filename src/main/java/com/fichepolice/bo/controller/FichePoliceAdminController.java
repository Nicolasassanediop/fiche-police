package com.fichepolice.bo.controller;
import com.fichepolice.bo.entity.FichePolice;
import com.fichepolice.bo.repository.FichePoliceRepository;
import com.fichepolice.enums.StatutFiche;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bo/hotels/fiches")
public class FichePoliceAdminController {

    private final FichePoliceRepository ficheRepo;

    public FichePoliceAdminController(FichePoliceRepository ficheRepo) {
        this.ficheRepo = ficheRepo;
    }

    @PostMapping("/{id}/valider")
    public ResponseEntity<String> valider(@PathVariable Long id) {
        var fiche = ficheRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fiche introuvable"));
        fiche.setStatut(StatutFiche.VALIDEE);
        ficheRepo.save(fiche);
        return ResponseEntity.ok("Fiche validée avec succès !");
    }

    @PostMapping("/{id}/refuser")
    public ResponseEntity<String> refuser(@PathVariable Long id) {
        var fiche = ficheRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fiche introuvable"));
        fiche.setStatut(StatutFiche.REFUSE);
        ficheRepo.save(fiche);
        return ResponseEntity.ok("Fiche refusée !");
    }

    // Optionnel : lister toutes les fiches d'un hôtel pour le receptioniste
    @GetMapping("/hotel/{hotelCode}")
    public ResponseEntity<List<FichePolice>> listerFiches(@PathVariable String hotelCode) {
        var fiches = ficheRepo.findAll().stream()
                .filter(f -> f.getHotel().getCode().equals(hotelCode))
                .toList();
        return ResponseEntity.ok(fiches);
    }
}
