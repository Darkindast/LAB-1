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
    public static void exportToExcel(Map<String, Map<String, Double>> statsData, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Статистика");

        int rowIndex = 0;

        // Создание заголовков (первая строка)
        Row headerRow = sheet.createRow(rowIndex++);
        int colIndex = 1;  // Начинаем со второго столбца (A — Названия метрик, B, C, D — выборки)
        for (String sampleName : statsData.keySet()) {
            Cell cell = headerRow.createCell(colIndex++);
            cell.setCellValue(sampleName);
        }

        // Заполняем статистику по каждой метрике
        Map<String, Double> firstSampleStats = statsData.values().iterator().next(); // Берем первую выборку для ключей
        for (String metricName : firstSampleStats.keySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(metricName); // В колонку A пишем название метрики

            int column = 1;
            for (String sampleName : statsData.keySet()) {
                Double value = statsData.get(sampleName).get(metricName);
                row.createCell(column++).setCellValue(value);
            }
        }

        // Сохранение файла
        try (FileOutputStream fileOut = new FileOutputStream(new File(filePath))) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
