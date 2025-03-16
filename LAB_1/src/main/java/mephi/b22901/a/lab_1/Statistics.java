/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;

import java.util.Map;
import java.util.Arrays;
import java.util.LinkedHashMap;


public class Statistics {

    private Map<String, Map<String, Double>> statistics;


    public Statistics(Data_Sample dataSample) {
        this.statistics = calculateStatistics(dataSample);
    }


    public Map<String, Map<String, Double>> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Map<String, Double>> statistics) {
        this.statistics = statistics;
    }


    public Map<String, Map<String, Double>> calculateStatistics(Data_Sample dataSample) {
        Map<String, Map<String, Double>> statistics = new LinkedHashMap<>();
        try {
            Map<String, double[]> dataMap = dataSample.getDataMap();

            for (Map.Entry<String, double[]> entry : dataMap.entrySet()) {
                String columnName = entry.getKey();
                double[] data = entry.getValue();
                Map<String, Double> stats = new LinkedHashMap<>();

                if (data.length == 0) {
                    continue; 
                }

           
                stats.put("Среднее геометрическое", calculateGeometricMean(data));

               
                stats.put("Среднее арифметическое", calculateArithmeticMean(data));

     
                stats.put("Стандартное отклонение", calculateStandardDeviation(data));

           
                stats.put("Размах", calculateRange(data));

               
                stats.put("Количество элементов", (double) data.length);

               
                stats.put("Коэффициент вариации", calculateCoefficientOfVariation(data));


                stats.put("Доверительный интервал", calculateConfidenceInterval(data));

             
                stats.put("Дисперсия", calculateVariance(data));

         
                stats.put("Минимум", calculateMin(data));
                stats.put("Максимум", calculateMax(data));

                statistics.put(columnName, stats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statistics;
    }

    private static double calculateGeometricMean(double[] data) {
        double product = 1.0;
        for (double num : data) {
            if (num <= 0) {
                return Double.NaN; 
            }
            product *= num;
        }
        return Math.pow(product, 1.0 / data.length);
    }


    private static double calculateArithmeticMean(double[] data) {
        double sum = Arrays.stream(data).sum();
        return sum / data.length;
    }

 
    private static double calculateStandardDeviation(double[] data) {
        double mean = calculateArithmeticMean(data);
        double sd = Arrays.stream(data)
                .map(x -> Math.pow(x - mean, 2))
                .sum();
        return Math.sqrt(sd / (data.length - 1));
    }

 
    private static double calculateRange(double[] data) {
        return calculateMax(data) - calculateMin(data);
    }

 
    private static double calculateCoefficientOfVariation(double[] data) {
        double mean = calculateArithmeticMean(data);
        double stdDev = calculateStandardDeviation(data);
        return (stdDev / mean) * 100; 
    }

 
    private static double calculateConfidenceInterval(double[] data) {
        double mean = calculateArithmeticMean(data);
        double stdDev = calculateStandardDeviation(data);
        double marginOfError = 1.96 * (stdDev / Math.sqrt(data.length));
        return marginOfError;
    }


    private static double calculateVariance(double[] data) {
        double mean = calculateArithmeticMean(data);
        double var = Arrays.stream(data)
                .map(x -> Math.pow(x - mean, 2))
                .sum();
        return var / (data.length - 1);
    }


    private static double calculateMin(double[] data) {
        return Arrays.stream(data).min().orElse(Double.NaN);
    }


    private static double calculateMax(double[] data) {
        return Arrays.stream(data).max().orElse(Double.NaN);
    }
}


