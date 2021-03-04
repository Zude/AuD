package expression.unary;

import expression.Expression;

/**
 * Klasse fuer Not Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
public class NotExpression extends AbstractUnaryExpression {

    public NotExpression(Expression expression) {

        super(expression);
    }

    @Override
    public boolean evaluate(boolean value) {

        return !value;
    }

    @Override
    protected String getOperationSymbol() {

        return "!";
    }

    @Override
    protected String returnOperationSymbolGraphviz() {

        return "!";
    }

}
