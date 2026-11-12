package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.dto.VehiculeDTO;

import java.util.ArrayList;
import tn.esprit.autolock.domain.Equipement;
import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class VehiculeMapper {

    public VehiculeDTO toDto(Vehicule e) {
        if (e == null) {
            return null;
        }
        return new VehiculeDTO(
                e.getIdVehicule(),
                e.getImmatriculation(),
                e.getMarque(),
                e.getModele(),
                e.getCategorie(),
                e.getTarifJournalier(),
                e.getStatut(),
                e.getAgence() != null ? e.getAgence().getIdAgence() : null,
                e.getEquipements() == null ? new ArrayList<>() : e.getEquipements().stream().map(Equipement::getLibelle).toList());
    }

    public List<VehiculeDTO> toDtoList(List<Vehicule> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
