package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.dto.ReservationDTO;
import tn.esprit.autolock.dto.ReservationResponseDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class ReservationMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String fmt(LocalDate date) {
        return date == null ? null : date.format(FORMATTER);
    }

    public ReservationDTO toDto(Reservation e) {
        if (e == null) {
            return null;
        }
        return new ReservationDTO(
                e.getIdReservation(),
                fmt(e.getDateDebut()),
                fmt(e.getDateFin()),
                e.getStatut(),
                e.getClient() != null ? e.getClient().getIdClient() : null,
                e.getVehicule() != null ? e.getVehicule().getIdVehicule() : null);
    }

    public List<ReservationDTO> toDtoList(List<Reservation> entities) {
        return entities.stream().map(this::toDto).toList();
    }

    public ReservationResponseDTO toResponseDto(Reservation e) {
        if (e == null) {
            return null;
        }
        return new ReservationResponseDTO(
                e.getIdReservation(),
                fmt(e.getDateDebut()),
                fmt(e.getDateFin()),
                e.getStatut(),
                e.getClient() != null ? e.getClient().getIdClient() : null,
                e.getClient() != null ? e.getClient().getNom() : null,
                e.getClient() != null ? e.getClient().getPrenom() : null,
                e.getVehicule() != null ? e.getVehicule().getIdVehicule() : null,
                e.getVehicule() != null ? e.getVehicule().getImmatriculation() : null,
                e.getContrat() != null ? e.getContrat().getIdContrat() : null);
    }
}
