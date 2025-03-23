/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Controller {
    private GUI view = new GUI();
    private Data_Sample dataSample; 
    private Statistics stat; 

    public Controller() {
        view.setVisible(true);
        view.addImportListener(e -> importData());
        view.addExportListener(e -> exportData());
        view.addStatsListener(e -> stats());
        view.addExitListener(e -> System.exit(0));
    }

     public void importData() {
        String filePath = view.getImportFilePath();
        int sheetIndex = view.getSheetIndex();

        try {

            dataSample = Data_Importer.importer(filePath, sheetIndex);
            Map<String, double[]> dataMap = dataSample.getDataMap();

 
            StringBuilder result = new StringBuilder("Импортированные данные:\n");
            dataMap.forEach((key, value) ->
                result.append(key).append(" = ").append(java.util.Arrays.toString(value)).append("\n")
            );

            view.setResultText(result.toString());
        } catch (Exception e) {
            view.showError("Ошибка при импорте: " + e.getMessage());
        }
    }
    
    public void stats() {
        if (dataSample == null || dataSample.getDataMap().isEmpty()) {
            view.showError("Сначала импортируйте данные.");
            return;
        }

        
        stat= new Statistics(dataSample);
        
        Map<String, Map<String, Double>> statsData = stat.getStatistics();
        
        StringBuilder statsResult = new StringBuilder("Статистика данных:\n");

        for (Map.Entry<String, Map<String, Double>> entry : statsData.entrySet()) {
            statsResult.append("\nВыборка: ").append(entry.getKey()).append("\n");
            for (Map.Entry<String, Double> statEntry : entry.getValue().entrySet()) {
                statsResult.append(statEntry.getKey()).append(": ").append(statEntry.getValue()).append("\n");
            }
        }

        view.setResultText(statsResult.toString());
    }
    
    public void exportData() {
        if (dataSample == null || dataSample.getDataMap().isEmpty()) {
            view.showError("Нет данных для экспорта!");
            return;
        }

        if (stat == null || stat.getStatistics().isEmpty()) {
            view.showError("Сначала рассчитайте статистику перед экспортом!");
            return;
        }

        String filePath = view.getExportFilePath();

        try {

            Data_Exporter.exporter(stat.getStatistics(), filePath);
            view.setResultText("Данные успешно экспортированы в файл: " + filePath);
        } catch (Exception e) {
            view.showError("Ошибка при экспорте: " + e.getMessage());
        }
    }

}
