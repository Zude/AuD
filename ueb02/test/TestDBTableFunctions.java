import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import db.DBTable;
import db.SortDirection;

public class TestDBTableFunctions {

    @Test
    public final void isValidIdentifier() {

        assertEquals(true, DBTable.isValidIdentifier("Hal9_oo"));
        assertEquals(false, DBTable.isValidIdentifier("9al9_oo"));
        assertEquals(true, DBTable.isValidIdentifier("a"));
        assertEquals(false, DBTable.isValidIdentifier("_"));
        assertEquals(false, DBTable.isValidIdentifier("Hal9_o!"));
        assertEquals(true, DBTable.isValidIdentifier("H"));
        assertEquals(false, DBTable.isValidIdentifier(""));
        assertEquals(false, DBTable.isValidIdentifier(" "));
        assertEquals(false, DBTable.isValidIdentifier("1"));
        assertEquals(false, DBTable.isValidIdentifier("!"));
        assertEquals(false, DBTable.isValidIdentifier("A!"));
        assertEquals(false, DBTable.isValidIdentifier("@"));
        assertEquals(true, DBTable.isValidIdentifier("A_______1234569854_______aaaaassss"));
    }

    @Test
    public final void areValidIdentifiers() {

        ArrayList<String> strList1 = new ArrayList<String>();
        strList1.add("Hallo");
        strList1.add("H");
        strList1.add("a");
        strList1.add("H___347838");
        strList1.add("H___3478__KJFLNHJShjkfhdsjkfh38");
        assertEquals(true, DBTable.areValidIdentifiers(strList1));

        ArrayList<String> strList2 = new ArrayList<String>();
        strList2.add("Hallo");
        strList2.add("H");
        strList2.add("");
        strList2.add("a");
        strList2.add(" ");
        strList2.add("H___3478__KJFLNHJShjkfhdsjkfh38");
        strList2.add("2");
        assertEquals(false, DBTable.areValidIdentifiers(strList2));
    }

    @Test
    public final void areOnlyUniqueValues() {

        ArrayList<String> strList1 = new ArrayList<String>();
        strList1.add("Hallo");
        strList1.add("H");
        strList1.add("a");
        strList1.add("H___347838");
        strList1.add("H___3478__KJFLNHJShjkfhdsjkfh38");
        assertEquals(true, DBTable.areOnlyUniqueValues(strList1));

        ArrayList<String> strList2 = new ArrayList<String>();
        strList2.add("Hallo");
        strList2.add("H");
        strList2.add("");
        strList2.add("a");
        strList2.add(" ");
        strList2.add("");
        strList2.add("H___3478__KJFLNHJShjkfhdsjkfh38");
        strList2.add("2");
        assertEquals(false, DBTable.areValidIdentifiers(strList2));

        ArrayList<String> strList3 = new ArrayList<String>();
        strList3.add("Hallo");
        strList3.add("H");
        strList3.add("");
        strList3.add("H___3478__KJFLNHJShjkhsdhfajksfjkdsafhdkjsfhsjkafhdsjkfh38");
        strList3.add("a");
        strList3.add(" ");
        strList3.add("dasdagsagadfa");
        strList3.add("H___3478__KJFLNHJShjkhsdhfajksfjkdsafhdkjsfhsjkafhdsjkfh38");
        strList3.add("2");
        assertEquals(false, DBTable.areValidIdentifiers(strList3));
    }

    @Test
    public final void sort() {

        ArrayList<String> colList1 = new ArrayList<String>();
        colList1.add("Col1");
        colList1.add("Col2");
        colList1.add("Col3");

        DBTable table1 = new DBTable("TESTTABLE1", colList1);

        List<String> values1 = new LinkedList<String>();
        values1.add("1");
        values1.add("1");
        values1.add("1");
        table1.appendRow(values1);

        List<String> values2 = new LinkedList<String>();
        values2.add("2");
        values2.add("2");
        values2.add("2");
        table1.appendRow(values2);

        List<String> values3 = new LinkedList<String>();
        values3.add("3");
        values3.add("3");
        values3.add("3");
        table1.appendRow(values3);

        table1.sort("Col1", SortDirection.ASC);

        System.out.println(table1);
    }

    @Test
    public final void join() {

        ArrayList<String> colList1 = new ArrayList<String>();
        colList1.add("Col1");
        colList1.add("Col2");
        colList1.add("Col3");

        DBTable table1 = new DBTable("TESTTABLE1", colList1);

        ArrayList<String> colList2 = new ArrayList<String>();
        colList2.add("ColA");
        colList2.add("ColB");
        colList2.add("ColC");

        DBTable table2 = new DBTable("TESTTABLE2", colList2);

        List<String> values1 = new LinkedList<String>();
        values1.add("1");
        values1.add("2");
        values1.add("3");
        table1.appendRow(values1);
        table2.appendRow(values1);

        List<String> values2 = new LinkedList<String>();
        values2.add("A");
        values2.add("B");
        values2.add("C");
        table1.appendRow(values2);
        table2.appendRow(values2);

        DBTable table3 = table1.equijoin(table2, "Col1", "ColC", "table3");

        System.out.println(table3);

    }

    @Test
    public final void project() {

        ArrayList<String> colList1 = new ArrayList<String>();
        colList1.add("Col1");
        colList1.add("Col2");
        colList1.add("Col3");

        DBTable table1 = new DBTable("TESTTABLE1", colList1);

        ArrayList<String> colList2 = new ArrayList<String>();
        colList2.add("ColA");
        colList2.add("ColB");
        colList2.add("ColC");

        DBTable table2 = new DBTable("TESTTABLE2", colList2);

        List<String> values1 = new LinkedList<String>();
        values1.add("1");
        values1.add("2");
        values1.add("3");
        table1.appendRow(values1);
        table2.appendRow(values1);

        List<String> values2 = new LinkedList<String>();
        values2.add("A");
        values2.add("B");
        values2.add("C");
        table1.appendRow(values2);
        table2.appendRow(values2);

        DBTable table3 = table1.project(values2, "Test");

        System.out.println(table3);
    }
}
