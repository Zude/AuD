package expression.binary;

import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Klasse fuer Xor Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
public class XorExpression extends AbstractBinaryExpression {

    public XorExpression(Expression left, Expression right) {

        super(left, right);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {

        // Bei einer Xor Auswertung muessen beide Seiten ausgewertet werden
        return evaluate(leftExpression.evaluateShort(c), rightExpression.evaluateShort(c));
    }

    @Override
    protected boolean evaluate(boolean left, boolean right) {

        return (left && !right) || (!left && right);
    }

    @Override
    protected String getOperationSymbol() {

        return "^";
    }
}
