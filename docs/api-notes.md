# Notes sur l'API REST — AutoLoc (Atelier 5)

URL de base : `http://localhost:8081/api` (le préfixe `/api` vient de `server.servlet.context-path`).
Documentation : `/api/swagger-ui.html` (interface) et `/api/v3/api-docs` (JSON OpenAPI).

## A. Endpoints

Chaque contrôleur dépend uniquement de l'interface `I…Service` (injection par constructeur) et d'un mapper Entité → DTO (Chapitre 10). Les 404 viennent de `ResourceNotFoundException` et les 400 de `IllegalArgumentException`, traduites par `GlobalExceptionHandler`.

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| Agence, Client, Contrat, Employé, Équipement, Maintenance, Réservation, Véhicule | GET `/api/{ressource}` | `findAll()` | 200 | — |
| (idem) | GET `/api/{ressource}/{id}` | `findById(id)` | 200 | 404 |
| (idem) | POST `/api/{ressource}` | `create(x)` | 201 | 400 |
| (idem) | PUT `/api/{ressource}/{id}` | `update(id, x)` | 200 | 404, 400 |
| (idem) | DELETE `/api/{ressource}/{id}` | `deleteById(id)` | 204 | 404 |
| Paiement | GET `/api/paiements` | `findAll()` | 200 | — |
| Paiement | GET `/api/paiements/{id}` | `findById(id)` | 200 | 404 |
| Paiement | POST / PUT / DELETE | — (non exposés) | — | 405 |

Ressources : `agences`, `clients`, `contrats`, `employes`, `equipements`, `maintenances`, `reservations`, `vehicules`.

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
|---|---|---|
| Contrat → Paiement (`paiements`) | `@JsonManagedReference("contrat-paiements")` côté liste | Composition : les paiements sont créés dans le JSON de leur contrat (cascade ALL). |
| Paiement → Contrat (`contrat`) | `@JsonBackReference("contrat-paiements")` côté parent | Coupe la boucle de sérialisation et rattache automatiquement chaque paiement à son contrat à la lecture du JSON. Même nom que le `@JsonManagedReference`. |
| Agence → Véhicule / Employé, Client → Réservation, Véhicule → Réservation / Maintenance / Équipement, Équipement → Véhicule, Réservation → Contrat | `@JsonIgnore` sur la liste (côté inverse) | L'enfant est créé seul et désigne son parent par un identifiant (`{ "agence": {"idAgence": 1} }`) : on ne veut pas créer les enfants depuis le parent. |

**Différence `@JsonBackReference` / `@JsonIgnore`** : `@JsonBackReference` est lié à un `@JsonManagedReference` et reconstruit le lien parent ← enfant à la lecture du JSON ; `@JsonIgnore` ignore simplement la propriété, dans les deux sens, sans reconstruire de lien. Sur `Reservation.client`, un `@JsonBackReference` serait faux : la propriété serait ignorée à la lecture et le POST créerait une réservation sans client.

Les réponses renvoient des DTO (et non les entités), donc aucune boucle de sérialisation côté réponse.

## C. Postman et Swagger

- **Postman** sert à **tester** : requêtes rejouables avec des assertions (statut attendu) et des variables d'environnement. Exemple rencontré : la requête « Consulter un id inexistant » (GET `/api/contrats/9999`) vérifie qu'on obtient bien un 404 avec un corps JSON lisible, et le scénario « Créer un contrat avec ses paiements » vérifie la cascade.
- **Swagger UI** sert à **documenter et explorer** : le contrat de l'API est généré depuis le code (tags, résumés, codes de réponse) et reste synchronisé avec lui. Exemple : le groupe « Paiements » n'affiche que deux GET, ce qui documente la lecture seule.

Ils sont complémentaires : Swagger décrit l'API, Postman la valide.

## D. Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| `@ApiResponses({ @ApiResponse(...), @ApiResponse(...) })` dans les 9 contrôleurs (« Remove the 'ApiResponses' wrapper ») | java:S1710 « Annotation repetitions should not be wrapped » : depuis Java 8, on répète directement l'annotation, sans conteneur, pour un code plus lisible. | Suppression du conteneur `@ApiResponses` : les `@ApiResponse` sont maintenant répétées directement sur la méthode. |
| Entité JPA utilisée dans `@RequestBody` (« Replace this persistent entity with a simple POJO or DTO object ») | Règle S4684 : ne pas exposer d'entité persistante en paramètre d'une méthode de contrôleur (couplage API/base, risque de modification de champs internes). | Non corrigée dans cet atelier : le sujet expose volontairement les entités en entrée ; le remplacement par des DTO de requête relève du Chapitre 10. |

## E. Questions

1. **Quel composant reçoit les requêtes ?** Le `DispatcherServlet`. Il interroge le `HandlerMapping`, qui retrouve la méthode du contrôleur à partir du verbe HTTP et de l'URL.
2. **Pourquoi dépendre de `IContratService` ?** Le contrôleur ne porte pas de logique métier : il délègue au service. Il dépend de l'interface (et non de `ContratServiceImpl`) pour rester découplé et remplaçable, et jamais du repository, pour respecter les couches.
3. **201, 204 et 404 ?** `POST` crée une ressource : 201 Created. `DELETE` réussi n'a rien à renvoyer : 204 No Content. Une ressource absente est une erreur du client (mauvais identifiant) et non une panne du serveur : 404 et non 500.
4. **Pourquoi `PaiementController` n'expose que des GET ?** Un paiement n'existe que rattaché à son contrat (composition) : il se crée, se modifie et se supprime à travers le Contrat. Un POST sur `/api/paiements` reçoit un 405 Method Not Allowed.
5. **Risques de l'exposition directe des entités et choix des annotations ?** Couplage entre l'API et la base, champs internes visibles, boucles de sérialisation, proxies LAZY non sérialisables. `Contrat.paiements` utilise `@JsonManagedReference` car les paiements sont créés dans le JSON du contrat (composition). `Agence.vehicules` utilise `@JsonIgnore` car un véhicule survit à son agence et se crée seul, en désignant son agence par un identifiant.
