package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Equipement;
import tn.esprit.autolock.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEquipement implements IServiceEquipement {

    // Injection de dépendance par constructeur (généré par Lombok grâce à @RequiredArgsConstructor)
    private final IEquipementRepository equipementRepository;

    @Override
    public List<Equipement> retrieveAllEquipements() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement addEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement updateEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement retrieveEquipement(Long idEquipement) {
        return equipementRepository.findById(idEquipement).orElse(null);
    }

    @Override
    public void removeEquipement(Long idEquipement) {
        equipementRepository.deleteById(idEquipement);
    }

    @Override
    public List<Equipement> addEquipements(List<Equipement> equipements) {
        return equipementRepository.saveAll(equipements);
    }
}
