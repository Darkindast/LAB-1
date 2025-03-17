/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mephi.b22901.a.lab_1;

import javax.swing.SwingUtilities;

public class LAB_1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI view = new GUI();
            Data_Importer dataImporter = new Data_Importer();
            Data_Exporter dataExporter = new Data_Exporter();
            new Controller(view, dataImporter,dataExporter);
            view.setVisible(true);
        });
    }
}