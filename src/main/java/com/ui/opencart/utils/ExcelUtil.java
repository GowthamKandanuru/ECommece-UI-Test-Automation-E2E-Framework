package com.ui.opencart.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelUtil {

    private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/TestData.xlsx";

    private static Workbook workbook;
    private static Sheet sheet;

    private static ArrayList<Object> td = new ArrayList<>();


    public static Object[][] getTestData(String sheetName) {
        Object[][] data = null;
        try {
            FileInputStream fis = new FileInputStream(TEST_DATA_SHEET_PATH);
            workbook = WorkbookFactory.create(fis);
            sheet = workbook.getSheet("ProductDetails");
            int rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();
            data = new Object[rowcount][sheet.getRow(0).getLastCellNum()];
            for (int i = 0; i < rowcount; i++) {
                int cellCount = sheet.getRow(i).getLastCellNum();
                for (int j = 0; j < cellCount; j++) {
                    data[i][j] = sheet.getRow(i+1).getCell(j).toString();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
