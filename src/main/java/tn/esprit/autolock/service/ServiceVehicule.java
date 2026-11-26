package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceVehicule implements IServiceVehicule {

    // Injection de dépendance par constructeur (généré par Lombok grâce à @RequiredArgsConstructor)
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public List<Vehicule> retrieveAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule addVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule retrieveVehicule(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule).orElse(null);
    }

    @Override
    public void removeVehicule(Long idVehicule) {
        vehiculeRepository.deleteById(idVehicule);
    }

    @Override
    public List<Vehicule> addVehicules(List<Vehicule> vehicules) {
        return vehiculeRepository.saveAll(vehicules);
    }
}
