package tn.esprit.autolock.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Calcul du montant d'un contrat : tarif journalier x nombre de jours de la réservation (minimum 1 jour)
public final class MontantCalculator {

    private MontantCalculator() {
    }

    public static BigDecimal calculer(BigDecimal tarifJournalier, LocalDate debut, LocalDate fin) {
        if (tarifJournalier == null || debut == null || fin == null) {
            return BigDecimal.ZERO;
        }
        long jours = Math.max(1, ChronoUnit.DAYS.between(debut, fin));
        return tarifJournalier.multiply(BigDecimal.valueOf(jours));
    }
}
