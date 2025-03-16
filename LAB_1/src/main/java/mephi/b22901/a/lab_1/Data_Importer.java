/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Data_Importer {
    
    public static Data_Sample importer(String inputFileName, int sheetIndex) {
        Data_Sample dataSample = new Data_Sample();

        try (FileInputStream fis = new FileInputStream(inputFileName);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex); 

            if (sheet.getPhysicalNumberOfRows() == 0) {
                return dataSample;
            }

            int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int k = 0; k < columnCount; k++) {
                double[] selection = new double[rowCount - 1];
                for (int i = 0; i < rowCount - 1; i++) {
                    try {
                        selection[i] = sheet.getRow(i + 1).getCell(k).getNumericCellValue();
                    } catch (Exception e) {
                        selection[i] = Double.NaN; 
                    }
                }
                dataSample.getDataMap().put(sheet.getRow(0).getCell(k).toString(), selection);
                System.out.println(sheet.getRow(0).getCell(k).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataSample.setDataMap(dataSample.getDataMap());
        return dataSample;
    }
}