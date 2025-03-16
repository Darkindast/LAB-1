/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Statistics {
    public static Map<String, Map<String, Double>> calculateStatistics(Data_Sample dataSample) {
        Map<String, Map<String, Double>> statistics = new LinkedHashMap<>();
        try {
            Map<String, double[]> dataMap = dataSample.getDataMap();

            for (Map.Entry<String, double[]> entry : dataMap.entrySet()) {
                String key = entry.getKey();
                double[] data = entry.getValue();
                Map<String, Double> stats = new HashMap<>();

                if (data.length == 0) {
                    continue;
                }

                // 1. Среднее геометрическое
                stats.put("Среднее геометрическое", geometricMean(data));

                // 2. Среднее арифметическое
                stats.put("Среднее арифметическое", arithmeticMean(data));

                // 3. Оценка стандартного отклонения
                stats.put("Стандартное отклонение", standardDeviation(data));

                // 4. Размах выборки
                stats.put("Размах", range(data));

                // 5. Количество элементов
                stats.put("Количество элементов", (double) data.length);

                // 6. Коэффициент вариации
                stats.put("Коэффициент вариации", coefficientOfVariation(data));

                // 7. Доверительный интервал (95%)
                stats.put("Доверительный интервал", confidenceInterval(data));

                // 8. Дисперсия
                stats.put("Дисперсия", variance(data));

                // 9. Минимум и максимум
                stats.put("Минимум", Arrays.stream(data).min().orElse(Double.NaN));
                stats.put("Максимум", Arrays.stream(data).max().orElse(Double.NaN));

                statistics.put(key, stats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statistics;
    }

    // 1. Среднее геометрическое
    private static double geometricMean(double[] data) {
        double product = 1.0;
        for (double num : data) {
            if (num <= 0) return Double.NaN; // Нельзя считать геометрическое среднее для <= 0
            product *= num;
        }
        return Math.pow(product, 1.0 / data.length);
    }

    // 2. Среднее арифметическое
    private static double arithmeticMean(double[] data) {
        return Arrays.stream(data).average().orElse(Double.NaN);
    }

    // 3. Стандартное отклонение
    private static double standardDeviation(double[] data) {
        double mean = arithmeticMean(data);
        return Math.sqrt(Arrays.stream(data).map(x -> Math.pow(x - mean, 2)).sum() / (data.length - 1));
    }

    // 4. Размах выборки
    private static double range(double[] data) {
        return Arrays.stream(data).max().orElse(0) - Arrays.stream(data).min().orElse(0);
    }

    // 5. Коэффициент вариации
    private static double coefficientOfVariation(double[] data) {
        double mean = arithmeticMean(data);
        double stdDev = standardDeviation(data);
        return stdDev / mean;
    }

    // 6. Доверительный интервал (95%)
    private static double confidenceInterval(double[] data) {
        double mean = arithmeticMean(data);
        double stdDev = standardDeviation(data);
        double marginOfError = 1.96 * (stdDev / Math.sqrt(data.length));
        return marginOfError;
    }

    // 7. Дисперсия
    private static double variance(double[] data) {
        double mean = arithmeticMean(data);
        return Arrays.stream(data).map(x -> Math.pow(x - mean, 2)).sum() / (data.length - 1);
    }
}


