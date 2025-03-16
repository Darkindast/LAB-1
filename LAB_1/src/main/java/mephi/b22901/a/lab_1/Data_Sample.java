/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;

import java.util.HashMap;
import java.util.Map;

class Data_Sample {
    private Map<String, double[]> dataMap;

    public Data_Sample() {
        this.dataMap = new HashMap<>();
    }

    public void setDataMap(Map<String, double[]> dataMap) {
        this.dataMap = dataMap;
    }

    public Map<String, double[]> getDataMap() {
        return dataMap;
    }
}

