package expression.emementary;

import expression.Context;
import expression.IncompleteContextException;

/**
 * Klasse fuer Variable Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
public class VariableExpression extends AbstractElementaryExpression {

    // Name der konkreten Variable der aktuellen Expression
    private final String name;

    public VariableExpression(String name) {

        this.name = name;
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {

        Boolean result;

        if (c == null) {
            throw new IncompleteContextException(this.name);
        }

        result = c.get(this.name);

        if (result == null) {
            throw new IncompleteContextException(this.name);
        }

        return result.booleanValue();
    }

    @Override
    protected String getOperationSymbol() {

        return name;
    }

}
