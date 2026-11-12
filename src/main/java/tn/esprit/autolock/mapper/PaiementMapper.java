package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Paiement;
import tn.esprit.autolock.dto.PaiementDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class PaiementMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String fmt(LocalDate date) {
        return date == null ? null : date.format(FORMATTER);
    }

    public PaiementDTO toDto(Paiement e) {
        if (e == null) {
            return null;
        }
        return new PaiementDTO(
                e.getIdPaiement(),
                e.getMontant(),
                fmt(e.getDatePaiement()),
                e.getModePaiement(),
                e.getContrat() != null ? e.getContrat().getIdContrat() : null);
    }

    public List<PaiementDTO> toDtoList(List<Paiement> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
