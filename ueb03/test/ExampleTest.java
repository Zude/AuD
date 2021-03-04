import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import api.ExpressionAPI;
import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.IncompleteContextException;

public class ExampleTest {

    @Test
    public final void testToStringEquivalenceOr() {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        Assert.assertEquals("((T || F) <-> T)",
                api.makeEquivalenceExpression(api.makeOrExpression(wahr, falsch), wahr).toString());
    }

    @Test
    public final void testToGraphvizEquivalenceOr() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        TestToolkit.assertDotEquals("((T || F) <-> T) als Graphviz",
                api.makeEquivalenceExpression(api.makeOrExpression(wahr, falsch), wahr), "a1");
    }

    @Test
    public final void testToGraphvizNotConstant() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        TestToolkit.assertDotEquals("(!T) als Graphviz", api.makeNotExpression(wahr), "notConst");
    }

    @Test
    public final void testToGraphvizAndVar() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();

        final Expression a = api.makeVariableExpression("a");
        final Expression b = api.makeVariableExpression("b");

        TestToolkit.assertDotEquals("(a && b) als Graphviz", api.makeAndExpression(a, b), "andVar");
    }

    @Test
    public final void testToGraphvizIdentityVar() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();

        final Expression a = api.makeVariableExpression("a");

        TestToolkit.assertDotEquals("(a && b) als Graphviz", api.makeIdExpression(a), "idVar");
    }

    @Test
    public final void testToStringNotConstant() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression falsch = api.makeConstantExpression(false);

        String result = api.makeNotExpression(falsch).toString();
        Assert.assertEquals("(!F)", result);
    }

    @Test
    public final void testToStringIdConstant() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression falsch = api.makeConstantExpression(false);

        String result = api.makeIdExpression(falsch).toString();
        Assert.assertEquals("(F)", result);
    }

    @Test
    public final void testToStringAndConstant() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression falsch = api.makeConstantExpression(false);
        final Expression wahr = api.makeConstantExpression(true);

        String result = api.makeAndExpression(falsch, wahr).toString();
        Assert.assertEquals("(F && T)", result);
    }

    @Test
    public final void testToStringEquivalenceAnd() {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        Assert.assertEquals("((T && F) <-> F)", api
                .makeEquivalenceExpression(api.makeAndExpression(wahr, falsch), falsch).toString());
    }

    @Test
    public final void testToGraphvizEquivalenceAnd() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        TestToolkit.assertDotEquals("((T && F) <-> F) als Graphviz",
                api.makeEquivalenceExpression(api.makeAndExpression(wahr, falsch), falsch), "a2");
    }

    @Test
    public final void testToStringAlmostAll() {
        final ExpressionAPI api = new ExpressionAPI();
        Assert.assertEquals("((a -> b) <-> ((!a) || (b)))",
                api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeVariableExpression("b")),
                        api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                                api.makeIdExpression(api.makeVariableExpression("b"))))
                        .toString());
    }

    @Test
    public final void testToGraphvizAlmostAll() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        TestToolkit.assertDotEquals("((a -> b) <-> ((!a) || (b))) als Graphviz",
                api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeVariableExpression("b")),
                        api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                                api.makeIdExpression(api.makeVariableExpression("b")))),
                "a3");
    }

    @Test
    public final void testEvaluateNot() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression variable = api.makeVariableExpression("a");
        ctx.set("a", false);

        Assert.assertTrue(api.makeNotExpression(variable).evaluateShort(ctx));
        Assert.assertTrue(api.makeNotExpression(variable).evaluateComplete(ctx));
    }

    @Test
    public final void testEvaluateNotConstant() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression variable = api.makeConstantExpression(false);

        Assert.assertTrue(api.makeNotExpression(variable).evaluateShort(ctx));
        Assert.assertTrue(api.makeNotExpression(variable).evaluateComplete(ctx));
    }

    @Test
    public final void testEvaluateOr() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);

        Assert.assertTrue(api.makeOrExpression(wahr, falsch).evaluateShort(ctx));
        Assert.assertTrue(api.makeOrExpression(wahr, falsch).evaluateComplete(ctx));
    }

    @Test
    public final void testEvaluateAnd() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);

        Assert.assertFalse(api.makeAndExpression(wahr, falsch).evaluateShort(ctx));
        Assert.assertFalse(api.makeAndExpression(wahr, falsch).evaluateComplete(ctx));
    }

    @Test
    public final void testEvaluateAndParallel() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);

        Counter.initialize();
        boolean result = api.makeAndExpression(wahr, falsch).evaluateParallel(ctx, 2);
        Assert.assertFalse(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void testParallelAlmostAll() throws IncompleteContextException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);

        final Expression bigger = api.makeEquivalenceExpression(
                api.makeConsequenceExpression(falsch, wahr),
                api.makeOrExpression(api.makeNotExpression(falsch), api.makeIdExpression(wahr)));

        Counter.initialize();
        Assert.assertTrue(bigger.evaluateParallel(ctx, 6));
        Assert.assertEquals(1, Counter.getCounter());

        Counter.initialize();
        Assert.assertTrue(bigger.evaluateParallel(ctx, 2));
        Assert.assertEquals(3, Counter.getCounter());
    }

}
