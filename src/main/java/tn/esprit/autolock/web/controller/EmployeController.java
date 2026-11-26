package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Employe;
import tn.esprit.autolock.dto.EmployeDTO;
import tn.esprit.autolock.mapper.EmployeMapper;
import tn.esprit.autolock.service.IEmployeService;

import java.util.List;

@Tag(name = "Employés", description = "Gestion des employés des agences")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IEmployeService employeService;
    private final EmployeMapper employeMapper;

    @Operation(summary = "Lister les employes")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<EmployeDTO> findAll() {
        return employeMapper.toDtoList(employeService.findAll());
    }

    @Operation(summary = "Consulter un employé")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public EmployeDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return employeMapper.toDto(employeService.findById(id));
    }

    @Operation(summary = "Créer un employé")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDTO create(@RequestBody Employe employe) {
        return employeMapper.toDto(employeService.create(employe));
    }

    @Operation(summary = "Modifier un employé")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public EmployeDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Employe employe) {
        return employeMapper.toDto(employeService.update(id, employe));
    }

    @Operation(summary = "Supprimer un employé")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        employeService.deleteById(id);
    }
}
