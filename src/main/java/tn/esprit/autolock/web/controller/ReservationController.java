package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.dto.ReservationDTO;
import tn.esprit.autolock.mapper.ReservationMapper;
import tn.esprit.autolock.service.IServiceReservation;

import java.util.List;

@Tag(name = "Gestion des reservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceReservation
    private final IServiceReservation serviceReservation;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final ReservationMapper reservationMapper;

    @Operation(description = "Récupérer tous les reservations")
    @GetMapping
    public List<ReservationDTO> retrieveAllReservations() {
        return reservationMapper.toDtoList(serviceReservation.retrieveAllReservations());
    }

    @Operation(description = "Récupérer un réservation par son id")
    @GetMapping("/{idReservation}")
    public ReservationDTO retrieveReservation(@PathVariable Long idReservation) {
        return reservationMapper.toDto(serviceReservation.retrieveReservation(idReservation));
    }

    @Operation(description = "Ajouter un réservation")
    @PostMapping
    public ReservationDTO addReservation(@RequestBody Reservation reservation) {
        return reservationMapper.toDto(serviceReservation.addReservation(reservation));
    }

    @Operation(description = "Ajouter plusieurs reservations")
    @PostMapping("/all")
    public List<ReservationDTO> addReservations(@RequestBody List<Reservation> reservations) {
        return reservationMapper.toDtoList(serviceReservation.addReservations(reservations));
    }

    @Operation(description = "Modifier un réservation")
    @PutMapping
    public ReservationDTO updateReservation(@RequestBody Reservation reservation) {
        return reservationMapper.toDto(serviceReservation.updateReservation(reservation));
    }

    @Operation(description = "Supprimer un réservation par son id")
    @DeleteMapping("/{idReservation}")
    public void removeReservation(@PathVariable Long idReservation) {
        serviceReservation.removeReservation(idReservation);
    }
}
