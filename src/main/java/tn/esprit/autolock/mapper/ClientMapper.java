package tn.esprit.autolock.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autolock.domain.Client;
import tn.esprit.autolock.dto.ClientDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Mapper manuel Entité -> DTO (Chapitre 10) : ne copie que les champs utiles à l'API
@Component
public class ClientMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String fmt(LocalDate date) {
        return date == null ? null : date.format(FORMATTER);
    }

    public ClientDTO toDto(Client e) {
        if (e == null) {
            return null;
        }
        return new ClientDTO(
                e.getIdClient(),
                e.getNom(),
                e.getPrenom(),
                e.getEmail(),
                e.getTelephone(),
                fmt(e.getDateInscription()));
    }

    public List<ClientDTO> toDtoList(List<Client> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
