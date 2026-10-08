package tn.esprit.autolock.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutReservation statut;

    // Plusieurs réservations pour un même client (côté propriétaire : colonne id_client)
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    // Plusieurs réservations pour un même véhicule (côté propriétaire : colonne id_vehicule)
    @ManyToOne
    @JoinColumn(name = "id_vehicule")
    private Vehicule vehicule;

    // Une réservation donne lieu à un seul contrat (côté inverse : la clé étrangère est dans contrat)
    @OneToOne(mappedBy = "reservation")
    private Contrat contrat;
}
