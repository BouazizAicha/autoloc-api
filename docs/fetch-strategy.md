# Stratégie de fetch et de cascade — AutoLoc (Atelier 2)

## Principes retenus

- **Fetch : LAZY partout.** Par défaut, `@ManyToOne` et `@OneToOne` sont en EAGER : charger une réservation chargerait son client, son véhicule, l'agence du véhicule, etc. (effet « chaîne »). On force donc `FetchType.LAZY` sur toutes les associations. Les collections (`@OneToMany`, `@ManyToMany`) sont déjà LAZY par défaut ; on le précise explicitement par lisibilité.
- **Cascade : uniquement en cas de composition.** Une cascade n'est justifiée que si l'enfant ne peut pas vivre sans son parent. Dans tous les autres cas (agrégation), aucune cascade de suppression n'est appliquée.
- **Côté propriétaire** = côté qui porte la clé étrangère (`@ManyToOne` / `@JoinColumn`). Le côté inverse utilise `mappedBy` et ne crée aucune colonne.
- **Lombok ciblé** (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`), sans `@Data`, pour éviter les `toString`/`hashCode` qui parcourent les deux côtés d'une association bidirectionnelle (StackOverflowError).

## Tableau de justification

| Association | Côté propriétaire | Fetch | Cascade | Justification |
|---|---|---|---|---|
| Contrat → Paiement | Paiement (`id_contrat`) | LAZY | ALL + orphanRemoval | Composition : un paiement n'existe que rattaché à son contrat ; supprimer le contrat supprime ses paiements, et retirer un paiement de la liste le supprime en base. |
| Agence → Véhicule | Véhicule (`id_agence`) | LAZY | Aucune | Un véhicule survit à la suppression de son agence : il ne doit pas être supprimé avec elle. Une agence peut avoir beaucoup de véhicules, inutile de les charger systématiquement. |
| Agence → Employé | Employé (`id_agence`) | LAZY | Aucune | Un employé existe indépendamment de son agence (il peut être réaffecté). Pas de suppression en chaîne. |
| Véhicule ↔ Équipement | Véhicule (`@JoinTable vehicule_equipement`) | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules : supprimer un véhicule ne doit pas supprimer un équipement utilisé ailleurs. Utilisation d'un `Set` pour éviter les doublons et les problèmes de « bag » Hibernate. |
| Client → Réservation | Réservation (`id_client`) | LAZY | PERSIST | Enregistrer un nouveau client avec ses réservations les enregistre aussi. Pas de REMOVE : l'historique des réservations ne doit pas disparaître avec le client par accident. |
| Réservation → Véhicule | Réservation (`id_vehicule`) | LAZY | Aucune | Le véhicule existe indépendamment de ses réservations. |
| Réservation ↔ Contrat | Contrat (`id_reservation`, unique) | LAZY | ALL (côté Réservation) | Le contrat n'a de sens que pour sa réservation (composition) : il suit son cycle de vie. Limite : un `@OneToOne` côté inverse (`mappedBy`) ne peut pas être réellement LAZY sans amélioration de bytecode, Hibernate doit interroger la base pour savoir si le contrat existe. |
| Véhicule → Maintenance | Maintenance (`id_vehicule`) | LAZY | PERSIST | Une maintenance planifiée avec un nouveau véhicule est enregistrée avec lui. Pas de REMOVE : l'historique de maintenance est conservé. |

## Conséquence technique : LAZY et sérialisation JSON

Avec LAZY, les relations sont des proxies Hibernate : les sérialiser directement en JSON poserait problème. C'est une raison supplémentaire d'avoir introduit les DTO (Chapitre 10) : les contrôleurs renvoient des DTO qui ne contiennent que des champs simples et les identifiants des relations, jamais les entités JPA. Les collections inverses restent en plus protégées par `@JsonIgnore`.

## Point d'attention : orphanRemoval et mise à jour

Les paiements d'un contrat ne sont pas envoyés dans le corps JSON d'un `PUT /contrats` (`@JsonIgnore`). Sans précaution, `orphanRemoval = true` verrait une liste vide et supprimerait tous les paiements. Le service `updateContrat` recharge donc le contrat existant et conserve sa liste de paiements avant la sauvegarde.
