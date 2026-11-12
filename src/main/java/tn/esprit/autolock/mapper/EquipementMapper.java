package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Equipement;
import tn.esprit.autolock.dto.EquipementDTO;

import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class EquipementMapper {

    public EquipementDTO toDto(Equipement e) {
        if (e == null) {
            return null;
        }
        return new EquipementDTO(
                e.getIdEquipement(),
                e.getLibelle());
    }

    public List<EquipementDTO> toDtoList(List<Equipement> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
