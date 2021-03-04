package db;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Eine Datenbanktabelle hat einen Namen bzw. Bezeichner und eine feste Spaltenanzahl, die ebenso
 * wie die Bezeichner (Datentyp String) der einzelnen Spalten und deren Reihenfolge bei der
 * Erzeugung festgelegt werden. Die Tabelle besteht darüber hinaus noch aus einer flexiblen Anzahl
 * von Zeilen, in denen jeweils genau so viele Werte (Datentyp String) stehen, wie es Spalten gibt.
 * Eine neue Zeile wird immer nach der letzten Zeile an die Tabelle angehängt.
 *
 * Der Bezeichner der Datenbanktabelle und die Bezeichner der Spalten müssen einem vorgegebenen
 * Muster folgen um gültig zu sein. Ein valider Bezeichner besteht stets aus einem Zeichen aus der
 * Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
 * 
 * @author kar, mhe, Alexander Loeffler
 */
public final class DBTable {

    // *********** Member ***********//

    private final Map<String, Integer> head = new HashMap<String, Integer>();
    private final List<String[]> table = new LinkedList<String[]>();
    private final String name;
    private final int numberOfCols;

    // *********** Main ***********//

    /**
     * Erzeugt eine leere Datenbanktabelle mit dem Bezeichner anId und den Spaltenbezeichnern
     * someColIds. Ein Iterator der Collection someColIds muss die Spaltennamen in der Reihenfolge
     * liefern, in der sie in der Tabelle stehen sollen.
     *
     * @param anId Bezeichner der Datenbanktabelle, die erzeugt werden soll.
     * @param someColIds Spaltenbezeichner
     *
     * @pre anId != null
     * @pre someColIds != null
     * @pre der Bezeichner anId muss gültig sein.
     * @pre someColIds muss mindestens einen Wert enthalten
     * @pre Alle Werte in someColIds müssen gültige Spaltenbezeichner sein
     * @pre Alle Spaltenbezeichner müssen eindeutig sein
     */
    public DBTable(final String anId, final Collection<String> someColIds) {
        assert anId != null;
        assert someColIds != null;
        assert !someColIds.isEmpty();
        assert isValidIdentifier(anId);
        assert areValidIdentifiers(someColIds);
        assert areOnlyUniqueValues(someColIds);

        Iterator<String> iterator = someColIds.iterator();

        this.name = anId;
        this.numberOfCols = someColIds.size();

        // Fuellt die Col Namen in die Head Map
        for (int i = 0; i < numberOfCols; i++) {
            this.head.put(iterator.next(), i);
        }
    }

    /**
     * Liefert den Bezeichner der Datenbanktabelle.
     *
     * @return Bezeichner der Datenbanktabelle.
     */
    public String getId() {

        return this.name;
    }

    /**
     * Prüft, ob die Tabelle eine Spalte mit dem Bezeichner aColId hat.
     * 
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = 1. (Da Hashset)
     *
     * @param aColId Bezeichner, der geprüft wird
     *
     * @pre aColId != null
     * @pre der Bezeichner aColId muss gültig sein
     *
     * @return boolscher Wert, der angibt, ob die Tabelle eine Spalte mit dem Bezeichner aColId hat
     */
    public boolean hasCol(final String aColId) {
        assert aColId != null;
        assert isValidIdentifier(aColId);

        return this.head.containsKey(aColId);
    }

    /**
     * Prüft, ob die Zeichenketten in someColIds Spaltenbezeichner dieser Tabelle sind.
     * 
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = n. (Mit n Anzahl der Zeichenketten in someColIds, da n * O(1)).
     *
     * @param someColIds Bezeichner, die geprüft werden
     * 
     * @pre someColIds != null
     * @pre someColIds muss mindestens einen Wert enthalten
     * @pre Alle Werte in someColIds müssen gültige Spaltenbezeichner sein
     * 
     * @return boolscher Wert, der angibt, ob alle Zeichenketten in someColIds Spaltenbezeichner
     *         dieser Tabelle sind
     */
    public boolean hasCols(final Collection<String> someColIds) {
        assert someColIds != null;
        assert someColIds.size() > 0;
        assert areValidIdentifiers(someColIds);

        Iterator<String> iterator = someColIds.iterator();

        while (iterator.hasNext()) {
            if (!this.hasCol(iterator.next())) {

                return false;
            }
        }

        return true;
    }

    /**
     * Liefert die Spaltenanzahl der Datenbanktabelle.
     * 
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = 1.
     * 
     * @return Spaltenanzahl der Datenbanktabelle.
     */
    public int getColCnt() {

        return this.head.size();
    }

    /**
     * Liefert die Zeilenanzahl der Datenbanktabelle.
     * 
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = 1.
     *
     * @return Zeilenanzahl der Datenbanktabelle.
     */
    public int getRowCnt() {

        return this.table.size();
    }

    /**
     * Fügt die Werte von row in der angegebenen Reihenfolge als letzte Zeile in die Tabelle ein.
     * Ein Iterator der Collection someColIds muss die Inhalte der Zeile in der Reihenfolge liefern,
     * in der sie in der Tabelle stehen sollen.
     * 
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = 1.
     *
     * @param row Werte, für die letzte Zeile
     * 
     * @pre row != null
     * @pre Die Anzahl der Werte in row muss der Spaltenanzahl der Tabelle entsprechen.
     */
    public void appendRow(final Collection<String> row) {
        assert row != null;
        assert row.size() == head.size();

        String[] rowValue = new String[head.size()];
        Iterator<String> rowIterator = row.iterator();

        for (int i = 0; i < rowValue.length; i++) {
            rowValue[i] = rowIterator.next();
        }

        this.table.add(rowValue);
    }

    /**
     * Löscht alle Zeilen der Tabelle.
     *
     * @post die Tabelle enthält keine Zeilen.
     */
    public void removeAllRows() {

        this.table.clear();

        assert this.table.size() == 0;
    }

    /**
     * Liefert eine Liste der Spaltenbezeichner. Die Reihenfolge entspricht dabei der Reihenfolge
     * der Spalten in der Tabelle.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = n.
     *
     * @return Liste der Spaltenbezeichner.
     */
    public List<String> getColIds() {

        String[] resultList = new String[this.head.size()];

        for (Map.Entry<String, Integer> entry : this.head.entrySet()) {

            resultList[entry.getValue()] = entry.getKey();
        }

        return Arrays.asList(resultList);
    }

    /**
     * Sortiert die Zeilen dieser Tabelle anhand der Werte in der Spalte mit dem Bezeichner aColId
     * in der Sortierreihenfolge sortDir.
     *
     * @param aColId Bezeichner der Spalte, nach der sortiert werden soll.
     * @param sortDir Reihenfolge, nach der sortiert werden soll.
     *
     * @pre aColId != null
     * @pre sortDir != null
     * @pre der Bezeichner aColId muss gültig sein
     * @pre die Tabelle muss eine Spalte mit dem Bezeichner aColId haben
     */
    public void sort(final String aColId, final SortDirection sortDir) {
        assert aColId != null;
        assert sortDir != null;
        assert isValidIdentifier(aColId);
        assert hasCol(aColId);

        int colToSortTo = head.get(aColId);

        Collections.sort(this.table, new TableCompare(colToSortTo, sortDir));
    }

    /**
     * Entfernt alle Zeilen aus dieser Tabelle, bei denen ein Test über dem Wert in der Spalte, die
     * mit aColId bezeichnet ist, erfolgreich ist.
     * 
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = ??? TODO.
     *
     * @param aColId Bezeichner der Spalte, deren Werte für den Test herangezogen werden sollen.
     * @param p Ein Predicate-Objekt zum Testen des jeweiligen Spaltenwertes
     *
     * @pre aColId != null
     * @pre p != null
     * @pre der Bezeichner aColId muss gültig sein
     * @pre die Tabelle muss eine Spalte mit dem Bezeichner aColId haben
     */
    public void removeRows(final String aColId, final Predicate<String> p) {
        assert aColId != null;
        assert p != null;
        assert isValidIdentifier(aColId);
        assert hasCol(aColId);

        Iterator<String[]> tableIterator = this.table.iterator();
        int colNum = head.get(aColId);

        while (tableIterator.hasNext()) {
            if (p.test(tableIterator.next()[colNum])) {
                tableIterator.remove();
            }
        }
    }

    /**
     * Erzeugt eine Tabelle mit dem Bezeichner newTableID, die alle Zeilen enthält, bei denen ein
     * Test über dem Wert in der Spalte, die mit aColId bezeichnet ist, erfolgreich ist.
     * 
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = n.
     *
     * @param aColId Bezeichner der Spalte, deren Werte für den Vergleich herangezogen werden
     *            sollen.
     * @param p Ein Predicate-Objekt zum Testen des jeweiligen Spaltenwertes
     * @param newTableId Bezeichner der erzeugten Tabelle.
     *
     * @pre aColId != null
     * @pre p != null
     * @pre newTableId != null
     * @pre der Bezeichner aColId muss gültig sein
     * @pre die Tabelle muss eine Spalte mit dem Bezeichner aColId haben
     * @pre der Bezeichner newTableId muss gültig sein
     *
     * @return erzeugte Tabelle.
     */
    public DBTable select(final String aColId, final Predicate<String> p, final String newTableId) {
        assert aColId != null;
        assert p != null;
        assert newTableId != null;
        assert isValidIdentifier(aColId);
        assert hasCol(aColId);
        assert isValidIdentifier(newTableId);

        DBTable newTable = new DBTable(newTableId, this.getColIds());
        Iterator<String[]> currentTableIterator = this.table.iterator();
        int colNum = head.get(aColId);

        while (currentTableIterator.hasNext()) {

            String[] currentRow = currentTableIterator.next();

            if (p.test(currentRow[colNum])) {
                newTable.appendRow(Arrays.asList(currentRow));
            }

        }

        return newTable;
    }

    /**
     * Erzeugt eine Tabelle mit dem Bezeichner newTableID, die alle Spalten enthält, deren
     * Bezeichner in someColIds aufgeführt sind, dabei wird die Reihenfolge der Spalten aus
     * someColIds übernommen. Ein Iterator der Collection someColIds muss demzufolge die
     * Spaltennamen in der Reihenfolge liefern, in der sie in der erzeugten Tabelle stehen sollen.
     *
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = n.
     *
     * @param someColIds Bezeichner der Spalten, die in die erzeugte Tabelle übernommen werden. Die
     *            Reihenfolge der Spalten in someColIds entspricht der in der erzeugten Tabelle.
     * @param newTableId Bezeichner der Tabelle, die erzeugt wird.
     *
     * @pre someColIds != null
     * @pre newTableId != null
     * @pre someCoIds muss mindestens einen Spaltenbezeichner enthalten
     * @pre zu allen Einträgen in someColIds gibt es eine entsprechende Spalte in der Tabelle
     * @pre der Bezeichner newTableId muss gültig sein
     *
     * @return die erzeugte Tabelle
     */
    public DBTable project(final Collection<String> someColIds, final String newTableId) {
        assert someColIds != null;
        assert newTableId != null;
        assert !someColIds.isEmpty();
        assert hasCols(someColIds);
        assert isValidIdentifier(newTableId);

        DBTable newTable = new DBTable(newTableId, someColIds);
        Iterator<String[]> currentTableIterator = this.table.iterator();

        // Iteriere alle Reihen und anschliessend die Spalten
        while (currentTableIterator.hasNext()) {

            String[] currentRow = currentTableIterator.next();
            String[] newRow = new String[someColIds.size()];

            Iterator<String> someColIdsIterator = someColIds.iterator();
            int i = 0;

            while (someColIdsIterator.hasNext()) {
                String colName = someColIdsIterator.next();
                int colNumber = this.head.get(colName);

                newRow[i] = currentRow[colNumber];
                i++;
            }
        }

        return newTable;
    }

    /**
     * Führt eine join-Operation mit der aktuellen und der übergebenen Tabelle other durch.
     * Hierbei wird eine neue Tabelle mit dem Bezeichner newTableID erzeugt, die alle Spalten beider
     * Tabellen enthält: zunächst die Spalten der aktuellen Tabelle und danach die Spalten der
     * übergebenen Tabelle. In der neuen Tabelle befinden sich alle Zeilen, in denen die Werte an
     * den Positionen der übergebenen Spaltenbezeichner (thisColId für die aktuelle Tabelle und
     * otherColId für die übergebene Tabelle) übereinstimmen. Es bleibt die Reihenfolge der
     * Zeilen aus der aktuellen Tabelle bzw. der Tabelle other erhalten.
     *
     * Die Spaltenbezeichner der neuen Tabelle werden aus den Spaltenbezeichnern der beiden
     * vorhandenen Tabellen erzeugt und zwar nach dem Schema, dass vor jeden vorhandenen Bezeichner
     * der Name der entsprechenden Ursprungstabelle gefolgt von einem Unterstrich geschrieben wird.
     *
     * @param other die Tabelle, mit der this gejoint werden soll
     * @param newTableId Bezeichner der Tabelle, die erzeugt wird.
     * @param thisColId Spaltenbezeichner der Spalte deren Werte in this verglichen werden.
     * @param otherColId Spaltenbezeichner der Spalte deren Werte in other verglichen werden.
     *
     * @pre other != null
     * @pre thisColId != null
     * @pre otherColId != null
     * @pre newTableId != null
     * @pre der Bezeichner newTableId muss gültig sein.
     * @pre die Tabellennamen der beiden Tabellen this und other müssen verschieden sein.
     * @pre der Bezeichner thisColId muss in der Tabelle this vorhanden sein
     * @pre der Bezeichner otherColId muss in der Tabelle other vorhanden sein
     * @post die Bezeichner der neuen Tabellenspalten müssen gültig sein
     * @post die Bezeichner der neuen Tabellenspalten müssen eindeutig sein
     *
     * @return die erzeugte Tabelle
     */
    public DBTable equijoin(final DBTable other, final String thisColId, final String otherColId,
            final String newTableId) {
        assert other != null;
        assert thisColId != null;
        assert otherColId != null;
        assert newTableId != null;
        assert isValidIdentifier(newTableId);
        assert this.name != other.name;
        assert this.hasCol(thisColId);
        assert other.hasCol(otherColId);

        List<String> allColId = new LinkedList<String>();
        List<String> table1Ids = this.getColIds();
        List<String> table2Ids = other.getColIds();

        // Alle Col bezeichner raussuchen und neue Tabelle initialisieren
        for (String id : table1Ids) {
            allColId.add(this.name + "_" + id);
        }

        for (String id : table2Ids) {
            allColId.add(other.name + "_" + id);
        }

        assert areValidIdentifiers(allColId);
        assert areOnlyUniqueValues(allColId);

        DBTable resultTable = new DBTable(newTableId, allColId);

        int table1Index = this.head.get(thisColId);

        // Iteriere alle Werte und uebertrage die Werte abheangig vom Vergleich
        for (String[] rows : this.table) {
            List<String[]> otherValues = other.select(otherColId,
                    new EqualsPredicate(rows[table1Index]), newTableId).table;

            for (String[] otherRows : otherValues) {
                List<String> resultRow = new LinkedList<String>();
                resultRow.addAll(Arrays.asList(rows));
                resultRow.addAll(Arrays.asList(otherRows));
                resultTable.appendRow(resultRow);
            }

        }

        return resultTable;
    }

    /**
     *
     * Liefert die Stringrepräsentation der Datenbanktabelle. Die Stringrepräsentation ist wie
     * folgt aufgebaut:
     * <ul>
     * <li>In der ersten Zeile stehen durch Kommata getrennt die Bezeichner der Spalten der
     * Datenbanktabelle.</li>
     * <li>Es folgen die Zeilen der Datenbanktabelle in der Reihenfolge, in der sie in der
     * Datenbanktabelle gespeichert sind. Die einzelnen Werte einer Zeile der Datenbanktabelle
     * werden durch Kommata getrennt hintereinander gereiht.</li>
     * </ul>
     *
     * Zeilenumbrüche (Unix, DOS, MacOs) innerhalb der Felder der Tabelle werden zugunsten der
     * besseren Lesbarkeit hierbei entfernt.
     *
     * Es werden stets Unix-Zeilenumbrüche (\n) verwendet.
     *
     * @return Stringrepräsentation der Datenbanktabelle.
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        List<String> colIds = getColIds();
        Iterator<String> colIdsIterator = colIds.iterator();
        int colCount = 0;

        // Uebertrage Werte fuer erste Zeile (cols)
        while (colIdsIterator.hasNext()) {
            String colId = colIdsIterator.next();

            if (colCount == colIds.size() - 1) {
                builder.append(colId);
            } else {
                builder.append(colId + ",");
            }
            colCount++;
        }

        builder.append("\n");

        Iterator<String[]> tableIterator = this.table.iterator();

        // Uebertrage Table Werte fuer die Table Line
        while (tableIterator.hasNext()) {
            String[] row = tableIterator.next();

            colCount = 0;

            for (String value : row) {
                if (colCount == colIds.size() - 1) {
                    builder.append(removeLineBreaks(value));
                } else {
                    builder.append(removeLineBreaks(value) + ",");
                }
                colCount++;
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Liefert die Stringrepräsentation der Datenbanktabelle so wie sie in eine Datei geschrieben
     * wird.
     *
     * Diese Stringrepräsentation ist wie folgt aufgebaut:
     * <ul>
     * <li>In der ersten Zeile steht nur der Bezeichner der Datenbanktabelle.</li>
     * <li>In der zweiten Zeile stehen durch Kommata getrennt die Bezeichner der Spalten der
     * Datenbanktabelle.</li>
     * <li>Es folgen die Zeilen der Datenbanktabelle in der Reihenfolge, in der sie in der
     * Datenbanktabelle gespeichert sind, in einer einzigen Zeile. Die Felder der Zeilen werden
     * durch Kommata voneinander getrennt und hintereinander gereiht.</li>
     * </ul>
     *
     * Da in den Tabellenfeldern alle Zeichen erlaubt sind, werden sowohl Kommata (",") als auch
     * Backslashes ("\") bei dieser Ausgabe jeweils mit einem Backslash ("\") gequotet.
     *
     * Es werden stets Unix-Zeilenumbrüche (\n) verwendet.
     *
     * @return Dateirepräsentation der Datenbanktabelle.
     */
    public String toFile() {

        List<String> colIds = getColIds();
        Iterator<String> colIdsIterator = colIds.iterator();
        StringBuilder builder = new StringBuilder();
        int colCount = 0;

        // Schreibt erste Zeile
        builder.append(this.name + "\n");

        // Schreibe die zweite Zeile
        while (colIdsIterator.hasNext()) {
            String colId = colIdsIterator.next();

            if (colCount == colIds.size() - 1) {
                builder.append(colId);
            } else {
                builder.append(colId + ",");
            }
            colCount++;
        }

        // Uebertrage Werte der Tabelle in dritte Zeile
        if (!this.table.isEmpty()) {
            builder.append("\n");

            Iterator<String[]> tableIterator = this.table.iterator();

            while (tableIterator.hasNext()) {
                String[] row = tableIterator.next();

                colCount = 0;

                for (String value : row) {
                    if (colCount == colIds.size() - 1) {
                        builder.append(value.replace("\\", "\\\\").replace(",", "\\,"));
                    } else {
                        builder.append(value.replace("\\", "\\\\").replace(",", "\\,") + ",");
                    }
                    colCount++;
                }

                if (tableIterator.hasNext()) {
                    builder.append(",");
                }
            }
        }

        return builder.toString();
    }

    /**
     * Prüft, ob die Zeichenkette str ein gültiger Bezeichner für eine Datenbanktabelle bzw. die
     * Spalte einer Datenbanktabelle ist. Ein valider Bezeichner besteht aus einem Zeichen aus der
     * Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
     *
     * 
     * @param str Zeichenkette, die geprüft wird.
     * 
     * @pre str != null
     *
     * @return boolscher Wert, der angibt, ob die Zeichenkette str ein gültiger Bezeichner ist.
     */
    public static boolean isValidIdentifier(final String str) {
        assert str != null;

        return str.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
    }

    /**
     * Prüft, ob in dem Array aus Zeichenketten strs nur gültige Bezeichner für eine
     * Datenbanktabelle bzw. die Spalte einer Datenbanktabelle ist. Ein valider Bezeichner besteht
     * aus einem Zeichen aus der Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus
     * der Menge [a-zA-Z0-9_].
     * 
     * @param strs Bezeichner, die geprüft werden
     * 
     * @pre strs != null
     *
     * @return boolscher Wert, der angibt, ob das Array strs nur gültige Bezeichner enthält.
     */
    public static boolean areValidIdentifiers(final Collection<String> strs) {
        assert strs != null;

        Iterator<String> iterator = strs.iterator();

        while (iterator.hasNext()) {
            if (!isValidIdentifier(iterator.next())) {

                return false;
            }
        }

        return true;
    }

    /**
     * Prüft, ob alle übergebenen Strings eindeutig sind. Sollte mindestens eine Zeichenkette
     * doppelt vorkommen, so gibt die Methode false zurück.
     *
     * @param strs Bezeichner, die geprüft werden
     * 
     * @pre strs != null
     * 
     * @return true, wenn alle Zeichenketten einmalig sind
     */
    public static boolean areOnlyUniqueValues(final Collection<String> strs) {
        assert strs != null;

        Set<String> compareSet = new HashSet<String>(strs);

        return strs.size() == compareSet.size();
    }

    // *********** HELPER ***********//

    /**
     * Entfernt die Zeilenumbrueche von den uebergebenen String
     *
     * @param string der String der veraendert werden soll
     * 
     * @return der veraenderte String
     */
    private String removeLineBreaks(String string) {

        return string.replace("\r\n", "").replace("\n", "").replace("\r", "");
    }

    /**
     * Eine Hilfsklasse zum vergleichen von Spaltenwerten der Tabelle mit Sortierung
     * 
     * @author Alexander Loeffler
     */
    private class TableCompare implements Comparator<String[]> {

        // der int Wert fuer die Spalte
        private int col;

        // die Richtung nach der Sortiert werden soll
        private SortDirection dir;

        /**
         * Konstruktor
         * 
         * @param col der int Wert fuer die Spalte
         * @param dir die Richtung nach der Sortiert werden soll
         */
        TableCompare(int col, SortDirection dir) {
            this.col = col;
            this.dir = dir;
        }

        @Override
        public int compare(String[] firstRow, String[] secondRow) {

            return (dir == SortDirection.ASC) ? firstRow[this.col].compareTo(secondRow[this.col])
                    : firstRow[this.col].compareTo(secondRow[this.col]) * -1;
        }

    }

    /**
     * Eine Hilfsklasse zum vergleichen von Stringwerten auf Equals
     * 
     * @author alexander loeffler
     */
    public class EqualsPredicate implements Predicate<String> {

        // der zu vergleichene String
        private final String compareString;

        public EqualsPredicate(String compareString) {
            this.compareString = compareString;
        }

        @Override
        public boolean test(String t) {
            return t.equals(compareString);
        }
    }

}
