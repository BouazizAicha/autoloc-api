package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Maintenance;
import tn.esprit.autolock.dto.MaintenanceDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class MaintenanceMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String fmt(LocalDate date) {
        return date == null ? null : date.format(FORMATTER);
    }

    public MaintenanceDTO toDto(Maintenance e) {
        if (e == null) {
            return null;
        }
        return new MaintenanceDTO(
                e.getIdMaintenance(),
                fmt(e.getDateDebut()),
                fmt(e.getDateFin()),
                e.getDescription(),
                e.getVehicule() != null ? e.getVehicule().getIdVehicule() : null);
    }

    public List<MaintenanceDTO> toDtoList(List<Maintenance> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
