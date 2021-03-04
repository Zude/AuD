import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import api.ExpressionAPI;
import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.IncompleteContextException;

public class UnaryTests {

    // ************************ID************************//

    @Test
    public final void idBasicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        final Expression result1 = api.makeIdExpression(falsch);
        final Expression result2 = api.makeIdExpression(wahr);

        // String Test
        Assert.assertEquals("(F)", result1.toString());
        Assert.assertEquals("(T)", result2.toString());

        // Evaluate Tests
        Assert.assertFalse(api.makeIdExpression(falsch).evaluateShort(ctx));
        Assert.assertTrue(api.makeIdExpression(wahr).evaluateShort(ctx));

        Assert.assertFalse(api.makeIdExpression(falsch).evaluateComplete(ctx));
        Assert.assertTrue(api.makeIdExpression(wahr).evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(F) als Graphviz", api.makeIdExpression(falsch),
                "idBasicConstantFalse");
        TestToolkit.assertDotEquals("(T) als Graphviz", api.makeIdExpression(wahr),
                "idBasicConstantTrue");

        // Parallel
        Counter.initialize();
        boolean result = api.makeIdExpression(wahr).evaluateParallel(ctx, 0);
        Assert.assertTrue(result);
        Assert.assertEquals(0, Counter.getCounter());
    }

    @Test
    public final void idBasicVar() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeVariableExpression("f");
        final Expression wahr = api.makeVariableExpression("t");
        ctx.set("f", false);
        ctx.set("t", true);

        final Expression result1 = api.makeIdExpression(falsch);
        final Expression result2 = api.makeIdExpression(wahr);

        // String Test
        Assert.assertEquals("(f)", result1.toString());
        Assert.assertEquals("(t)", result2.toString());

        // Evaluate Tests
        Assert.assertFalse(api.makeIdExpression(falsch).evaluateShort(ctx));
        Assert.assertTrue(api.makeIdExpression(wahr).evaluateShort(ctx));

        Assert.assertFalse(api.makeIdExpression(falsch).evaluateComplete(ctx));
        Assert.assertTrue(api.makeIdExpression(wahr).evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(f) als Graphviz", api.makeIdExpression(falsch),
                "idBasicVarFalse");
        TestToolkit.assertDotEquals("(t) als Graphviz", api.makeIdExpression(wahr),
                "idBasicVarTrue");

        // Parallel
        Counter.initialize();
        boolean result = api.makeIdExpression(wahr).evaluateParallel(ctx, 0);
        Assert.assertTrue(result);
        Assert.assertEquals(0, Counter.getCounter());
    }

    // ************************NOT************************//

    @Test
    public final void notBasicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        final Expression result1 = api.makeNotExpression(falsch);
        final Expression result2 = api.makeNotExpression(wahr);

        // String Test
        Assert.assertEquals("(!F)", result1.toString());
        Assert.assertEquals("(!T)", result2.toString());

        // Evaluate Tests
        Assert.assertFalse(api.makeNotExpression(wahr).evaluateShort(ctx));
        Assert.assertTrue(api.makeNotExpression(falsch).evaluateShort(ctx));

        Assert.assertFalse(api.makeNotExpression(wahr).evaluateComplete(ctx));
        Assert.assertTrue(api.makeNotExpression(falsch).evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(!F) als Graphviz", api.makeNotExpression(falsch),
                "notBasicConstantFalse");
        TestToolkit.assertDotEquals("(!T) als Graphviz", api.makeNotExpression(wahr),
                "notBasicConstantTrue");

        // Parallel
        Counter.initialize();
        boolean result = api.makeNotExpression(wahr).evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(0, Counter.getCounter());
    }

    @Test
    public final void notBasicVar() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeVariableExpression("f");
        final Expression wahr = api.makeVariableExpression("t");
        ctx.set("f", false);
        ctx.set("t", true);

        final Expression result1 = api.makeNotExpression(falsch);
        final Expression result2 = api.makeNotExpression(wahr);

        // String Test
        Assert.assertEquals("(!f)", result1.toString());
        Assert.assertEquals("(!t)", result2.toString());

        // Evaluate Tests
        Assert.assertFalse(api.makeNotExpression(wahr).evaluateShort(ctx));
        Assert.assertTrue(api.makeNotExpression(falsch).evaluateShort(ctx));

        Assert.assertFalse(api.makeNotExpression(wahr).evaluateComplete(ctx));
        Assert.assertTrue(api.makeNotExpression(falsch).evaluateComplete(ctx));

        // Graphiv Test
        TestToolkit.assertDotEquals("(!f) als Graphviz", api.makeNotExpression(falsch),
                "notBasicVarFalse");
        TestToolkit.assertDotEquals("(!t) als Graphviz", api.makeNotExpression(wahr),
                "notBasicVarTrue");

        // Parallel
        Counter.initialize();
        boolean result = api.makeNotExpression(wahr).evaluateParallel(ctx, 0);
        Assert.assertFalse(result);
        Assert.assertEquals(0, Counter.getCounter());
    }

    // ************************CONST************************//

    @Test
    public final void basicConstant() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

    }

}
