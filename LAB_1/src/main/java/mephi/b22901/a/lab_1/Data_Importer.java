/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class Data_Importer {

    public static Data_Sample importer(String inputFileName, int sheetIndex) throws IOException {
       
        if (inputFileName == null || inputFileName.isEmpty() || sheetIndex < 0) {
            throw new IllegalArgumentException("Введите корректный путь и номер листа.");
        }

       
        File file = new File(inputFileName);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден.");
        }

       
        if (!inputFileName.toLowerCase().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Файл должен быть в формате XLSX.");
        }

        Data_Sample dataSample = new Data_Sample();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            
            int numberOfSheets = workbook.getNumberOfSheets();
            if (sheetIndex >= numberOfSheets) {
                throw new IllegalArgumentException("Номер листа должен быть от 1 до " + (numberOfSheets));
            }

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
            int rowCount = sheet.getPhysicalNumberOfRows();

         
            for (int k = 0; k < columnCount; k++) {
                double[] selection = new double[rowCount - 1];
                for (int i = 0; i < rowCount - 1; i++) {
                    Row row = sheet.getRow(i + 1);
                    Cell cell = row.getCell(k);

                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case FORMULA:
                                CellValue cellValue = evaluator.evaluate(cell);
                                if (cellValue.getCellType() == CellType.NUMERIC) {
                                    selection[i] = cellValue.getNumberValue();
                                } else {
                                    selection[i] = Double.NaN;
                                }
                                break;
                            case NUMERIC:
                                selection[i] = cell.getNumericCellValue();
                                break;
                            default:
                                selection[i] = Double.NaN;
                                break;
                        }
                    } else {
                        selection[i] = Double.NaN;
                    }
                }
                dataSample.getDataMap().put(sheet.getRow(0).getCell(k).toString(), selection);
                System.out.println(sheet.getRow(0).getCell(k).toString());
            }
        }

        return dataSample;
    }
}