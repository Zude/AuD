package expression.unary;

import expression.Expression;

/**
 * Klasse fuer Identity Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
public class IdExpression extends AbstractUnaryExpression {

    public IdExpression(Expression expression) {

        super(expression);
    }

    @Override
    public boolean evaluate(boolean value) {

        return value;
    }

    @Override
    protected String getOperationSymbol() {

        return "";
    }

    @Override
    protected String returnOperationSymbolGraphviz() {

        return "()";
    }
}
