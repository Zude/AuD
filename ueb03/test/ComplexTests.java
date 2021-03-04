import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import api.ExpressionAPI;
import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.IncompleteContextException;

public class ComplexTests {

    // ************************GRAPHVIZ************************//

    @Test
    public final void graphivAufgabestellung() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        ctx.set("a", false);
        ctx.set("b", true);

        // Graphiv Test
        TestToolkit.assertDotEquals("Graphiviz aus Aufgabenstellung",
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeVariableExpression("b")),
                        api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                                api.makeVariableExpression("b")))),
                "graphivAufgabestellung");
    }

    // ************************Threads************************//

    @Test
    public final void thread1() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        ctx.set("a", false);
        ctx.set("b", true);

        // Graphiv Test

        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("a"),
                        api.makeVariableExpression("b")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("b"))));

        assertEquals("(((a -> b) <-> ((!a) || b)))", expression.toString());

        // Parallel
        Counter.initialize();
        boolean result = expression.evaluateParallel(ctx, 0);
        Assert.assertTrue(result);
        Assert.assertEquals(3, Counter.getCounter());
    }

    @Test
    public final void thread2() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        ctx.set("a", false);
        ctx.set("b", true);

        // Graphiv Test

        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("a"),
                        api.makeVariableExpression("b")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("b"))));

        assertEquals("(((a -> b) <-> ((!a) || b)))", expression.toString());

        // Parallel
        Counter.initialize();
        boolean result = expression.evaluateParallel(ctx, 3);
        Assert.assertTrue(result);
        Assert.assertEquals(2, Counter.getCounter());
    }

    @Test
    public final void thread3() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        ctx.set("a", false);
        ctx.set("b", true);

        // Graphiv Test

        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("a"),
                        api.makeVariableExpression("b")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("b"))));

        assertEquals("(((a -> b) <-> ((!a) || b)))", expression.toString());

        // Parallel
        Counter.initialize();
        boolean result = expression.evaluateParallel(ctx, 4);
        Assert.assertTrue(result);
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void thread4() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        ctx.set("a", false);
        ctx.set("b", true);

        // Graphiv Test

        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("a"),
                        api.makeVariableExpression("b")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("b"))));

        assertEquals("(((a -> b) <-> ((!a) || b)))", expression.toString());

        // Parallel
        Counter.initialize();
        boolean result = expression.evaluateParallel(ctx, 8);
        Assert.assertTrue(result);
        Assert.assertEquals(0, Counter.getCounter());
    }

    @Test
    public final void shortAnd() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression a = api.makeVariableExpression("a");
        final Expression b = api.makeVariableExpression("b");
        final Expression c = api.makeVariableExpression("c");
        final Expression d = api.makeVariableExpression("d");

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", true);

        // Ausdruck
        Expression expression = api.makeAndExpression(a, api.makeOrExpression(d, d));

        Assert.assertFalse(expression.evaluateShort(ctx));
        // Assert.assertFalse(expression.evaluateComplete(ctx));
    }

    @Test
    public final void shortCons() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression a = api.makeVariableExpression("a");
        final Expression b = api.makeVariableExpression("b");
        final Expression c = api.makeVariableExpression("c");
        final Expression d = api.makeVariableExpression("d");

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", true);

        // Ausdruck
        Expression expression = api.makeConsequenceExpression(a, d);

        Assert.assertTrue(expression.evaluateShort(ctx));
        // Assert.assertTrue(expression.evaluateComplete(ctx));
    }

    @Test
    public final void shortOr() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression a = api.makeVariableExpression("a");
        final Expression b = api.makeVariableExpression("b");
        final Expression c = api.makeVariableExpression("c");
        final Expression d = api.makeVariableExpression("d");

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", true);

        // Ausdruck
        Expression expression = api.makeOrExpression(b, d);

        Assert.assertTrue(expression.evaluateShort(ctx));
        // Assert.assertTrue(expression.evaluateComplete(ctx));
    }

    @Test
    public final void shortComplex() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression a = api.makeVariableExpression("a");
        final Expression d = api.makeVariableExpression("d");

        ctx.set("a", false);
        ctx.set("c", true);

        // Ausdruck
        Expression expression = api.makeConsequenceExpression(
                api.makeNotExpression(api
                        .makeOrExpression(api.makeNotExpression(api.makeAndExpression(a, d)), d)),
                d);

        Assert.assertTrue(expression.evaluateShort(ctx));
        // Assert.assertTrue(expression.evaluateComplete(ctx));
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext1() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression a = api.makeVariableExpression("a");
        final Expression d = api.makeVariableExpression("d");

        ctx.set("a", false);
        ctx.set("c", true);

        // Ausdruck
        Expression expression = api.makeAndExpression(a, d);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext2() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression a = api.makeVariableExpression("a");
        final Expression d = api.makeVariableExpression("d");

        ctx.set("a", false);
        ctx.set("c", true);

        // Ausdruck
        Expression expression = api.makeConsequenceExpression(
                api.makeNotExpression(api
                        .makeOrExpression(api.makeNotExpression(api.makeAndExpression(a, d)), d)),
                d);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext3() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        final Expression c = api.makeVariableExpression("c");

        ctx.set("a", true);
        ctx.set("b", true);

        // Ausdruck
        Expression expression = api.makeNotExpression(c);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext4() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        final Expression c = api.makeVariableExpression("c");

        // Ausdruck
        Expression expression = api.makeIdExpression(c);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext5() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        final Expression c = api.makeVariableExpression("c");
        final Expression q = api.makeVariableExpression("q");

        // Ausdruck
        Expression expression = api.makeOrExpression(c, q);

        // call function
        expression.evaluateParallel(ctx, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext6() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();

        final Expression c = api.makeVariableExpression("c");
        final Expression q = api.makeVariableExpression("q");

        // Ausdruck
        Expression expression = api.makeOrExpression(c, q);

        // call function
        expression.evaluateParallel(null, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext7() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        ctx.set("c", false);
        ctx.set("q", false);
        ctx.set("a", false);

        // Ausdruck
        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("b"),
                        api.makeVariableExpression("q")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("c"))));

        // call function
        expression.evaluateParallel(null, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext8() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        ctx.set("c", false);
        ctx.set("q", false);
        ctx.set("a", false);

        // Ausdruck
        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("b"),
                        api.makeVariableExpression("q")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("c"))));

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext9() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("b"),
                        api.makeVariableExpression("q")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("c"))));

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext10() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("b"),
                        api.makeVariableExpression("q")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("c"))));

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext11() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression = api.makeIdExpression(api.makeEquivalenceExpression(
                api.makeConsequenceExpression(api.makeVariableExpression("b"),
                        api.makeVariableExpression("q")),
                api.makeOrExpression(api.makeNotExpression(api.makeVariableExpression("a")),
                        api.makeVariableExpression("c"))));

        // call function
        expression.evaluateParallel(ctx, 1);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext12() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        ctx.set("h", false);
        // ctx.set("i", true);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext14() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        ctx.set("h", false);
        // ctx.set("i", true);

        // call function
        expression.evaluateParallel(ctx, 2);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext15() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        // ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext16() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        // ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateShort(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext17() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        // ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateParallel(ctx, 2);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext18() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext19() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateShort(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext20() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateParallel(ctx, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext21() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        // ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext22() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        // ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateShort(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext23() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        // ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateParallel(ctx, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext24() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        // ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateParallel(ctx, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext25() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        // ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext26() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        // ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateShort(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext30() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        // ctx.set("b", true);
        // ctx.set("c", false);
        ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateParallel(ctx, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext31() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        ctx.set("b", true);
        // ctx.set("c", false);
        // ctx.set("d", true);
        ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext32() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        // ctx.set("a", false);
        ctx.set("b", true);
        ctx.set("c", false);
        // ctx.set("d", true);
        // ctx.set("e", true);
        ctx.set("f", false);
        ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateShort(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext33() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        ctx.set("a", false);
        // ctx.set("b", true);
        // ctx.set("c", false);
        ctx.set("d", true);
        // ctx.set("e", true);
        ctx.set("f", false);
        // ctx.set("g", true);
        ctx.set("h", false);
        ctx.set("i", true);

        // call function
        expression.evaluateParallel(ctx, 0);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext34() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        // call function
        expression.evaluateComplete(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext35() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        // call function
        expression.evaluateShort(ctx);
    }

    @Test(expected = IncompleteContextException.class)
    public final void withOutContext36() throws IncompleteContextException, IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();

        // Ausdruck
        Expression expression =
                api.makeIdExpression(api.makeEquivalenceExpression(
                        api.makeConsequenceExpression(api.makeVariableExpression("a"),
                                api.makeIdExpression(api.makeEquivalenceExpression(
                                        api.makeConsequenceExpression(
                                                api.makeVariableExpression("b"),
                                                api.makeVariableExpression("c")),
                                        api.makeOrExpression(
                                                api.makeNotExpression(
                                                        api.makeVariableExpression("d")),
                                                api.makeVariableExpression("e"))))),
                        api.makeOrExpression(
                                api.makeNotExpression(
                                        api.makeIdExpression(api.makeEquivalenceExpression(
                                                api.makeConsequenceExpression(
                                                        api.makeVariableExpression("f"),
                                                        api.makeVariableExpression("q")),
                                                api.makeOrExpression(
                                                        api.makeNotExpression(
                                                                api.makeVariableExpression("g")),
                                                        api.makeVariableExpression("h"))))),
                                api.makeVariableExpression("i"))));

        // call function
        expression.evaluateParallel(ctx, 0);
    }

}
