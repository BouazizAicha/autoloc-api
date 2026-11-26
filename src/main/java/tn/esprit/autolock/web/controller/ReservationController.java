package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.dto.ReservationDTO;
import tn.esprit.autolock.mapper.ReservationMapper;
import tn.esprit.autolock.service.IReservationService;

import java.util.List;

@Tag(name = "Gestion des reservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @Operation(description = "Récupérer la liste complète (reservations)")
    @GetMapping
    public List<ReservationDTO> findAll() {
        return reservationMapper.toDtoList(reservationService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public ReservationDTO findById(@PathVariable("id") Long id) {
        return reservationMapper.toDto(reservationService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public ReservationDTO create(@RequestBody Reservation reservation) {
        return reservationMapper.toDto(reservationService.create(reservation));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public ReservationDTO update(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        return reservationMapper.toDto(reservationService.update(id, reservation));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        reservationService.deleteById(id);
    }
}
