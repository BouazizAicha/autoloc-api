package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Employe;
import tn.esprit.autolock.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEmploye implements IServiceEmploye {

    // Injection de dépendance par constructeur (généré par Lombok grâce à @RequiredArgsConstructor)
    private final IEmployeRepository employeRepository;

    @Override
    public List<Employe> retrieveAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public Employe addEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe updateEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe retrieveEmploye(Long idEmploye) {
        return employeRepository.findById(idEmploye).orElse(null);
    }

    @Override
    public void removeEmploye(Long idEmploye) {
        employeRepository.deleteById(idEmploye);
    }

    @Override
    public List<Employe> addEmployes(List<Employe> employes) {
        return employeRepository.saveAll(employes);
    }
}
