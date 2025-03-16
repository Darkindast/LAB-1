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

           
                stats.put("Среднее геометрическое", Geometric_Mean(data));

               
                stats.put("Среднее арифметическое", Arithmetic_Mean(data));

     
                stats.put("Стандартное отклонение", Standard_Deviation(data));

           
                stats.put("Размах", Range(data));

               
                stats.put("Количество элементов", (double) data.length);

               
                stats.put("Коэффициент вариации", Coefficient_Of_Variation(data));


                stats.put("Доверительный интервал", Confidence_Interval(data));

             
                stats.put("Дисперсия", calculateVariance(data));

         
                stats.put("Минимум", Min(data));
                stats.put("Максимум", Max(data));

                statistics.put(columnName, stats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statistics;
    }

    private static double Geometric_Mean(double[] data) {
        double product = 1.0;
        for (double num : data) {
            if (num <= 0) {
                return Double.NaN; 
            }
            product *= num;
        }
        return Math.pow(product, 1.0 / data.length);
    }


    private static double Arithmetic_Mean(double[] data) {
        double sum = Arrays.stream(data).sum();
        return sum / data.length;
    }

 
    private static double Standard_Deviation(double[] data) {
        double mean = Arithmetic_Mean(data);
        double sd = Arrays.stream(data)
                .map(x -> Math.pow(x - mean, 2))
                .sum();
        return Math.sqrt(sd / (data.length - 1));
    }

 
    private static double Range(double[] data) {
        return Max(data) - Min(data);
    }

 
    private static double Coefficient_Of_Variation(double[] data) {
        double mean = Arithmetic_Mean(data);
        double stdDev = Standard_Deviation(data);
        return (stdDev / mean) * 100; 
    }

 
    private static double Confidence_Interval(double[] data) {
        double mean = Arithmetic_Mean(data);
        double stdDev = Standard_Deviation(data);
        double marginOfError = 1.96 * (stdDev / Math.sqrt(data.length));
        return marginOfError;
    }


    private static double calculateVariance(double[] data) {
        double mean = Arithmetic_Mean(data);
        double var = Arrays.stream(data).map(x -> Math.pow(x - mean, 2)).sum();
        return var / (data.length - 1);
    }


    private static double Min(double[] data) {
        return Arrays.stream(data).min().orElse(Double.NaN);
    }


    private static double Max(double[] data) {
        return Arrays.stream(data).max().orElse(Double.NaN);
    }
}


