package expression.emementary;

import expression.AbstractExpression;
import expression.Context;
import expression.IncompleteContextException;

/**
 * Abstrakte Klasse fuer Elementare Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
abstract class AbstractElementaryExpression extends AbstractExpression {

    protected AbstractElementaryExpression() {

        super(0);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {

        return evaluateComplete(c);
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {

        return evaluateComplete(c);
    }

    @Override
    public void toString(StringBuilder builder) {

        // Schreibt den korrekt String fuer das toString Format
        builder.append(getOperationSymbol());
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {

        // Schreibt den korrekt String fuer das Graphiz Format
        builder.append(prefix + " " + "[label=\"" + getOperationSymbol() + "\"]\n");
    }

}
