import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import api.ExpressionAPI;
import expression.Expression;

public class DevTests {

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

        TestToolkit.assertDotEquals("(a) als Graphviz", api.makeIdExpression(a), "idVar");
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

}
