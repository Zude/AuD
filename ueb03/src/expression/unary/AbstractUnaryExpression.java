package expression.unary;

import expression.AbstractExpression;
import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Abstrakte Klasse fuer Unaere Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
abstract class AbstractUnaryExpression extends AbstractExpression {

    protected final Expression expression;

    // Konstruktor fuer Unaere Operationen
    protected AbstractUnaryExpression(Expression expression) {

        // Unaere Expressions haben stets nur einen Unterknoten
        super(1 + expression.getChildrenCount());
        this.expression = expression;
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {

        return evaluate(expression.evaluateComplete(c));
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {

        return evaluate(expression.evaluateShort(c));
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {

        return evaluate(expression.evaluateParallel(c, bound));
    }

    @Override
    public void toString(StringBuilder builder) {

        // Schreibt den korrekt String fuer das toString Format
        builder.append("(");
        builder.append(getOperationSymbol());
        expression.toString(builder);
        builder.append(")");
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {

        // Schreibt den korrekt String fuer das graphiz Format
        builder.append(prefix + " " + "[label=\"" + returnOperationSymbolGraphviz() + " ["
                + this.getChildrenCount() + "]\"]\n");
        builder.append(prefix + " -> " + prefix + "_" + " " + "[label=\"\"]\n");
        expression.toGraphviz(builder, prefix + "_");
    }

    /**
     * Repraesentiert die aktuelle Audrucks Logik der konkreten Klassen
     * 
     * @param value Der Linke Teilausdruck
     * @return Der Wahrheitswert des Ausdrucks.
     */
    protected abstract boolean evaluate(boolean value);

    /**
     * Gibt den reprasentierenen String der logsiche Operation zurueck fuer das graphiv format
     * 
     * @return Der Logische Ausdruck als String
     */
    protected abstract String returnOperationSymbolGraphviz();
}
