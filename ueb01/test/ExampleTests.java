import org.junit.Assert;
import org.junit.Test;

import simplex.Fraction;
import simplex.LinearProgram;
import simplex.LinearProgram.Restriction;
import simplex.LinearProgram.Restriction.Type;
import simplex.LinearProgram.SolveType;
import simplex.SimplexSolver;

/**
 * Beispieltests für die Klassen Fraction und SimplexSolver
 * 
 * @author kar, mhe
 *
 */
public class ExampleTests {
    /** Hilfsmethode zum Erstellen einer Fraction-Instanz */
    public static Fraction f(long numerator) {
        return new Fraction(numerator);
    }

    /** Hilfsmethode zum Erstellen einer Fraction-Instanz */
    public static Fraction f(long numerator, long denominator) {
        return new Fraction(numerator, denominator);
    }

    /** Hilfsmethode zum Erstellen eines Fraction-Arrays */
    public static Fraction[] fs(Fraction... fs) {
        return fs;
    }

    /** Hilfsmethode zum Erstellen einer Fraction-Matrix */
    public static Fraction[][] fss(Fraction[]... fss) {
        return fss;
    }

    /** Hilfsmethode zum Erstellen einer Restriction-Instanz */
    public static Restriction r(Fraction[] term, Type type, Fraction rightSide) {
        return new Restriction(term, type, rightSide);
    }

    /** Hilfsmethode zum Erstellen eines Restriction-Arrays */
    public static Restriction[] rs(Restriction... rs) {
        return rs;
    }

    /** Hilfsmethode zum Erstellen eines int-Arrays */
    public static int[] ints(int... ints) {
        return ints;
    }

    @Test
    public void fraction_generate() {

        Fraction oneFraction = f(5, 10);

        Assert.assertEquals(1, oneFraction.getNumerator());
        Assert.assertEquals(2, oneFraction.getDenominator());
    }

    @Test
    public void fraction_generate_negative() {

        Fraction oneFraction = f(-20, 10);
        Fraction twoFraction = f(-4, 3);

        Assert.assertEquals(-2, oneFraction.getNumerator());
        Assert.assertEquals(1, oneFraction.getDenominator());

        Assert.assertEquals(-4, twoFraction.getNumerator());
        Assert.assertEquals(3, twoFraction.getDenominator());
    }

    @Test
    public void fraction_add() {

        Fraction oneFraction = f(5, 10);
        Fraction twoFraction = f(5, 10);

        Fraction resuFraction = oneFraction.add(twoFraction);
        Fraction compaFraction = f(1);

        Assert.assertEquals(compaFraction, resuFraction);
    }

    @Test
    public void fraction_add_negative() {

        Fraction oneFraction = f(-15, 10);
        Fraction twoFraction = f(5, 10);

        Fraction resuFraction = oneFraction.add(twoFraction);
        Fraction compaFraction = f(-10, 10);

        Assert.assertEquals(compaFraction, resuFraction);
    }

    @Test
    public void fraction_add_negative_two() {

        Fraction oneFraction = f(-145, 21);
        Fraction twoFraction = f(47, 5);

        Fraction resuFraction = oneFraction.add(twoFraction);
        Fraction compaFraction = f(262, 105);

        Assert.assertEquals(compaFraction, resuFraction);
    }

    @Test
    public void fraction_sub1() {

        Fraction oneFraction = f(5, 10);
        Fraction twoFraction = f(5, 10);

        Fraction resuFraction = oneFraction.subtract(twoFraction);
        Fraction compaFraction = Fraction.ZERO;

        Assert.assertEquals(compaFraction, resuFraction);
    }

    @Test
    public void fraction_sub2() {

        Fraction oneFraction = f(2, 8);
        Fraction twoFraction = f(3, 4);

        Fraction resuFraction = twoFraction.subtract(oneFraction);
        Fraction compaFraction = f(1, 2);

        Assert.assertEquals(compaFraction, resuFraction);
    }

    @Test
    public void fraction_sub_neg() {

        Fraction oneFraction = f(-2, 8);
        Fraction twoFraction = f(-16, 41);

        Fraction resuFraction = oneFraction.subtract(twoFraction);
        Fraction compaFraction = f(23, 164);

        Assert.assertEquals(compaFraction, resuFraction);
    }

    @Test
    public void fraction_sub_neg2() {

        Fraction oneFraction = f(-2, 82);
        Fraction twoFraction = f(16, 41);

        Fraction resuFraction = oneFraction.subtract(twoFraction);
        Fraction compaFraction = f(-17, 41);

        Assert.assertEquals(compaFraction, resuFraction);
    }

    @Test
    public void fraction_multiplyBy() {
        Assert.assertEquals("2/3 * 3/4", f(1, 2), f(2, 3).multiplyBy(f(3, 4)));
    }

    @Test
    public void fraction_multiplyBy_neg() {
        Assert.assertEquals("-3/2 * -18/154", f(27, 154), f(-3, 2).multiplyBy(f(-18, 154)));
    }

    @Test
    public void fraction_divideBy() {
        Assert.assertEquals("1/2 / 3/4", f(2, 3), f(1, 2).divideBy(f(3, 4)));
    }

    @Test
    public void fraction_divideBy_neg() {
        Assert.assertEquals("-3/2 / -18/154", f(77, 6), f(-3, 2).divideBy(f(-18, 154)));
    }

    @Test
    public void fraction_compareTo() {
        Assert.assertTrue("2/3 > 3/7", f(2, 3).compareTo(f(3, 7)) > 0);
    }

    @Test
    public void fraction_compareTo2() {
        Assert.assertTrue("2/3 > 4/6", f(2, 3).compareTo(f(4, 6)) == 0);
    }

    @Test
    public void fraction_compareTo_neg1() {
        Assert.assertTrue("-2/3 < 3/7", f(-2, 3).compareTo(f(3, 7)) < 0);
    }

    @Test
    public void fraction_compareTo_neg2() {
        Assert.assertTrue("12/18 > -1872/12314", f(12, 18).compareTo(f(-1872, 12314)) > 0);
    }

    @Test
    public void simplex() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(r(fs(f(4), f(3)), Type.LE, f(320)),
                r(fs(f(2), f(4)), Type.GE, f(100)), r(fs(f(3), f(3)), Type.EQ, f(270))),
                SolveType.MIN, f(2), f(8)));

        Assert.assertFalse("Beispiel 1 (Ausgangstableau): isValidSolution", s.isValidSolution());
        Assert.assertArrayEquals("Beispiel 1 (Ausgangstableau): getTable",
                fss(fs(f(4), f(3), f(1), f(0), f(0), f(0), f(0), f(0), f(320)),
                        fs(f(2), f(4), f(0), f(-1), f(0), f(0), f(1), f(0), f(100)),
                        fs(f(3), f(3), f(0), f(0), f(0), f(0), f(0), f(1), f(270)),
                        fs(f(-2), f(-8), f(0), f(0), f(0), f(0), f(0), f(0), f(0))),
                s.getTable());
        Assert.assertArrayEquals("Beispiel 1 (Ausgangstableau): getBaseVars", ints(2, 6, 7),
                s.getBaseVars());

        Assert.assertArrayEquals("Beispiel 1: solve", fs(f(50), f(40), f(420)), s.solve());
        Assert.assertTrue("Beispiel 1 (Lösung): isValidSolution", s.isValidSolution());
        Assert.assertArrayEquals("Beispiel 1 (Lösung): getBaseVars", ints(3, 0, 1),
                s.getBaseVars());
        Assert.assertArrayEquals("Beispiel 1 (Lösung): getTable",
                fss(fs(f(0), f(0), f(-2), f(1), f(0), f(0), f(-1), f(10, 3), f(160)),
                        fs(f(1), f(0), f(1), f(0), f(0), f(0), f(0), f(-1), f(50)),
                        fs(f(0), f(1), f(-1), f(0), f(0), f(0), f(0), f(4, 3), f(40)),
                        fs(f(0), f(0), f(-6), f(0), f(0), f(0), f(0), f(26, 3), f(420))),
                s.getTable());
    }

    @Test
    public void simplex_2() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(r(fs(f(2), f(1)), Type.LE, f(600)),
                r(fs(f(5), f(4)), Type.LE, f(1000)), r(fs(f(0), f(2)), Type.GE, f(150))),
                SolveType.MIN, f(3), f(4)));

        Assert.assertArrayEquals("simplex_nonSolved: solve", fs(f(0), f(75), f(300)), s.solve());
    }

    @Test
    public void simplex_3() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(r(fs(f(2), f(1)), Type.LE, f(600)),
                r(fs(f(5), f(4)), Type.LE, f(1000)), r(fs(f(0), f(2)), Type.GE, f(150))),
                SolveType.MAX, f(3), f(4)));

        Assert.assertArrayEquals("simplex_nonSolved: solve", fs(f(0), f(250), f(1000)), s.solve());
    }

    @Test
    public void simplex4() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(r(fs(f(2), f(1)), Type.LE, f(18)),
                r(fs(f(2), f(3)), Type.LE, f(42)), r(fs(f(3), f(1)), Type.LE, f(24))),
                SolveType.MAX, f(3), f(2)));

        Assert.assertArrayEquals("Beispiel 1: solve", fs(f(3), f(12), f(33)), s.solve());

    }

    @Test
    public void simplex5() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(r(fs(f(2), f(1)), Type.GE, f(18)),
                r(fs(f(2), f(3)), Type.GE, f(42)), r(fs(f(3), f(1)), Type.LE, f(24))),
                SolveType.MAX, f(3), f(2)));

        Assert.assertArrayEquals("Beispiel 1: solve", fs(f(0), f(24), f(48)), s.solve());

    }
}
