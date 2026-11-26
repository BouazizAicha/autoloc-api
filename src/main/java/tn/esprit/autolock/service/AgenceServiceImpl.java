package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Agence;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence e) {
        if (e.getIdAgence() != null) {
            throw new IllegalArgumentException("Un nouveau agence ne doit pas avoir d'identifiant");
        }
        valider(e);
        return agenceRepository.save(e);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Agence existant = findById(id);
        existant.setNom(e.getNom());
        existant.setVille(e.getVille());
        existant.setAdresse(e.getAdresse());
        existant.setTelephone(e.getTelephone());

        return agenceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private static void valider(Agence e) {
        if (isBlank(e.getNom()) || isBlank(e.getVille()) || isBlank(e.getAdresse())) {
            throw new IllegalArgumentException("Le nom, la ville et l'adresse de l'agence sont obligatoires");
        }
    }

    private static boolean isBlank(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
