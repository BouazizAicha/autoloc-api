package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.repository.IContratRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceContrat implements IServiceContrat {

    // Injection de dépendance par constructeur (généré par Lombok grâce à @RequiredArgsConstructor)
    private final IContratRepository contratRepository;

    @Override
    public List<Contrat> retrieveAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat addContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat updateContrat(Contrat contrat) {
        // Les paiements ne sont pas envoyés dans le JSON (@JsonIgnore) : sans cette ligne,
        // orphanRemoval supprimerait tous les paiements du contrat lors d'un PUT
        contratRepository.findById(contrat.getIdContrat())
                .ifPresent(existant -> contrat.setPaiements(existant.getPaiements()));
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat retrieveContrat(Long idContrat) {
        return contratRepository.findById(idContrat).orElse(null);
    }

    @Override
    public void removeContrat(Long idContrat) {
        contratRepository.deleteById(idContrat);
    }

    @Override
    public List<Contrat> addContrats(List<Contrat> contrats) {
        return contratRepository.saveAll(contrats);
    }
}
