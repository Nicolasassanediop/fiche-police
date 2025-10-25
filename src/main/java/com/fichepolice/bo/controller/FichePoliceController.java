//package com.fichepolice.bo.controller;
//
//import com.fichepolice.bo.dto.FichePoliceRequest;
//import com.fichepolice.bo.dto.FichePoliceResponse;
//import com.fichepolice.bo.dto.ValidateFichePoliceRequest;
//import com.fichepolice.bo.mapper.FichePoliceMapper;
//import com.fichepolice.bo.service.FichePoliceService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/fiches")
//@RequiredArgsConstructor
//public class FichePoliceController {
//
//    private final FichePoliceService service;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public FichePoliceResponse create(@Valid @RequestBody FichePoliceRequest request) {
//        var saved = service.create(request);
//        return FichePoliceMapper.toResponse(saved);
//    }
//
//    @PostMapping("/{id}/validate")
//    public FichePoliceResponse validate(@PathVariable Long id,
//                                        @Valid @RequestBody ValidateFichePoliceRequest request) {
//        var updated = service.validate(id, request);
//        return FichePoliceMapper.toResponse(updated);
//    }
//
//    @GetMapping("/{id}")
//    public FichePoliceResponse get(@PathVariable Long id) {
//        var fiche = service.get(id);
//        return FichePoliceMapper.toResponse(fiche);
//    }
//
//    @GetMapping
//    public List<FichePoliceResponse> list() {
//        return service.listAll().stream()
//                .map(FichePoliceMapper::toResponse)
//                .toList();
//    }
//}
