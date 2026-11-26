package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Employe;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe e) {
        if (e.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouveau employe ne doit pas avoir d'identifiant");
        }
        valider(e);
        return employeRepository.save(e);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Employe existant = findById(id);
        existant.setNom(e.getNom());
        existant.setPrenom(e.getPrenom());
        existant.setRole(e.getRole());
        if (e.getAgence() != null) {
            existant.setAgence(e.getAgence());
        }
        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private static void valider(Employe e) {
        if (isBlank(e.getNom()) || isBlank(e.getPrenom())) {
            throw new IllegalArgumentException("Le nom et le prénom de l'employé sont obligatoires");
        }
    }

    private static boolean isBlank(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
