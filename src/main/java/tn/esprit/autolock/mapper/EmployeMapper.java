package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Employe;
import tn.esprit.autolock.dto.EmployeDTO;

import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class EmployeMapper {

    public EmployeDTO toDto(Employe e) {
        if (e == null) {
            return null;
        }
        return new EmployeDTO(
                e.getIdEmploye(),
                e.getNom(),
                e.getPrenom(),
                e.getRole(),
                e.getAgence() != null ? e.getAgence().getIdAgence() : null,
                e.getAgence() != null ? e.getAgence().getNom() : null);
    }

    public List<EmployeDTO> toDtoList(List<Employe> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
