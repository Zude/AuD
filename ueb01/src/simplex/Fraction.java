package simplex;

import java.util.Arrays; // darf nur in "toMatrix" verwendet werden

/**
 * Ein vollständig gekürzter mathematischer Bruch, der aus einem Zähler und einem Nenner besteht.
 * Fraction-Instanzen sind unveränderlich, d.h. für Änderungen werden stets neue Instanzen
 * angelegt.
 * 
 * @author kar, mhe, Alexander Loeffler
 */
public class Fraction implements Comparable<Fraction> {

    /** Bruch mit Zähler -1 und Nenner 1 */
    public static final Fraction MINUS_ONE = new Fraction(-1);

    /** Bruch mit Zähler 0 und Nenner 1 */
    public static final Fraction ZERO = new Fraction(0);

    /** Bruch mit Zähler 1 und Nenner 1 */
    public static final Fraction ONE = new Fraction(1);

    /** Der Zähler des Bruchs */
    private final long numerator;

    /** Der Nenner des Bruchs */
    private final long denominator;

    /**
     * Erstellt einen vollständig gekürzten Bruch aus dem übergebenen Zähler und Nenner. Die
     * interne Repräsentation des Nenners ist nicht-negativ. Die Zahl 0 wird durch den Zähler 0
     * und den Nenner 1 dargestellt.
     * 
     * @param numerator Zu verwendender Zähler (beliebige ganze Zahl)
     * @param denominator Zu verwendender Nenner (beliebige ganze Zahl, außer 0)
     * @pre denominator != 0
     */
    public Fraction(long numerator, long denominator) {
        assert denominator != 0;

        if (denominator == 1) {
            this.numerator = numerator;
            this.denominator = denominator;
        } else {

            // Falls der Zaehler negativ ist wird er fuer den ggt * -1 gerechnet.
            long ggt = (numerator < 0) ? ggt(numerator * -1, denominator)
                    : ggt(numerator, denominator);

            this.numerator = numerator / ggt;
            this.denominator = denominator / ggt;
        }
    }

    /**
     * Erstellt einen Bruch aus dem übergebenen Zähler und dem Nenner 1.
     * 
     * @param numerator Zu verwendender Zähler (beliebige ganze Zahl)
     */
    public Fraction(long numerator) {
        this(numerator, 1);
    }

    /**
     * Gibt einen neuen Bruch zurück, der aus der Addition des übergebenen Bruchs entsteht.
     * 
     * @param other zu addierender Bruch (Summand)
     * @return neuer Bruch (Summe)
     * @pre other != null
     */
    public Fraction add(Fraction other) {
        assert other != null;

        // Berechnet den kgv der beiden Zahlen
        long kgv = kgv(this.denominator, other.denominator);

        // Berechnet den Faktor der beiden Zahlen zum Erweitern auf den kgv
        long factorX = kgv / this.denominator;
        long factorY = kgv / other.denominator;

        return new Fraction(this.numerator * factorX + other.numerator * factorY,
                other.denominator * factorY);
    }

    /**
     * Gibt einen neuen Bruch zurück, der aus der Subtraktion des übergebenen Bruchs entsteht.
     * 
     * @param other zu subtrahierender Bruch (Subtrahend)
     * @return neuer Bruch (Differenz)
     * @pre other != null
     */
    public Fraction subtract(Fraction other) {
        assert other != null;

        // Berechnet den kgv der beiden Zahlen
        long kgv = kgv(this.denominator, other.denominator);

        // Berechnet den Faktor der beiden Zahlen zum Erweitern auf den kgv
        long factorX = kgv / this.denominator;
        long factorY = kgv / other.denominator;

        return new Fraction(this.numerator * factorX - other.numerator * factorY,
                this.denominator * factorX);
    }

    /**
     * Gibt einen neuen Bruch zurück, der aus der Multiplikation mit dem übergebenen Bruch
     * entsteht.
     * 
     * @param other zu multiplizierenden Bruch (Faktor)
     * @return neuer Bruch (Produkt)
     * @pre other != null
     */
    public Fraction multiplyBy(Fraction other) {
        assert other != null;

        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator);
    }

    /**
     * Gibt einen neuen Bruch zurück, der aus der Division des übergebenen Bruchs entsteht.
     * 
     * @param other Bruch, durch den geteilt wird (Divisor)
     * @return neuer Bruch (Quotient)
     * @pre other != null
     */
    public Fraction divideBy(Fraction other) {
        assert other != null;

        Fraction firstFraction = this;

        // Gebildet aus dem Kehrwert des zweiten Bruches
        Fraction secondFraction =
                (other.numerator < 0) ? new Fraction(other.denominator * -1, other.numerator * -1)
                        : new Fraction(other.denominator, other.numerator);

        return firstFraction.multiplyBy(secondFraction);
    }

    /**
     * @return der Zähler des Bruchs
     */
    public long getNumerator() {
        return this.numerator;
    }

    /**
     * @return der Nenner des Bruchs
     */
    public long getDenominator() {
        return this.denominator;
    }

    /**
     * @return der Wert des Bruchs als Gleitkommazahl (floating-point number)
     */
    public double getAsFPN() {
        return (double) this.numerator / this.denominator;
    }

    /**
     * @return Stringrepräsentation des Bruchs
     */
    @Override
    public String toString() {
        return this.numerator + (this.denominator == 1 ? "" : "/" + this.denominator);
    }

    /**
     * Vergleicht diesen Bruch mit dem übergebenen Bruch. Gibt eine Zahl kleiner bzw. größer als
     * 0 zurück, wenn die von diesem Bruch repräsentierte Zahl kleiner bzw. größer als die des
     * übergebenen Bruchs ist. Wenn die repräsentierten Zahlen gleich sind, wird 0 zurückgegeben.
     * 
     * @param other Bruch, mit dem dieser Bruch verglichen wird
     * @return Vergleichsergebnis (kleiner, gleich, oder größer 0)
     * @pre other != null
     */
    @Override
    public int compareTo(Fraction other) {
        assert other != null;

        // Berechnet den kgv der beiden Zahlen
        long kgv = kgv(this.denominator, other.denominator);

        // Berechnet den Faktor der beiden Zahlen zum Erweitern auf den kgv
        long factorX = kgv / this.denominator;
        long factorY = kgv / other.denominator;

        if (this.numerator * factorX > other.numerator * factorY) {
            return 1;
        } else if (this.numerator * factorX < other.numerator * factorY) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction other = (Fraction) obj;
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (numerator ^ (numerator >>> 32));
        result = prime * result + (int) (denominator ^ (denominator >>> 32));
        return result;
    }

    /**
     * Gibt die Stringrepräsentation einer übergebenen Fraction-Matrix zurück.
     * 
     * @param fractions Matrix
     * @return Stringrepräsentation von fractions
     * @pre fractions != null
     */
    public static String toMatrix(Fraction[][] fractions) {
        assert fractions != null;

        StringBuilder sb = new StringBuilder();
        for (Fraction[] row : fractions) {
            sb.append(Arrays.toString(row));
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Gibt den groessten gemeinsamen Teiler fuer 2 Zahlen x und y wieder
     * 
     * @param x erste Zahl
     * @param y zweite Zahl
     * @return der ggt der beiden Zahlen
     */
    private long ggt(long x, long y) {

        if (x == 0) {
            return y;
        }

        while (y != 0) {

            if (x > y) {
                x = x - y;
            } else {
                y = y - x;
            }
        }

        return x;
    }

    /**
     * Gibt das kleinste gemeinsames Vielfache fuer 2 Zahlen x und y wieder
     * 
     * @param x erste Zahl
     * @param y zweite Zahl
     * @return der kgv der beiden Zahlen
     */
    private long kgv(long x, long y) {
        long xTemp = x;
        long yTemp = y;

        while (xTemp != yTemp) {
            if (xTemp < yTemp) {
                xTemp += x;
            } else {
                yTemp += y;
            }
        }

        return xTemp;
    }

}
