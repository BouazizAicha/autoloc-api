package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.dto.ContratDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class ContratMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String fmt(LocalDate date) {
        return date == null ? null : date.format(FORMATTER);
    }

    public ContratDTO toDto(Contrat e) {
        if (e == null) {
            return null;
        }
        return new ContratDTO(
                e.getIdContrat(),
                fmt(e.getDateSignature()),
                e.getMontantTotal(),
                e.isValide(),
                e.getReservation() != null ? e.getReservation().getIdReservation() : null);
    }

    public List<ContratDTO> toDtoList(List<Contrat> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
