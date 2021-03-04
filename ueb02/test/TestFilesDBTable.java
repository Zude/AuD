import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import db.DBTable;
import fileio.FileUtil;
import fileio.WrongSyntaxException;

/**
 * Beispielhafte Tests zum Dateivergleich.
 *
 * @author kar, mhe
 */
public class TestFilesDBTable {

    /** Testtabelle mit einer Spalte. */
    private DBTable tab1 = null;

    /**
     * Intialisierung für alle Tests.
     */
    @Before
    public void setUp() {
        this.tab1 = this.createTable1();
    }

    /**
     * Aufräummethode für alle Tests. Die Instanzen der Tabellenklasse werden gelöscht.
     */
    @After
    public void tearDown() {
        this.tab1 = null;
    }

    @Test
    public final void testWriteFile() throws IOException, InterruptedException {
        TestToolkit.writeAndAssert(tab1, "simple");
    }

    @Test
    public final void testReadFile()
            throws IOException, WrongSyntaxException, InterruptedException {
        DBTable tab = TestToolkit.read("simple");

        Assert.assertEquals(1, tab.getColCnt());
        Assert.assertEquals(3, tab.getRowCnt());
        assertEquals(TestValues.TABLE_1_ID, tab.getId());

    }

    @Test
    public final void testOwnFile() throws IOException, WrongSyntaxException, InterruptedException {
        DBTable tab = TestToolkit.read("alexIn");

        final FileUtil u = new FileUtil();

        // "test/results"
        File resultsDir = new File("alexOut");
        resultsDir.mkdir();

        u.writeTableToFile("test/results/alexOut", tab);
    }

    /**
     * Erzeugt eine Tabelle mit dem Bezeichner {@link TestValues#TABLE_1_ID}, den Spalten
     * {@link TestValues#TABLE_1_COL_IDS} und einer Zeile, die dem ersten Wert von
     * {@link TestValues#TABLE_1_ROWS} entspricht.
     *
     * @return die erzeugte Tabelle.
     */
    private DBTable createTable1() {
        final DBTable tab =
                new DBTable(TestValues.TABLE_1_ID, Arrays.asList(TestValues.TABLE_1_COL_IDS));

        tab.appendRow(Arrays.asList(TestValues.TABLE_1_ROWS[0]));
        tab.appendRow(Arrays.asList(TestValues.TABLE_1_ROWS[1]));
        tab.appendRow(Arrays.asList(TestValues.TABLE_1_ROWS[2]));

        return tab;
    }

}
