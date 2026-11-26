package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule e) {
        if (e.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        valider(e);
        return vehiculeRepository.save(e);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Vehicule existant = findById(id);
        existant.setImmatriculation(e.getImmatriculation());
        existant.setMarque(e.getMarque());
        existant.setModele(e.getModele());
        existant.setCategorie(e.getCategorie());
        existant.setTarifJournalier(e.getTarifJournalier());
        existant.setStatut(e.getStatut());
        if (e.getAgence() != null) {
            existant.setAgence(e.getAgence());
        }
        if (e.getEquipements() != null && !e.getEquipements().isEmpty()) {
            existant.setEquipements(e.getEquipements());
        }
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private static void valider(Vehicule e) {
        if (e.getTarifJournalier() != null && e.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }
}
