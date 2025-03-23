/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Data_Exporter {

    public static void exporter(Map<String, Map<String, Double>> statsData, String filePath) throws IOException {
       
        if (statsData == null || statsData.isEmpty()) {
            throw new IllegalArgumentException("Нет данных для экспорта!");
        }

       
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Введите путь для сохранения файла.");
        }

      
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

       
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Не удалось создать директорию: " + parentDir.getAbsolutePath());
            }
        }

      
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Статистика");

        int rowIndex = 0;

       
        Row headerRow = sheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Показатель");

        int colIndex = 1;
        for (String sampleName : statsData.keySet()) {
            if (!sampleName.startsWith("Ковариация")) {
                headerRow.createCell(colIndex).setCellValue(sampleName);
                colIndex++;
            }
        }

       
        for (String pokazName : statsData.values().iterator().next().keySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(pokazName);

            int column = 1;
            for (String sampleName : statsData.keySet()) {
                if (!sampleName.startsWith("Ковариация")) {
                    Double value = statsData.get(sampleName).get(pokazName);
                    Cell cell = row.createCell(column);
                    if (value != null) {
                        cell.setCellValue(value);
                    } else {
                        cell.setCellValue(0.0); // Заполнение нулём, если значение отсутствует
                    }
                    column++;
                }
            }
        }

       
        rowIndex++;
        Row exRow = sheet.createRow(rowIndex++);
        exRow.createCell(0).setCellValue("Ковариационная матрица");

        for (String covKey : statsData.keySet()) {
            if (covKey.startsWith("Ковариация")) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(covKey);

                colIndex = 1;
                Map<String, Double> covariances = statsData.get(covKey);
                for (String sampleName : statsData.keySet()) {
                    if (!sampleName.startsWith("Ковариация")) {
                        Double value = covariances.get(sampleName);
                        Cell cell = row.createCell(colIndex);
                        if (value != null) {
                            cell.setCellValue(value);
                        } else {
                            cell.setCellValue(Double.NaN); // Заполнение NaN, если значение отсутствует
                        }
                        colIndex++;
                    }
                }
            }
        }

      
        for (int i = 0; i < colIndex; i++) {
            sheet.autoSizeColumn(i);
        }

       
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }
    }
}
