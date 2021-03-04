package expression.binary;

import expression.AbstractExpression;
import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.ExpressionRunner;
import expression.IncompleteContextException;

/**
 * Abstrakte Klasse fuer Binaere Expressions
 * 
 * @author Alexander Loeffler
 * 
 */
abstract class AbstractBinaryExpression extends AbstractExpression {

    // linke und rechte unter Expression
    protected final Expression leftExpression;
    protected final Expression rightExpression;

    protected AbstractBinaryExpression(Expression leftExpression, Expression rightExpression) {

        // Binaere Expressions haben Stets 2 Unterknoten
        super(2 + leftExpression.getChildrenCount() + rightExpression.getChildrenCount());
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {

        return evaluate(leftExpression.evaluateComplete(c), rightExpression.evaluateComplete(c));
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {

        if (childrenCount >= bound) {

            // Initialisiert den zu startenen Thread
            ExpressionRunner rightRunnable = new ExpressionRunner(rightExpression, c, bound);
            Thread righThread = new Thread(rightRunnable);
            boolean rightResult = false;

            // Zaehlt den Thread zaehler hoch und starte den Thread
            Counter.increment();
            righThread.start();

            // Warte auf den Abschluss des Threads und fange Exception wenn noetig
            try {
                righThread.join();
            } catch (InterruptedException e) {
                // not supposed to happen
                throw new AssertionError(e);
            }

            // Result der rechten Seite nehmen und ggf Exception fangen und Weiterwerfen
            try {
                rightResult = rightRunnable.getResult();
            } catch (Exception e) {
                throw rightRunnable.getException();
            }

            return evaluate(leftExpression.evaluateParallel(c, bound), rightResult);
        } else {

            return evaluate(leftExpression.evaluateParallel(c, bound),
                    rightExpression.evaluateParallel(c, bound));
        }
    }

    @Override
    public void toString(StringBuilder builder) {

        // Schreibt den korrekt String fuer das toString Format
        builder.append("(");
        leftExpression.toString(builder);
        builder.append(" " + getOperationSymbol() + " ");
        rightExpression.toString(builder);
        builder.append(")");
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {

        // Schreibt den korrekt String fuer das Graphiz Format
        builder.append(prefix + " " + "[label=\"" + getOperationSymbol() + " ["
                + this.getChildrenCount() + "]\"]\n");
        builder.append(prefix + " -> " + prefix + "l" + " " + "[label=\"\"]\n");
        builder.append(prefix + " -> " + prefix + "r" + " " + "[label=\"\"]\n");
        leftExpression.toGraphviz(builder, prefix + "l");
        rightExpression.toGraphviz(builder, prefix + "r");
    }

    /**
     * Repraesentiert die aktuelle Audrucks Logik der konkreten Klassen
     * 
     * @param left Der Linke Teilausdruck
     * @param right Der Rechte Teilausdruck
     * @return Der Wahrheitswert des Ausdrucks.
     */
    protected abstract boolean evaluate(boolean left, boolean right);

}
