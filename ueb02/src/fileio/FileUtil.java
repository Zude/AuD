package fileio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import db.DBTable;

/**
 * Bietet Methoden für das Lesen und Schreiben von {@link DBTable}s aus bzw. in Dateien.
 *
 * @author kar, mhe, Alexander Loeffler
 *
 */
public class FileUtil {

    private final int correctNumberOfLines = 3;

    /**
     * Liest eine Text-Datei und interpretiert diese als {@link DBTable}. Dabei wird das Quoting
     * innerhalb der einzelnen Felder wieder rückgängig gemacht, so dass sich die Inhalte der
     * Tabelle wieder im Originalzustand befinden. (siehe
     * {@link FileUtil#writeTableToFile(String,DBTable)}).
     *
     * Syntaktische Fehler in der Datei werden dabei über eine WrongSyntaxException signalisiert.
     * Zu den syntaktischen Fehlern zählen hierbei auch nicht valide Bezeichner und die Verletzung
     * der Konsistenzbedingungen für Bezeichner der Datenbank (also verletzte Vorbedingungen).
     *
     * @param filename Dateiname
     * 
     * @pre filename != null
     *
     * @return Tabelle als DBTable
     * @throws IOException Fehler beim Einlesen der Datei
     * @throws WrongSyntaxException Fehler in der Syntax der Eingabedatei
     */
    public DBTable readTableFromFile(final String filename)
            throws IOException, WrongSyntaxException {
        assert filename != null;

        // "test/testdata/simple2"
        PushbackReader push = new PushbackReader(new FileReader(filename));
        DBTable resultTable;

        String resultString = "";
        String[] lines;
        String[] cols;
        // String[] entrys;
        List<String> allEntrys = new LinkedList<String>();

        int numberOfCols = 1;
        int numberOfEntrys = 0;
        int numberOfRows = 0;
        int currentChar;
        int currentLine = 1;

        // Liest jedes Zeichen der Datei und quote Backslash und Komma beim uebertragen
        while ((currentChar = push.read()) != -1) {
            // Liest das aktuelle Zeichen und faehrt jeh nach ergebniss fort
            if (currentChar == '\\') {
                int nextChar = push.read();
                if (nextChar == '\\') {
                    resultString = resultString + (char) currentChar;
                } else if (nextChar == ',') {
                    resultString = resultString + (char) nextChar;
                } else {
                    push.unread(nextChar);
                }
            } else if (currentChar == ',') {
                if (currentLine == 2) {
                    resultString = resultString + (char) currentChar;
                } else {
                    allEntrys.add(resultString);
                    resultString = "";
                }
            } else if (currentChar == '\n' || currentChar == '\r') {
                int nextChar = push.read();
                if (nextChar == '\n') {
                    if (currentLine == correctNumberOfLines) {
                        resultString = resultString + (char) currentChar;
                        resultString = resultString + (char) nextChar;
                    } else {
                        currentLine++;
                        allEntrys.add(resultString);
                        resultString = "";
                    }
                } else {
                    if (currentLine == correctNumberOfLines) {
                        resultString = resultString + (char) currentChar;
                    } else {
                        currentLine++;
                        push.unread(nextChar);
                        allEntrys.add(resultString);
                        resultString = "";
                    }
                }
            } else {
                resultString = resultString + (char) currentChar;
            }
        }

        allEntrys.add(resultString);

        push.close();

        if (currentLine > correctNumberOfLines) {
            throw new WrongSyntaxException("Die Datei hat ein falsches Format!");
        }

        // Uebertragen der Werte
        Iterator<String> iterator = allEntrys.iterator();

        // Name uebtragen
        String tableName;
        if (iterator.hasNext()) {
            tableName = iterator.next();
        } else {
            throw new WrongSyntaxException("Die Datei hat ein falsches Format!");
        }

        // Cols uebertragen
        if (iterator.hasNext()) {
            cols = iterator.next().split(",");
        } else {
            throw new WrongSyntaxException("Die Datei hat ein falsches Format!");
        }

        // Values uebertragen
        List<String> entrys = new ArrayList<String>();
        while (iterator.hasNext()) {
            entrys.add(iterator.next());
        }

        // Table intitialisieren
        resultTable = new DBTable(tableName, Arrays.asList(cols));

        if (!DBTable.areOnlyUniqueValues(Arrays.asList(cols))
                || !DBTable.areValidIdentifiers(Arrays.asList(cols))
                || !DBTable.isValidIdentifier(tableName)) {
            throw new WrongSyntaxException("Die Datei hat ein falsches Format!");
        }

        if (currentLine == correctNumberOfLines) {

            numberOfCols = cols.length;
            numberOfEntrys = entrys.size();
            numberOfRows = numberOfEntrys / numberOfCols;

            if (numberOfEntrys % numberOfCols != 0) {
                throw new WrongSyntaxException("Die Datei hat ein falsches Format!");
            }

            // Durchlaufe alle Eintraege und uebertrage die Werte in den neuen DBTable
            for (int j = 0; j < numberOfRows; j++) {
                List<String> rowValueList = new LinkedList<String>();
                for (int x = 0; x < numberOfCols; x++) {
                    rowValueList.add(entrys.get(x + (numberOfCols * j)));
                }
                resultTable.appendRow(rowValueList);
            }
        }

        return resultTable;
    }

    /**
     * Schreibt die {@link DBTable} in die Datei namens filename. Die Syntax und Semantik der
     * Ausgabe sind identisch zu {@link DBTable#toFile()}.
     *
     * @param filename Dateiname
     * @param table Tabelle, welche geschrieben werden soll
     * 
     * @pre filename != null
     * @pre table != null
     *
     * @throws IOException Fehler beim Schreiben der Datei
     */
    public void writeTableToFile(final String filename, final DBTable table) throws IOException {
        assert filename != null;
        assert table != null;

        File output = new File(filename);
        FileWriter writer = new FileWriter(output);

        writer.write(table.toFile());
        writer.flush();
        writer.close();
    }

}
