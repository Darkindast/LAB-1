/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import javax.swing.JTextField;

public class Controller {
    private GUI view;
    private Data_Importer dataImporter;
    private Data_Sample dataSample; 
    private Statistics stat; 

    public Controller(GUI view, Data_Importer dataImporter) {
        this.view = view;
        this.dataImporter = dataImporter;
        view.addImportListener(e -> importData());
        view.addExportListener(e -> exportData());
        view.addStatsListener(e -> stats());
        view.addExitListener(e -> System.exit(0));
    }

    public void importData() {
        String filePath = view.getFilePath();
        int sheetIndex = view.getSheetIndex();

        if (filePath.isEmpty() || sheetIndex < 0) {
            view.showError("Введите корректный путь и номер листа.");
            return;
        }

        if (!(new java.io.File(filePath)).exists()) {
            view.showError("Файл не найден.");
            return;
        }

        try {
            dataSample = dataImporter.importer(filePath, sheetIndex);
            Map<String, double[]> dataMap = dataSample.getDataMap();

            StringBuilder result = new StringBuilder("Импортированные данные:\n");
            dataMap.forEach((key, value) -> 
            result.append(key).append(" = ").append(java.util.Arrays.toString(value)).append("\n")
            );

            view.setResultText(result.toString());
        } catch (Exception ex) {
            view.showError("Ошибка при импорте: " + ex.getMessage());
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
        if (filePath.isEmpty()) {
            view.showError("Введите путь для сохранения файла.");
            return;
        }

        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                view.showError("Не удалось создать директорию: " + parentDir.getAbsolutePath());
                return;
            }
        }


        try {
            Data_Exporter.exporter(stat.getStatistics(), filePath);
            view.setResultText("Данные успешно экспортированы в файл: " + filePath);
        } catch (Exception e) {
            view.showError("Ошибка при экспорте: " + e.getMessage());
        }
    }
}
