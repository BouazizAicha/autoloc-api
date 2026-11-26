# Notes sur la couche Service — AutoLoc (Atelier 4)

## A. Modes d'injection comparés (étape 2)

| Critère | Injection par constructeur (retenue) | Injection par attribut (`@Autowired` sur le champ) |
|---|---|---|
| Champ `final` possible ? | Oui (`private final IContratRepository contratRepository;`) | Non : le champ doit être modifiable pour que Spring l'affecte. |
| Dépendance visible dans la signature de la classe ? | Oui : elle apparaît dans le constructeur. | Non : dépendance cachée à l'intérieur de la classe. |
| Utilisable avec `new` hors conteneur ? | Oui, mais on est obligé de fournir le repository : l'objet est toujours valide. | Oui, mais le champ reste `null` : un appel de méthode lève une `NullPointerException`. |
| Signalé par SonarQube for IDE ? | Non. | Oui : règle S6813 (« field injection »), selon l'énoncé de l'atelier. À confirmer dans le panneau. |

Conclusion : constructeur + champ `final` + dépendance typée par interface, avec `@RequiredArgsConstructor` de Lombok, pour les neuf services.

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Une seule dépendance, obligatoire, typée par interface ; champ `final`, service sans état. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Idem. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Idem. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Idem. |
| ClientServiceImpl | IClientRepository | Constructeur | Idem. |
| ReservationServiceImpl | IReservationRepository | Constructeur | Idem. |
| ContratServiceImpl | IContratRepository | Constructeur | Idem ; la suppression d'un contrat supprime ses paiements par cascade. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Idem ; service en lecture seule (les paiements passent par le Contrat). |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Idem. |

Règles de gestion implémentées (lancent `IllegalArgumentException` avec un message clair) :

| Entité | Règle |
|---|---|
| Agence | nom, ville et adresse obligatoires |
| Employé | nom et prénom obligatoires |
| Véhicule | tarif journalier non négatif |
| Équipement | libellé obligatoire |
| Client | nom et e-mail obligatoires |
| Réservation | date de fin non antérieure à la date de début |
| Contrat | montant total non négatif |
| Paiement | lecture seule |
| Maintenance | date de fin non antérieure à la date de début (l'entité n'a pas de champ « coût ») |

## C. Messages d'erreur du conteneur (étape 3)

**Message 1** — `NoSuchBeanDefinitionException` (enveloppée dans `UnsatisfiedDependencyException`) : le contrôleur demande un bean de type `IContratService` et le conteneur n'en trouve aucun.
- Causes les plus probables côté `ContratServiceImpl` : (1) l'annotation `@Service` a été oubliée sur la classe ; (2) la classe est placée en dehors du package racine scanné (`tn.esprit.autolock` et ses sous-packages).
- Correction : ajouter `@Service` et vérifier le package.

**Message 2** — `NoUniqueBeanDefinitionException` : deux beans du même type (`emailNotificateur` et `smsNotificateur`), Spring ne sait pas lequel injecter dans `NotificationController`.
- Correction 1 : `@Primary` **sur la classe** du bean à utiliser par défaut (par exemple `EmailNotificateur`).
- Correction 2 : `@Qualifier("emailNotificateur")` **sur le paramètre du constructeur** du contrôleur, pour désigner explicitement le bean voulu.

**Message 3** — `BeanCurrentlyInCreationException` : dépendance circulaire entre `ClientServiceImpl` et `ReservationServiceImpl`.
- Avec l'injection par constructeur, Spring doit créer chaque bean complet avant de le passer à l'autre : il ne peut donc créer ni l'un ni l'autre, et le cycle ne peut pas être contourné (contrairement à l'injection par attribut, qui le masque).
- Piste de conception : extraire la responsabilité commune dans un troisième service (ou faire dépendre un seul des deux du repository de l'autre entité) pour que les dépendances ne forment plus de cycle.

## D. Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Import générique `jakarta.persistence.*` dans les entités | Éviter les imports avec joker (imports explicites plus lisibles). | Imports explicites (Atelier 3). |
| Injection par attribut (`@Autowired` sur un champ) dans `ContratServiceImpl` | Règle S6813 selon l'énoncé : préférer l'injection par constructeur. | Retour à `@RequiredArgsConstructor` et champ `final`. |
| Exception générique dans `findById` (`RuntimeException`) | Règle S112 selon l'énoncé : ne pas lever d'exception générique ; utiliser une exception métier. | Retour à `ResourceNotFoundException`. |

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?** Il n'est écrit nulle part dans notre code : c'est le conteneur Spring qui l'exécute au démarrage (`SpringApplication.run`). Il détecte la classe grâce à `@Service` et au scan, appelle son constructeur et lui fournit le proxy de `IContratRepository`.
2. **Pourquoi dépendre de `IContratService` et non de `ContratServiceImpl` ?** Pour réduire le couplage : on peut changer d'implémentation sans toucher au contrôleur, et remplacer le service par une version de test.
3. **Pourquoi un service singleton doit-il rester sans état ?** Une seule instance est partagée par tous les threads (toutes les requêtes). Exemple de bug : un attribut `Contrat contratCourant` modifié par la requête de l'utilisateur A est écrasé par celle de B ; A reçoit alors les données de B.
4. **Pourquoi `update` charge l'entité existante avant `save` ?** Passer directement l'objet reçu écraserait toutes les colonnes (les champs absents du JSON deviendraient `null`) et, avec `orphanRemoval`, une liste de paiements vide ferait supprimer tous les paiements du contrat.
5. **`@Component` vs `@Bean`, `@Primary` vs `@Qualifier`, `IPaiementService` ?** `@Component` se pose sur une classe que l'on possède et qui est détectée par le scan ; `@Bean` est une méthode dans une classe `@Configuration`, pour une classe qu'on ne peut pas annoter (bibliothèque, JDK) ou une construction particulière. `@Primary` désigne le candidat par défaut (posé sur la classe) ; `@Qualifier` fait un choix explicite au point d'injection. `IPaiementService` n'expose ni création, ni modification, ni suppression car un paiement n'existe que rattaché à son contrat (composition) : il se gère à travers le Contrat.
