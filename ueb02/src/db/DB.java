package db;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * In einer Datenbank werden Datenbanktabellen verwaltet, die jeweils durch einen eindeutigen
 * Bezeichner (Datentyp String) identifiziert werden. Auch die Datenbank selbst hat einen Bezeichner
 * (Datentyp String).
 * 
 * Ein valider Bezeichner besteht stets aus einem Zeichen aus der Menge [a-zA-Z] gefolgt von einer
 * beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
 *
 * @author kar, mhe, Alexander Loeffler
 */
public final class DB {

    // *********** Member ***********//
    private final String dbName;
    private final Map<String, DBTable> tables = new HashMap<String, DBTable>();

    /**
     * Erzeugt eine leere Datenbank mit dem Bezeichner anId.
     *
     * @param anId Bezeichner der Datenbank, die erzeugt werden soll.
     *
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     */
    public DB(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);

        this.dbName = anId;
    }

    /**
     * Fügt die Tabelle tab in die Datenbank ein.
     *
     * @param tab Tabelle, die in die Datenbank eingefügt werden soll.
     *
     * @pre tab != null
     * @pre es darf keine Tabelle mit demselben Bezeichner wie dem von tab in der Datenbank
     *      existieren.
     */
    public void addTable(final DBTable tab) {
        assert tab != null;
        assert !this.tables.containsKey(tab.getId());

        this.tables.put(tab.getId(), tab);
    }

    /**
     * Entfernt die Tabelle mit dem Bezeichner anId aus der Datenbank.
     *
     * @param anId Bezeichner der Tabelle, die aus der Datenbank entfernt werden soll.
     *
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     *
     * @post in der Datenbank befindet sich keine Tabelle mit dem Bezeichner anId.
     */
    public void removeTable(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);

        this.tables.remove(anId);

        assert !this.tables.containsKey(anId);
    }

    /**
     * Entfernt alle Tabellen aus der Datenbank.
     *
     * @post die Datenbank enthält keine Tabellen.
     */
    public void removeAllTables() {

        this.tables.clear();

        assert this.tables.size() == 0;
    }

    /**
     * Liefert eine aufsteigend sortierte Liste der Tabellenbezeichner.
     *
     * @return aufsteigend sortierte Liste der Tabellenbezeichner.
     */
    public List<String> getTableIds() {

        List<String> resultList = new ArrayList<String>(this.tables.keySet());

        resultList.sort(new TableIdCompare(SortDirection.ASC));

        return resultList;
    }

    /**
     * Gibt an, ob eine Tabelle mit dem Bezeichner anId in der Datenbank existiert.
     *
     * @param anId Bezeichner der Tabelle, deren Existenz geprüft werden soll.
     *
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     *
     * @return boolscher Wert, der angibt, ob eine Tabelle mit dem Bezeichner anId in der Datenbank
     *         existiert.
     */
    public boolean tableExists(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);

        return this.tables.containsKey(anId);
    }

    /**
     * Liefert die Tabelle mit dem Bezeichner anId.
     *
     * @param anId Bezeichner der Tabelle, die geliefert werden soll.
     *
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     *
     * @return Tabelle mit dem Bezeichner anId (falls vorhanden, sonst NULL-Referenz).
     */
    public DBTable getTable(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);

        return this.tables.get(anId);
    }

    /**
     * Liefert die Stringrepräsentation der Datenbank. Die Stringrepräsentation ist wie folgt
     * aufgebaut:
     * <ul>
     * <li>In der ersten Zeile steht <code>Datenbankname: </code> gefolgt von dem Bezeichner der
     * Datenbank.</li>
     * <li>Die zweite Zeile ist eine Leerzeile.</li>
     * <li>Es folgen Datenbanktabellen in aufsteigender Reihenfolge ihrer Bezeichner in folgender
     * Form:
     * <ul>
     * <li>Vor jeder Tabelle steht <code>Tabellenname: </code> gefolgt von dem Bezeichner der
     * Datenbanktabelle.</li>
     * <li>Danach folgt eine Leerzeile.</li>
     * <li>Es folgt die Stringrepräsentation der Datenbanktabelle.</li>
     * </ul>
     * </li>
     * <li>Nach jeder Tabelle folgt eine Leerzeile.</li>
     * </ul>
     *
     * @return die Stringrepräsentation der Datenbank.
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        List<String> tableNames = getTableIds();
        Iterator<String> tableNamesIterator = tableNames.iterator();

        // erste Zeile schreiben
        builder.append("Datenbankname: " + this.dbName + "\n");
        // zweite Zeile schreiben
        builder.append("\n");

        // table lines schreiben
        while (tableNamesIterator.hasNext()) {
            String tableName = tableNamesIterator.next();
            builder.append("Tabellenname: " + tableName + "\n" + "\n");
            builder.append(this.tables.get(tableName).toString() + "\n" + "\n");
        }

        return builder.toString();
    }

    /**
     * Liefert die Anzahl der Tabellen in der Datenbank.
     *
     * @return Anzahl der Tabellen in der Datenbank.
     */
    public int getTableCnt() {

        return this.tables.size();
    }

    /**
     * Liefert den Bezeichner der Datenbank.
     *
     * @return Bezeichner der Datenbank.
     */
    public String getId() {

        return this.dbName;
    }

    /**
     * Eine Hilfsklasse zum vergleichen von TableId Strings
     * 
     * @author alexander loeffler
     */
    private class TableIdCompare implements Comparator<String> {

        // Sortierrichtung
        private SortDirection direction;

        /**
         * Konstruktor
         * 
         * @param dir die Richtung nach der Sortiert werden soll
         */
        TableIdCompare(SortDirection dir) {
            this.direction = dir;
        }

        @Override
        public int compare(String firstRow, String secondRow) {

            return (this.direction == SortDirection.ASC) ? firstRow.compareTo(secondRow)
                    : firstRow.compareTo(secondRow) * -1;
        }

    }

}
