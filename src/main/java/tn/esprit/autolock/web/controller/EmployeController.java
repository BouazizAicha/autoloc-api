package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Employe;
import tn.esprit.autolock.dto.EmployeDTO;
import tn.esprit.autolock.mapper.EmployeMapper;
import tn.esprit.autolock.service.IEmployeService;

import java.util.List;

@Tag(name = "Gestion des employes")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IEmployeService employeService;
    private final EmployeMapper employeMapper;

    @Operation(description = "Récupérer la liste complète (employes)")
    @GetMapping
    public List<EmployeDTO> findAll() {
        return employeMapper.toDtoList(employeService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public EmployeDTO findById(@PathVariable("id") Long id) {
        return employeMapper.toDto(employeService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public EmployeDTO create(@RequestBody Employe employe) {
        return employeMapper.toDto(employeService.create(employe));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public EmployeDTO update(@PathVariable("id") Long id, @RequestBody Employe employe) {
        return employeMapper.toDto(employeService.update(id, employe));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        employeService.deleteById(id);
    }
}
