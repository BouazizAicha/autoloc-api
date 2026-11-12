package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Agence;
import tn.esprit.autolock.dto.AgenceDTO;

import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class AgenceMapper {

    public AgenceDTO toDto(Agence e) {
        if (e == null) {
            return null;
        }
        return new AgenceDTO(
                e.getIdAgence(),
                e.getNom(),
                e.getVille(),
                e.getAdresse(),
                e.getTelephone());
    }

    public List<AgenceDTO> toDtoList(List<Agence> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
