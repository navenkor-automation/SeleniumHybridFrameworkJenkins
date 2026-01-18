package com.qa.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {
    // This path must match exactly where you pasted the file
    public static String TESTDATA_SHEET_PATH = "src/test/resources/TestData.xlsx";
    static Workbook book;
    static Sheet sheet;

    public static Object[][] getTestData(String sheetName) {
        Object[][] data = null;
        try {
            FileInputStream ip = new FileInputStream(TESTDATA_SHEET_PATH);
            book = WorkbookFactory.create(ip);
            sheet = book.getSheet(sheetName);
            
            if (sheet == null) {
                System.out.println("Sheet " + sheetName + " not found in Excel file!");
                return new Object[0][0];
            }

            // DataFormatter ensures we get Strings even from numeric/date cells
            DataFormatter formatter = new DataFormatter();

            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
            
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                    // Merged your loop with the DataFormatter for stability
                    Cell cell = sheet.getRow(i + 1).getCell(k);
                    data[i][k] = formatter.formatCellValue(cell);
                }
            }
            
            ip.close(); // Important for Hybrid frameworks to release file resources
            
        } catch (FileNotFoundException e) {
            System.err.println("Excel file not found at: " + TESTDATA_SHEET_PATH);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}