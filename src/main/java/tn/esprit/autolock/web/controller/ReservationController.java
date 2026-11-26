package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.dto.ReservationDTO;
import tn.esprit.autolock.mapper.ReservationMapper;
import tn.esprit.autolock.service.IReservationService;

import java.util.List;

@Tag(name = "Réservations", description = "Gestion des réservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @Operation(summary = "Lister les reservations")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<ReservationDTO> findAll() {
        return reservationMapper.toDtoList(reservationService.findAll());
    }

    @Operation(summary = "Consulter une réservation")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public ReservationDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return reservationMapper.toDto(reservationService.findById(id));
    }

    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@RequestBody Reservation reservation) {
        return reservationMapper.toDto(reservationService.create(reservation));
    }

    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public ReservationDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Reservation reservation) {
        return reservationMapper.toDto(reservationService.update(id, reservation));
    }

    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        reservationService.deleteById(id);
    }
}
