package expression.emementary;

import expression.Context;
import expression.IncompleteContextException;

/**
 * Klasse fuer Constante Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
public class ConstantExpression extends AbstractElementaryExpression {

    // Bool Wert der Constanten Expression
    private final boolean value;

    public ConstantExpression(boolean value) {

        this.value = value;
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {

        return value;
    }

    @Override
    protected String getOperationSymbol() {

        return value ? "T" : "F";
    }

}
