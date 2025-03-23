/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;



import static java.lang.Math.sqrt;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;

import org.apache.commons.math3.stat.correlation.Covariance;
import java.util.LinkedHashMap;
import java.util.Map;

public class Statistics {

    private Map<String, Map<String, Double>> statistics;

    public Statistics(Data_Sample dataSample) {
        this.statistics = calculateStatistics(dataSample);
    }

    
    public Map<String, Map<String, Double>> getStatistics() {
        return statistics;
    }


    public Map<String, Map<String, Double>> calculateStatistics(Data_Sample dataSample) {
        statistics = new LinkedHashMap<>();
        try {
            Map<String, double[]> dataMap = dataSample.getDataMap();

           
            for (Map.Entry<String, double[]> entry : dataMap.entrySet()) {
                String columnName = entry.getKey();
                double[] data = entry.getValue();
                Map<String, Double> stats = new LinkedHashMap<>();


                double arithmeticMean = StatUtils.mean(data);
                double geometricMean = StatUtils.geometricMean(data);
                double variance = StatUtils.variance(data);
                double standardDeviation = sqrt(StatUtils.variance(data));
                double min = StatUtils.min(data);
                double max = StatUtils.max(data);
                double range = max - min;
                double count = data.length;
                double coefficientOfVariation = Double.NaN;
                if (arithmeticMean != 0) {
                    coefficientOfVariation = (standardDeviation / arithmeticMean) * 100;
                } else {
                    coefficientOfVariation = Double.NaN;
                }

                TDistribution tDist = new TDistribution(count-1);
                double tValue = tDist.inverseCumulativeProbability(0.975);
                double confidenceInterval = tValue * (standardDeviation / Math.sqrt(count));

                stats.put("Среднее геометрическое", geometricMean);
                stats.put("Среднее арифметическое", arithmeticMean);
                stats.put("Стандартное отклонение", standardDeviation);
                stats.put("Размах", range);
                stats.put("Количество элементов", count);
                stats.put("Коэффициент вариации", coefficientOfVariation);
                stats.put("Доверительный интервал", confidenceInterval);
                stats.put("Дисперсия", variance);
                stats.put("Минимум", min);
                stats.put("Максимум", max);

         
                statistics.put(columnName, stats);
            }


            double[][] dataMatrix = buildDataMatrix(dataMap);
            Covariance covariance = new Covariance(dataMatrix);
            double[][] covarianceMatrix = covariance.getCovarianceMatrix().getData();


            String[] columnNames = dataMap.keySet().toArray(new String[0]);

            for (int i = 0; i < columnNames.length; i++) {
                Map<String, Double> covariances = new LinkedHashMap<>();
                for (int j = 0; j < columnNames.length; j++) {
                    covariances.put(columnNames[j], covarianceMatrix[i][j]);
                }
                statistics.put("Ковариация " + columnNames[i], covariances);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return statistics;
    }


    private static double[][] buildDataMatrix(Map<String, double[]> dataMap) {
        int numColumns = dataMap.size();
        int numRows = dataMap.values().iterator().next().length;
        double[][] dataMatrix = new double[numRows][numColumns];

        int colIndex = 0;
        for (double[] columnData : dataMap.values()) {
            for (int row = 0; row < numRows; row++) {
                dataMatrix[row][colIndex] = columnData[row];
            }
            colIndex++;
        }
        return dataMatrix;
    }
}


