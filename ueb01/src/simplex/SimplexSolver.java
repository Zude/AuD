package simplex;

import simplex.LinearProgram.Restriction;
import simplex.LinearProgram.Restriction.Type;
import simplex.LinearProgram.SolveType;

/**
 * Ein Automat zum Lösen linearer Optimierungsprobleme
 * 
 * @author kar, mhe, Alexander Loeffler
 * 
 */
public class SimplexSolver {
    /** Simplex-Tableau mit den Zeilen in der ersten und den Spalten in der zweiten Dimension */
    private Fraction[][] table;

    /** Indices der Basisvariablen */
    private int[] baseVars;
    private int numberAiVars;
    private int numberSVars;
    private int numberVars;
    private int pivotRow = -1;
    private int pivotCol = -1;
    private int numberOfRows = -1;
    private int numberOfCols = -1;
    private SolveType solveType;

    /**
     * Erstellt einen Automaten aus dem übergebenen linearen Optimierungsproblem. Der Automat
     * verbleibt im Ausgangstableau, d.h. es werden noch keine Optimierungsschritte durchgeführt.
     * 
     * @param lp lineares Problem, das optimiert werden soll
     * @pre lp != null
     */
    public SimplexSolver(LinearProgram lp) {
        assert lp != null;

        // Klassenvariablen initialisieren
        Restriction[] restrictions = lp.getRestrictions();
        Fraction[] objectiveTerm = lp.getObjectiveTerm();

        solveType = lp.getSolveType();

        numberAiVars = restrictions.length;
        numberSVars = numberAiVars;
        numberVars = restrictions[0].getTerm().length;

        // Table und Base initialisieren
        numberOfRows = restrictions.length + 1;
        numberOfCols = numberVars + numberSVars + numberAiVars + 1;
        table = new Fraction[numberOfRows][numberOfCols];
        baseVars = new int[restrictions.length];

        System.out.println(" ");
        System.out.println("Simplex Solver gestartet!");
        System.out.println("Generiere Table");
        System.out.println(" ");

        // Iterieren ueber den Table und auffuellen der Werte
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {

                Fraction[] currentTerm =
                        (i != table.length - 1) ? restrictions[i].getTerm() : objectiveTerm;
                Fraction rightSide =
                        (i != table.length - 1) ? restrictions[i].getRightSide() : Fraction.ZERO;
                Type currentType = (i != table.length - 1) ? restrictions[i].getType() : Type.EQ;

                // Wenn wir beim koeffizienten einer normalen Var sind
                if (j < numberVars) {

                    // Wenn wir nicht in der Zielfunktion sind
                    if (i != table.length - 1) {
                        table[i][j] = currentTerm[j];
                    } else {

                        // Wenn wir ein Minimirungproblem haben Zielfunktion umkehren
                        table[i][j] = (lp.getSolveType() == SolveType.MIN)
                                ? objectiveTerm[j].multiplyBy(Fraction.MINUS_ONE)
                                : objectiveTerm[j];
                    }

                    // Wenn wir beim koeffizienten einer schlupf Var sind
                } else if (j < numberVars + numberSVars) {

                    // Wenn wir bei der richtigen Schlumpf Var Koeff angekommen sind
                    if (j == numberVars + i) {

                        // Uebtraegt den Wert abhaehngig von dem Typ der aktuellen Restriktion
                        if (currentType == Type.LE) {
                            table[i][j] = (i != table.length - 1) ? Fraction.ONE : Fraction.ZERO;
                        } else if (currentType == Type.GE) {
                            table[i][j] =
                                    (i != table.length - 1) ? Fraction.MINUS_ONE : Fraction.ZERO;
                        } else {
                            table[i][j] = Fraction.ZERO;
                        }

                        if (table[i][j] == Fraction.ONE) {
                            baseVars[i] = j;
                        }
                    } else {
                        table[i][j] = Fraction.ZERO;
                    }

                    // Wenn wir beim koeffizienten einer kuenstichen Var sind
                } else if (j < numberVars + numberSVars + numberAiVars) {

                    // Wenn wir bei der richtigen Kuenstlich Var Koeff angekommen sind
                    if (j == numberVars + numberSVars + i) {

                        // Uebtraegt den Wert abhaehngig von dem Typ der aktuellen Restriktion
                        if (currentType == Type.GE || currentType == Type.EQ) {
                            table[i][j] = (i != table.length - 1) ? Fraction.ONE : Fraction.ZERO;
                        } else {
                            table[i][j] = Fraction.ZERO;
                        }

                        if (table[i][j] == Fraction.ONE) {
                            baseVars[i] = j;
                        }
                    } else {
                        table[i][j] = Fraction.ZERO;
                    }
                } else {
                    // Wenn wir bei dem Funtionwert (ganz rechts) sind
                    table[i][j] = (i != table.length - 1) ? rightSide : Fraction.ZERO;
                }
            }
        }

        printTable();
        printBase();
    }

    /**
     * Gibt eine Referenz auf das Simplex-Tableau zurück.
     * 
     * @return Referenz auf das Simplex-Tableau
     */
    public Fraction[][] getTable() {
        return this.table;
    }

    /**
     * Gibt eine Referenz auf die Indices der Basisvariablen zurück.
     * 
     * @return Referenz auf die Indices der Basisvariablen
     */
    public int[] getBaseVars() {
        return this.baseVars;
    }

    /**
     * Gibt zurück, ob das aktuelle Simplex-Tableau eine gültige Lösung repräsentiert.
     * 
     * @return true, wenn die aktuelle Lösung gültig ist, ansonsten false
     */
    public boolean isValidSolution() {

        for (int baseVar : baseVars) {

            if (isAiVar(baseVar)) {

                System.out.println("Es liegt nocht KEINE Valide Loesung vor!");
                return false;
            }
        }

        System.out.println("Es liegt eine Valide Loesung vor!");
        return true;
    }

    /**
     * Versucht, das lineare Optimierungsproblem zu lösen. Führt dazu - wenn nötig - wiederholt
     * einen Simplexschritt aus, bis das Tableau eine optimale Lösung anzeigt oder es sich als
     * unlösbar erweist. Bei einer optimalen Lösung werden die Koeffizienten und der Wert der
     * Zielfunktion zurückgegeben, andernfalls die null-Referenz.
     * 
     * @return optimale Koeffizienten und Wert der Zielfunktion (in gegebener Reihenfolge, also x1,
     *         x2, ..., xn, z) oder null, wenn unlösbar
     */
    public Fraction[] solve() {

        System.out.println(" ");
        System.out.println("Das Loesen des Tables hat begonnen");
        printValidSolution();
        int index = 1;

        // Wenn die Loesung nicht gueltig ist oder die letze Zeile positive Werte hat wiederhole
        // Simplex Schritte
        while (!isValidSolution() || lastTableRowHasPositiveFraction()) {

            System.out.println("----------------------------------");
            System.out.println("Simplexschritte werden ausgefuehrt");
            System.out.println("Durchlauf: " + index);

            // Waehle Pivotspalte aus
            pivotCol = choosePivotColumn();
            if (pivotCol == -1) {
                System.out.println("Pivotspalte nicht gueltig! Abbruch!");
                return null;
            }

            // Waehle Pivotzeile aus
            pivotRow = choosePivotRow();
            if (pivotRow == -1) {
                System.out.println("Pivotzeile nicht gueltig! Abbruch!");
                return null;
            }

            // Tausche Variablen
            System.out.println(" ");
            System.out.println("Tausche Base in Zeile " + pivotRow + " und Index "
                    + baseVars[pivotRow] + " mit Index " + pivotCol);
            baseVars[pivotRow] = pivotCol;
            Fraction baseValue = table[pivotRow][pivotCol];

            System.out.println("Neue Basis: ");
            printBase();

            // Normiere Werte in Pivotzeile
            for (int i = 0; i < table[pivotRow].length; i++) {

                if (table[pivotRow][i] != null) {
                    table[pivotRow][i] = table[pivotRow][i].divideBy(baseValue);
                }
            }

            System.out.println("Normiere Pivotzeile: ");
            printTable();

            // Reduziere Zeilen
            System.out.println("Reduziere alle Zeilen: ");
            for (int i = 0; i < table.length; i++) {

                if (i != pivotRow) {

                    Fraction valueAtPivot = table[i][pivotCol];

                    for (int j = 0; j < table[i].length; j++) {

                        if (table[i][j] != null && table[pivotRow][j] != null) {
                            table[i][j] = table[i][j]
                                    .subtract(table[pivotRow][j].multiplyBy(valueAtPivot));
                        }
                    }
                }
            }

            printTable();
            System.out.println("----------------------------------");

            index++;
        }

        // Gebe die konkreten Loesungwerte der Variablen und Z aus
        Fraction[] resultFractions = new Fraction[numberVars + 1];

        for (int i = 0; i < numberVars + 1; i++) {
            for (int j = 0; j < baseVars.length; j++) {

                // Waehle die Ergebniswerte fuer die Variablen aus wenn sie Teil der Base sind
                if (i == baseVars[j]) {
                    resultFractions[i] = table[j][numberOfCols - 1];
                } else if (i == numberVars) {

                    if (solveType == SolveType.MAX) {

                        System.out.println(" Maximierungsproblem: Z Wert wird umgekehrt");
                        if (table[numberOfRows - 1][numberOfCols - 1] != null) {
                            resultFractions[i] = table[numberOfRows - 1][numberOfCols - 1]
                                    .multiplyBy(Fraction.MINUS_ONE);
                        }
                    } else {
                        resultFractions[i] = table[numberOfRows - 1][numberOfCols - 1];
                    }
                }
            }
            if (resultFractions[i] == null) {
                resultFractions[i] = Fraction.ZERO;
            }
        }

        System.out.println("Loesung fertig! Ergebnis: ");

        for (Fraction fraction : resultFractions) {
            System.out.println(fraction.toString());
        }

        System.out.println("----------------------------------");
        System.out.println("----------------------------------");

        return resultFractions;
    }

    /**
     * Gibt den Index der zu waehlenden Pivotspalte zurueck
     * 
     * @return Gibt den Index zurueck, der die zu waehlende Pivotspalte repraesentiert
     */
    private int choosePivotColumn() {

        System.out.println(" ");
        System.out.println("Pivotspalte wird ausgewaehlt.");

        int pivotColum = -1;
        Fraction biggestValue = Fraction.ZERO;
        int currentIndex = 0;

        // Wenn wir eine Valide Loesung haben die Groesste Fraction in der Ziele auswaehlen
        if (isValidSolution()) {
            pivotColum = columnWithBiggestPositiveNumer(table[table.length - 1]);
        } else {

            // Wenn keine loesung bilde die Summe fuer jede Spalte und waehlen die groesste Summe
            // bzw den
            Fraction[] columnSums = new Fraction[numberVars + numberSVars];

            for (int i = 0; i < numberVars + numberSVars; i++) {

                Fraction currentFraction = sumInColumnWithAiVar(i);

                if (currentFraction.compareTo(biggestValue) == 1) {

                    biggestValue = currentFraction;
                    pivotColum = currentIndex;
                }

                currentIndex++;
            }
        }

        System.out.println("Pivotspalte gefunden: " + pivotColum);
        System.out.println(" ");
        return pivotColum;
    }

    /**
     * Gibt den Index der zu waehlenden Pivotzeile zurueck
     * 
     * @return Gibt den Index zurueck, der die zu waehlende Pivotzeile repraesentiert
     */
    private int choosePivotRow() {

        System.out.println(" ");
        System.out.println("Pivotzeile wird ausgewaehlt.");

        int pivotRow = -1;
        Fraction smallestPositiveFraction = Fraction.ZERO;
        int index = 0;
        // Fraction[] qFractions = new Fraction[numberAiVars];

        // Quotienten fuer fuer jede Zeile bilden
        for (int i = 0; i < numberAiVars; i++) {

            Fraction rightSideVal = table[i][numberOfCols - 1];
            Fraction pivotColVal = table[i][pivotCol];
            Fraction currentFraction;

            if (pivotColVal.equals(Fraction.ZERO) || pivotColVal.compareTo(Fraction.ZERO) == -1) {
                currentFraction = null;
            } else {
                currentFraction = rightSideVal.divideBy(pivotColVal);
            }

            if (currentFraction != null && (currentFraction.compareTo(Fraction.ZERO) == 1)) {

                if (smallestPositiveFraction.equals(Fraction.ZERO)) {

                    smallestPositiveFraction = currentFraction;
                    pivotRow = index;
                } else if (currentFraction.compareTo(smallestPositiveFraction) == -1) {

                    smallestPositiveFraction = currentFraction;
                    pivotRow = index;
                }
            }

            index++;
        }

        System.out.println("Pivotzeile gefunden: " + pivotRow);

        return pivotRow;
    }

    /**
     * Gibt zurueck in welcher Spalte die Groesste Fraction einer Zeile ist
     * 
     * @param row die Zeile die ueberprueft werden soll
     * @return Der Index der Spalte indem sich die groesste Fraction fuer eine Zeile befindet
     */
    private int columnWithBiggestPositiveNumer(Fraction[] row) {

        int indexForBiggestNumber = -1;
        int currentIndex = 0;
        Fraction biggestValue = Fraction.ZERO;

        for (Fraction fraction : row) {

            if (fraction.compareTo(biggestValue) == 1) {

                biggestValue = fraction;
                indexForBiggestNumber = currentIndex;
            }

            currentIndex++;
        }

        return indexForBiggestNumber;
    }

    /**
     * Gibt zurück, ob die Variable an der uebergebenen Position eine kuenstliche Variable ist
     * 
     * @param column Die Spalte in der sich die Variable befindet (beginnend bei 0)
     * @return true, wenn die Variable eine kuenstliche Variable ist
     */
    private boolean isAiVar(int column) {

        return (column >= (numberVars + numberSVars)
                && column < numberVars + numberSVars + numberAiVars) ? true : false;
    }

    /**
     * Bildet die Fraction Summe in der uebergebenen Spalte fuer jeden Zeile die eine KV in der
     * Basis hat
     * 
     * @param column die Spalte in der die Summe gebildet werden soll
     * @pre column >= 0
     * @pre column < table[0].length
     * @return Liefert die Summe in Fraction der Spalte zurueck
     */
    private Fraction sumInColumnWithAiVar(int column) {
        assert column >= 0;
        assert column < table[0].length;

        Fraction columnSum = Fraction.ZERO;
        int index = 0;

        for (Fraction[] row : table) {

            if (index < table.length - 1 && hasAiVarInBase(index)) {
                columnSum = columnSum.add(row[column]);
            }

            index++;
        }

        return columnSum;
    }

    /**
     * Gibt zurueck, ob eine Zeile in unserem Table eine korrespondierende Kuenstliche Var in der
     * Base hat
     * 
     * @param row die Zeile fuer die geprueft werden soll
     * @pre row >= 0
     * @pre row < baseVars.length
     * @return true wenn die Zeile eine kuenstliche Var in der Base hat
     */
    private boolean hasAiVarInBase(int row) {
        assert row >= 0;
        assert row < baseVars.length;

        return isAiVar(baseVars[row]) ? true : false;
    }

    /**
     * Gibt zurueck ob die Zielfunktionszeile des Tables Positive Werte beinhaltet
     * 
     * @return true wenn die Zeile mindestens einen positiven Fraction Wert hat
     */
    private boolean lastTableRowHasPositiveFraction() {

        for (int i = 0; i < numberVars + numberSVars; i++) {

            Fraction currentFraction = table[table.length - 1][i];

            if (currentFraction.compareTo(Fraction.ZERO) > 0) {

                System.out.println("Loesung noch nicht optimal!");

                return true;
            }
        }

        System.out.println("Loesung ist optimal! Juhu");

        return false;
    }

    /**
     * Gibt den aktuellen Table aus
     */
    private void printTable() {

        System.out.println(" ");
        System.out.println("Aktueller Table: ");

        for (Fraction[] row : table) {

            String rowString = "";

            for (Fraction column : row) {

                if (column.getAsFPN() < 0) {
                    rowString = rowString + column.toString() + " ";
                } else {
                    rowString = rowString + column.toString() + "  ";
                }
            }
            System.out.println(rowString);
        }
        System.out.println(" ");
    }

    /**
     * Gibt die aktuelle Basis aus
     */
    private void printBase() {

        System.out.println(" ");
        System.out.println("Aktuelle Basis: ");

        for (int baseVar : baseVars) {
            System.out.println(baseVar);
        }
        System.out.println(" ");
    }

    /**
     * Gibt aus, ob es eine gueltige Loesung sit
     */
    private void printValidSolution() {

        System.out.println(" ");
        System.out.println("Liegt eine gueltige Loesung vor: " + isValidSolution());
        System.out.println(" ");
    }

}
