import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Assert;

import db.DBTable;
import fileio.FileUtil;
import fileio.WrongSyntaxException;

/**
 * Klasse mit (statischen) (Hilfs-)methoden für JUnit-Tests. Die Klasse beinhaltet unter Methoden
 * zum Vergleichen von Dateien.
 * 
 * @author kar, mhe
 */
public class TestToolkit {

    /**
     * Ermittelt, ob der Inhalt der Dateien fileName1 und fileName2 identisch ist. Funktioniert nur
     * für Dateien bis zu einer Größe von 2GB.
     * 
     * @param fileName1 erste Datei.
     * @param fileName2 zweite Datei.
     * 
     * @return boolscher Wert, der angibt, ob der Inhalt der Dateien fileName1 und fileName2
     *         identisch ist.
     * 
     * @throws InterruptedException Externer Fehler
     * @throws IOException Dateifehler
     */

    public static boolean filesAreEqual(String fileName1, String fileName2)
            throws InterruptedException, IOException {

        Path f1 = Paths.get(fileName1);
        Path f2 = Paths.get(fileName2);

        long size = Files.size(f1);
        if (size != Files.size(f2)) {
            return false;
        }

        return Arrays.equals(Files.readAllBytes(f1), Files.readAllBytes(f2));
    }

    /**
     * Liest eine Datei mit dem Namen filename aus dem test-Verzeichnis ein.
     * 
     * @param filename Dateiname
     * @return DBTable Datenbanktabelle
     * 
     * @throws IOException Dateifehler
     * @throws WrongSyntaxException Syntaktischer Fehler beim Einlesen der Datei
     * 
     */
    public static DBTable read(String filename) throws IOException, WrongSyntaxException {
        final FileUtil u = new FileUtil();
        return u.readTableFromFile("test/testdata/" + filename);
    }

    /**
     * Vergleicht zwei Dateien mit dem gleichen Namen (ohne Endung) aus den festgelegten Ordnern
     * miteinander und prüft sie auf Gleichheit.
     * 
     * @param filename Dateiname ohne Endung
     * @throws IOException
     * @throws InterruptedException
     */
    public static void assertFilesEqual(String filename) throws InterruptedException, IOException {
        Assert.assertTrue("Dateien sind nicht gleich! : " + filename, filesAreEqual(
                "test/results/" + filename + ".out", "test/expected_results/" + filename + ".exp"));
    }

    /**
     * Erzeugt eine Ausgabedatei und vergleicht sich mit der erwarteten Datei auf Gleichheit. Wenn
     * der Ordner results in dem die Ausgabedateien liegen sollen noch nicht vorhanden ist, wird er
     * erzeugt.
     * 
     * @param table Datenbanktabelle
     * @param filename Dateiname ohne Endung
     * @throws IOException Dateifehler
     * @throws InterruptedException
     */
    public static void writeAndAssert(DBTable table, String filename)
            throws IOException, InterruptedException {
        final FileUtil u = new FileUtil();

        // "test/results"
        File resultsDir = new File(filename);
        resultsDir.mkdir();

        u.writeTableToFile("test/results/" + filename + ".out", table);

        assertFilesEqual(filename);
    }

}
