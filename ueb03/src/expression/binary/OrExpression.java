package expression.binary;

import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Klasse fuer Or Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
public class OrExpression extends AbstractBinaryExpression {

    public OrExpression(Expression left, Expression right) {

        super(left, right);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {

        return leftExpression.evaluateShort(c) || rightExpression.evaluateComplete(c);
    }

    @Override
    protected boolean evaluate(boolean left, boolean right) {

        return left || right;
    }

    @Override
    protected String getOperationSymbol() {

        return "||";
    }

}
