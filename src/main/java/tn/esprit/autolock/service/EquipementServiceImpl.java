package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Equipement;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement e) {
        if (e.getIdEquipement() != null) {
            throw new IllegalArgumentException("Un nouveau equipement ne doit pas avoir d'identifiant");
        }
        valider(e);
        return equipementRepository.save(e);
    }

    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Equipement existant = findById(id);
        existant.setLibelle(e.getLibelle());

        return equipementRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private static void valider(Equipement e) {
        if (isBlank(e.getLibelle())) {
            throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
        }
    }

    private static boolean isBlank(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
