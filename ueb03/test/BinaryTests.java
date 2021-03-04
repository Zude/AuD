import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import api.ExpressionAPI;
import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.IncompleteContextException;

public class BinaryTests {

    // ************************AND************************//

    @Test
    public final void andBasicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        final Expression result1 = api.makeAndExpression(falsch, falsch);
        final Expression result2 = api.makeAndExpression(wahr, wahr);
        final Expression result3 = api.makeAndExpression(falsch, wahr);
        final Expression result4 = api.makeAndExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(F && F)", result1.toString());
        Assert.assertEquals("(T && T)", result2.toString());
        Assert.assertEquals("(F && T)", result3.toString());
        Assert.assertEquals("(T && F)", result4.toString());

        // Evaluate Tests
        Assert.assertFalse(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertFalse(result3.evaluateShort(ctx));
        Assert.assertFalse(result4.evaluateShort(ctx));

        Assert.assertFalse(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertFalse(result3.evaluateComplete(ctx));
        Assert.assertFalse(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(F && F) als Graphviz", result1, "andBasicConstant");
        TestToolkit.assertDotEquals("(T && T) als Graphviz", result2, "andBasicConstant2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void andBasicVar() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeVariableExpression("f");
        final Expression wahr = api.makeVariableExpression("t");
        ctx.set("f", false);
        ctx.set("t", true);

        final Expression result1 = api.makeAndExpression(falsch, falsch);
        final Expression result2 = api.makeAndExpression(wahr, wahr);
        final Expression result3 = api.makeAndExpression(falsch, wahr);
        final Expression result4 = api.makeAndExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(f && f)", result1.toString());
        Assert.assertEquals("(t && t)", result2.toString());
        Assert.assertEquals("(f && t)", result3.toString());
        Assert.assertEquals("(t && f)", result4.toString());

        // Evaluate Tests
        Assert.assertFalse(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertFalse(result3.evaluateShort(ctx));
        Assert.assertFalse(result4.evaluateShort(ctx));

        Assert.assertFalse(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertFalse(result3.evaluateComplete(ctx));
        Assert.assertFalse(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(f && f) als Graphviz", result1, "andBasicVar1");
        TestToolkit.assertDotEquals("(t && t) als Graphviz", result2, "andBasicVar2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    // ************************OR************************//

    @Test
    public final void orBasicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        final Expression result1 = api.makeOrExpression(falsch, falsch);
        final Expression result2 = api.makeOrExpression(wahr, wahr);
        final Expression result3 = api.makeOrExpression(falsch, wahr);
        final Expression result4 = api.makeOrExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(F || F)", result1.toString());
        Assert.assertEquals("(T || T)", result2.toString());
        Assert.assertEquals("(F || T)", result3.toString());
        Assert.assertEquals("(T || F)", result4.toString());

        // Evaluate Tests
        Assert.assertFalse(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertTrue(result3.evaluateShort(ctx));
        Assert.assertTrue(result4.evaluateShort(ctx));

        Assert.assertFalse(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertTrue(result3.evaluateComplete(ctx));
        Assert.assertTrue(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(F || F) als Graphviz", result1, "orBasicConstant");
        TestToolkit.assertDotEquals("(T || T) als Graphviz", result2, "orBasicConstant2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void orBasicVar() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeVariableExpression("f");
        final Expression wahr = api.makeVariableExpression("t");
        ctx.set("f", false);
        ctx.set("t", true);

        final Expression result1 = api.makeOrExpression(falsch, falsch);
        final Expression result2 = api.makeOrExpression(wahr, wahr);
        final Expression result3 = api.makeOrExpression(falsch, wahr);
        final Expression result4 = api.makeOrExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(f || f)", result1.toString());
        Assert.assertEquals("(t || t)", result2.toString());
        Assert.assertEquals("(f || t)", result3.toString());
        Assert.assertEquals("(t || f)", result4.toString());

        // Evaluate Tests
        Assert.assertFalse(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertTrue(result3.evaluateShort(ctx));
        Assert.assertTrue(result4.evaluateShort(ctx));

        Assert.assertFalse(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertTrue(result3.evaluateComplete(ctx));
        Assert.assertTrue(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(f || f) als Graphviz", result1, "orBasicVar1");
        TestToolkit.assertDotEquals("(t || t) als Graphviz", result2, "orBasicVar2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    // ************************XOR************************//

    @Test
    public final void xorBasicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        final Expression result1 = api.makeXorExpression(falsch, falsch);
        final Expression result2 = api.makeXorExpression(wahr, wahr);
        final Expression result3 = api.makeXorExpression(falsch, wahr);
        final Expression result4 = api.makeXorExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(F ^ F)", result1.toString());
        Assert.assertEquals("(T ^ T)", result2.toString());
        Assert.assertEquals("(F ^ T)", result3.toString());
        Assert.assertEquals("(T ^ F)", result4.toString());

        // Evaluate Tests
        Assert.assertFalse(result1.evaluateShort(ctx));
        Assert.assertFalse(result2.evaluateShort(ctx));
        Assert.assertTrue(result3.evaluateShort(ctx));
        Assert.assertTrue(result4.evaluateShort(ctx));

        Assert.assertFalse(result1.evaluateComplete(ctx));
        Assert.assertFalse(result2.evaluateComplete(ctx));
        Assert.assertTrue(result3.evaluateComplete(ctx));
        Assert.assertTrue(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(F ^ F) als Graphviz", result1, "xorBasicConstant1");
        TestToolkit.assertDotEquals("(T ^ T) als Graphviz", result2, "xorBasicConstant2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void xorBasicVar() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeVariableExpression("f");
        final Expression wahr = api.makeVariableExpression("t");
        ctx.set("f", false);
        ctx.set("t", true);

        final Expression result1 = api.makeXorExpression(falsch, falsch);
        final Expression result2 = api.makeXorExpression(wahr, wahr);
        final Expression result3 = api.makeXorExpression(falsch, wahr);
        final Expression result4 = api.makeXorExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(f ^ f)", result1.toString());
        Assert.assertEquals("(t ^ t)", result2.toString());
        Assert.assertEquals("(f ^ t)", result3.toString());
        Assert.assertEquals("(t ^ f)", result4.toString());

        // Evaluate Tests
        Assert.assertFalse(result1.evaluateShort(ctx));
        Assert.assertFalse(result2.evaluateShort(ctx));
        Assert.assertTrue(result3.evaluateShort(ctx));
        Assert.assertTrue(result4.evaluateShort(ctx));

        Assert.assertFalse(result1.evaluateComplete(ctx));
        Assert.assertFalse(result2.evaluateComplete(ctx));
        Assert.assertTrue(result3.evaluateComplete(ctx));
        Assert.assertTrue(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(f ^ f) als Graphviz", result1, "xorBasicVar1");
        TestToolkit.assertDotEquals("(t ^ t) als Graphviz", result2, "xorBasicVar2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    // ************************EQ************************//

    @Test
    public final void eqBasicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        final Expression result1 = api.makeEquivalenceExpression(falsch, falsch);
        final Expression result2 = api.makeEquivalenceExpression(wahr, wahr);
        final Expression result3 = api.makeEquivalenceExpression(falsch, wahr);
        final Expression result4 = api.makeEquivalenceExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(F <-> F)", result1.toString());
        Assert.assertEquals("(T <-> T)", result2.toString());
        Assert.assertEquals("(F <-> T)", result3.toString());
        Assert.assertEquals("(T <-> F)", result4.toString());

        // Evaluate Tests
        Assert.assertTrue(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertFalse(result3.evaluateShort(ctx));
        Assert.assertFalse(result4.evaluateShort(ctx));

        Assert.assertTrue(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertFalse(result3.evaluateComplete(ctx));
        Assert.assertFalse(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(F <-> F) als Graphviz", result1, "eqBasicConstant1");
        TestToolkit.assertDotEquals("(T <-> T) als Graphviz", result2, "eqBasicConstant2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertTrue(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void eqBasicVar() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeVariableExpression("f");
        final Expression wahr = api.makeVariableExpression("t");
        ctx.set("f", false);
        ctx.set("t", true);

        final Expression result1 = api.makeEquivalenceExpression(falsch, falsch);
        final Expression result2 = api.makeEquivalenceExpression(wahr, wahr);
        final Expression result3 = api.makeEquivalenceExpression(falsch, wahr);
        final Expression result4 = api.makeEquivalenceExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(f <-> f)", result1.toString());
        Assert.assertEquals("(t <-> t)", result2.toString());
        Assert.assertEquals("(f <-> t)", result3.toString());
        Assert.assertEquals("(t <-> f)", result4.toString());

        // Evaluate Tests
        Assert.assertTrue(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertFalse(result3.evaluateShort(ctx));
        Assert.assertFalse(result4.evaluateShort(ctx));

        Assert.assertTrue(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertFalse(result3.evaluateComplete(ctx));
        Assert.assertFalse(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(f <-> f) als Graphviz", result1, "eqBasicVar1");
        TestToolkit.assertDotEquals("(t <-> t) als Graphviz", result2, "eqBasicVar2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertTrue(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    // ************************IMPL************************//

    @Test
    public final void conBasicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        final Expression result1 = api.makeConsequenceExpression(falsch, falsch);
        final Expression result2 = api.makeConsequenceExpression(wahr, wahr);
        final Expression result3 = api.makeConsequenceExpression(falsch, wahr);
        final Expression result4 = api.makeConsequenceExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(F -> F)", result1.toString());
        Assert.assertEquals("(T -> T)", result2.toString());
        Assert.assertEquals("(F -> T)", result3.toString());
        Assert.assertEquals("(T -> F)", result4.toString());

        // Evaluate Tests
        Assert.assertTrue(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertTrue(result3.evaluateShort(ctx));
        Assert.assertFalse(result4.evaluateShort(ctx));

        Assert.assertTrue(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertTrue(result3.evaluateComplete(ctx));
        Assert.assertFalse(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(F -> F) als Graphviz", result1, "conBasicConstant1");
        TestToolkit.assertDotEquals("(T -> T) als Graphviz", result2, "conBasicConstant2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertTrue(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void conBasicVar() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeVariableExpression("f");
        final Expression wahr = api.makeVariableExpression("t");
        ctx.set("f", false);
        ctx.set("t", true);

        final Expression result1 = api.makeConsequenceExpression(falsch, falsch);
        final Expression result2 = api.makeConsequenceExpression(wahr, wahr);
        final Expression result3 = api.makeConsequenceExpression(falsch, wahr);
        final Expression result4 = api.makeConsequenceExpression(wahr, falsch);

        // String Test
        Assert.assertEquals("(f -> f)", result1.toString());
        Assert.assertEquals("(t -> t)", result2.toString());
        Assert.assertEquals("(f -> t)", result3.toString());
        Assert.assertEquals("(t -> f)", result4.toString());

        // Evaluate Tests
        Assert.assertTrue(result1.evaluateShort(ctx));
        Assert.assertTrue(result2.evaluateShort(ctx));
        Assert.assertTrue(result3.evaluateShort(ctx));
        Assert.assertFalse(result4.evaluateShort(ctx));

        Assert.assertTrue(result1.evaluateComplete(ctx));
        Assert.assertTrue(result2.evaluateComplete(ctx));
        Assert.assertTrue(result3.evaluateComplete(ctx));
        Assert.assertFalse(result4.evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(f -> f) als Graphviz", result1, "conBasicVar1");
        TestToolkit.assertDotEquals("(t -> t) als Graphviz", result2, "conBasicVar2");

        // Parallel
        Counter.initialize();
        boolean result = result1.evaluateParallel(ctx, 0);
        Assert.assertTrue(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

}
