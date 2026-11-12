package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Employe;
import tn.esprit.autolock.dto.EmployeDTO;
import tn.esprit.autolock.mapper.EmployeMapper;
import tn.esprit.autolock.service.IServiceEmploye;

import java.util.List;

@Tag(name = "Gestion des employes")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceEmploye
    private final IServiceEmploye serviceEmploye;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final EmployeMapper employeMapper;

    @Operation(description = "Récupérer tous les employes")
    @GetMapping
    public List<EmployeDTO> retrieveAllEmployes() {
        return employeMapper.toDtoList(serviceEmploye.retrieveAllEmployes());
    }

    @Operation(description = "Récupérer un employé par son id")
    @GetMapping("/{idEmploye}")
    public EmployeDTO retrieveEmploye(@PathVariable Long idEmploye) {
        return employeMapper.toDto(serviceEmploye.retrieveEmploye(idEmploye));
    }

    @Operation(description = "Ajouter un employé")
    @PostMapping
    public EmployeDTO addEmploye(@RequestBody Employe employe) {
        return employeMapper.toDto(serviceEmploye.addEmploye(employe));
    }

    @Operation(description = "Ajouter plusieurs employes")
    @PostMapping("/all")
    public List<EmployeDTO> addEmployes(@RequestBody List<Employe> employes) {
        return employeMapper.toDtoList(serviceEmploye.addEmployes(employes));
    }

    @Operation(description = "Modifier un employé")
    @PutMapping
    public EmployeDTO updateEmploye(@RequestBody Employe employe) {
        return employeMapper.toDto(serviceEmploye.updateEmploye(employe));
    }

    @Operation(description = "Supprimer un employé par son id")
    @DeleteMapping("/{idEmploye}")
    public void removeEmploye(@PathVariable Long idEmploye) {
        serviceEmploye.removeEmploye(idEmploye);
    }
}
