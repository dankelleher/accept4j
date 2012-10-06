package org.accept4j.specification.generator

import jxl.Workbook
import jxl.Sheet
import org.accept4j.specification.SpecParseException
import jxl.read.biff.BiffException
import org.accept4j.testpack.AcceptanceTestSuite
import jxl.CellType
import org.accept4j.testpack.AcceptanceTestItem

/**
 * Copyright: Daniel Kelleher Date: 05.10.12 Time: 00:00
 */
class ExcelSpecAdapter {
    Workbook book
    Sheet sheet

    def header = ["Suite", "Group", "Pack", "Test ID", "Name", "Description"]

    ExcelSpecAdapter(String filename) {
        try {
            book = Workbook.getWorkbook(new File(filename))
            setSheet()
            if (!validHeader()) throw new SpecParseException("Invalid header in spec sheet")
        } catch (BiffException e) {
            throw new SpecParseException(e)
        }
    }
    
    AcceptanceTestSuite getSuite() {
        AcceptanceTestSuite suite = new AcceptanceTestSuite()
        int rows = sheet.rows
        
        (1..<rows).each { row ->
            def testRow = toSuiteMap(getCells(0..<header.size(), row))
            def test = new AcceptanceTestItem(id: testRow["Test ID"], name: testRow.Name, description: testRow.Description)
            suite
                .findOrCreate(testRow.Group)
                .findOrCreate(testRow.Pack)
                .add(test)
        }

        return suite
    }

    // change to a map so we don't have to reference indices everywhere, allows us to change the order/contents later
    private def toSuiteMap(def cells) {
        int index = 0;
        return cells.collectEntries {[(header[index++]) : it]}
    }

    private boolean validHeader() {
        def actualHeader = getCells(0..<header.size(), 0)

        return header.equals(actualHeader)
    }

    private def getCells(def colRange, int row) {
        return colRange.collect { getCell(it, row) }
    }

    private String getCell(int col, int row) {
        return sheet.getCell(col, row).contents
    }

    private void setSheet() {
        sheet = book.getSheet("spec")
        if (!sheet) {
            sheet = book.getSheet(0)
        }

        if (!sheet) throw new SpecParseException("Spreadsheet not found in Excel spec. Sheet must be the first sheet in the workbook or named 'spec'")
    }
}
