package expression.binary;

import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Klasse fuer Consequence Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
public class ConsequenceExpression extends AbstractBinaryExpression {

    public ConsequenceExpression(Expression left, Expression right) {

        super(left, right);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {

        return !leftExpression.evaluateShort(c) || rightExpression.evaluateShort(c);
    }

    @Override
    protected boolean evaluate(boolean left, boolean right) {

        return !left || right;
    }

    @Override
    protected String getOperationSymbol() {

        return "->";
    }

}
