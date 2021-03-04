package simplex;

/**
 * Ein lineares Optimierungproblem
 * 
 * Diese Klasse ist komplett vorgegeben und darf nicht verändert werden.
 * 
 * @author kar, mhe
 *
 */
public class LinearProgram {
    /**
     * Eine Restriktion
     */
    public static class Restriction {
        /** Typ der Restriktion */
        public enum Type {
            /** &le; */
            LE,

            /** = */
            EQ,

            /** &ge; */
            GE
        };

        /** Koeffizienten des Terms (der linken Seite der Restriktion) */
        private final Fraction[] term;

        /** Typ der Restriktion (&le;, = oder &ge;) */
        private final Type type;

        /** Wert der rechten Seite */
        private final Fraction rightSide;

        /**
         * Konstruktor.
         * 
         * @param term zu nutzende Koeffizienten des Terms (der linken Seite der Restriktion)
         * @param type zu nutzender Restriktionstyp
         * @param rightSide zu nutzender Wert der rechten Seite der Restriktion
         * @pre term != null
         * @pre term hat mindestens einen Koeffizienten
         * @pre type != null
         * @pre rightSide != null
         * @pre rightSide ist nicht negativ
         */
        public Restriction(Fraction[] term, Type type, Fraction rightSide) {
            assert term != null;
            assert term.length >= 1;
            assert type != null;
            assert rightSide != null;
            assert rightSide.compareTo(Fraction.ZERO) >= 0;

            this.term = term;
            this.type = type;
            this.rightSide = rightSide;
        }

        /**
         * @return Koeffizienten des Restriktions-Terms
         */
        public Fraction[] getTerm() {
            return term;
        }

        /**
         * @return Restriktionstyp
         */
        public Type getType() {
            return type;
        }

        /**
         * @return Wert der rechten Seite
         */
        public Fraction getRightSide() {
            return rightSide;
        }
    }

    /**
     * Optimierungsrichtung für den Wert den Zielfunktion
     */
    public enum SolveType {
        /** Minimierung */
        MIN,

        /** Maximierung */
        MAX
    };

    /** Restriktionen des linearen Optimierungproblems */
    private final Restriction[] restrictions;

    /** Koeffizienten der Zielfunktion */
    private final Fraction[] objectiveTerm;

    /** Optimierungsrichtung */
    private final SolveType solveType;

    /**
     * Konstruktor.
     * 
     * @param restrictions zu nutzende Restriktionen
     * @param solveType zu nutzende Optimierungsrichtung
     * @param objectiveTerm zu nutzende Koeffizienten der Zielfunktion
     * @pre restrictions != null
     * @pre objectiveTerm != null
     * @pre objectiveTerm hat mindestens einen Koeffizienten
     * @pre alle restrictions haben genau so viele Koeffizienten wie objectiveTerm
     * @pre es gibt mindestens so viele restrictions wie objectiveTerm Koeffizienten hat
     */
    public LinearProgram(Restriction[] restrictions, SolveType solveType,
            Fraction... objectiveTerm) {
        assert restrictions != null;
        assert objectiveTerm != null;
        assert objectiveTerm.length >= 1;
        assert correctLength(objectiveTerm.length, restrictions);
        assert restrictions.length >= objectiveTerm.length;
        
        this.restrictions = restrictions;
        this.solveType = solveType;
        this.objectiveTerm = objectiveTerm;
    }

    /**
     * @return die Restriktionen
     */
    public Restriction[] getRestrictions() {
        return restrictions;
    }

    /**
     * @return die Optimierungsrichtung
     */
    public SolveType getSolveType() {
        return solveType;
    }

    /**
     * @return die Koeffizienten der Zielfunktion
     */
    public Fraction[] getObjectiveTerm() {
        return objectiveTerm;
    }

    /**
     * Gibt zurück, ob die übergebenen Restriktionen die erwartete Anzahl an Koeffizienten haben.
     * 
     * @param length erwartete Anzahl an Koeffizienten
     * @param restrictions zu prüfende Restriktionen
     * @return true, wenn alle restrictions length Koeffizienten haben, ansonsten false
     */
    private static boolean correctLength(int length, Restriction[] restrictions) {
        for (Restriction r : restrictions) {
            if (r.term.length != length) {
                return false;
            }
        }
        return true;
    }

}
