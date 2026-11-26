# Notes sur la couche Repository — AutoLoc (Atelier 3)

## Interfaces créées

Les neuf interfaces sont dans le package `tn.esprit.autolock.repository`, nommées avec le préfixe `I`, et étendent toutes `JpaRepository<Entité, Long>`.

| Interface | Étend | Justification |
|---|---|---|
| IAgenceRepository | `JpaRepository<Agence, Long>` | CRUD complet, `findAll` renvoie une `List`, tri/pagination et `flush` disponibles. |
| IEmployeRepository | `JpaRepository<Employe, Long>` | Idem : une seule interface couvre CRUD, listes, tri et pagination. |
| IVehiculeRepository | `JpaRepository<Vehicule, Long>` | Idem ; la pagination sera utile pour une liste de véhicules importante. |
| IEquipementRepository | `JpaRepository<Equipement, Long>` | Idem. |
| IClientRepository | `JpaRepository<Client, Long>` | Idem. |
| IReservationRepository | `JpaRepository<Reservation, Long>` | Idem ; tri par date possible avec `Sort`. |
| IContratRepository | `JpaRepository<Contrat, Long>` | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible. Les suppressions via `deleteById` appliquent la cascade et l'orphanRemoval vers les paiements. |
| IPaiementRepository | `JpaRepository<Paiement, Long>` | Créé pour pouvoir lire les paiements. Règle : le code métier passe par le Contrat pour créer ou retirer un paiement. |
| IMaintenanceRepository | `JpaRepository<Maintenance, Long>` | Idem. |

**Pourquoi `JpaRepository` plutôt que `CrudRepository` ?** `CrudRepository` renvoie un `Iterable` pour `findAll`, moins pratique. `JpaRepository` réunit CRUD, listes (`List`), tri, pagination et méthodes JPA (`flush`, `saveAndFlush`). C'est l'interface retenue pour AutoLoc. Aucune annotation `@Repository` n'est nécessaire : Spring Data génère le proxy au démarrage.

**Limite à retenir :** les suppressions en lot (`deleteAllInBatch`, `deleteAllByIdInBatch`) contournent le contexte de persistance : la cascade et l'orphanRemoval ne s'appliquent pas, ce qui laisserait des paiements orphelins ou ferait échouer la requête sur la clé étrangère. On utilise donc `deleteById` pour les contrats.

## Anomalies SonarQube for IDE

| Anomalie SonarQube for IDE | Règle / explication | Correction apportée |
|---|---|---|
| Import générique `import jakarta.persistence.*;` dans les 9 entités | Éviter les imports avec joker : on ne voit plus quelles classes sont utilisées et des conflits de noms sont possibles. | Remplacé par des imports explicites (`Entity`, `Id`, `Column`, `ManyToOne`, etc.). |
| à compléter depuis le panneau SonarQube | à compléter | à compléter |
| à compléter depuis le panneau SonarQube | à compléter | à compléter |
