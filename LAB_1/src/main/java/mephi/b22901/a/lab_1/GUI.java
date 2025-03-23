/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextField importfilePathField;
    private JTextField sheetIndexField;
    private JTextField exportfilePathField;
    private JButton importButton;
    private JButton exportButton;
    private JButton exitButton;
    private JButton statsButton;
    private JButton browseButton;
    private JButton browseButton2;
    private JTextArea resultArea;

    public GUI() {
        setTitle("Data Importer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 1, 5, 5)); // 6 строк, 1 столбец, отступ 5px

      
        JPanel importPanel = new JPanel(new BorderLayout());
        importPanel.add(new JLabel("Файл для импорта: "), BorderLayout.WEST);
        importfilePathField = new JTextField();
        importfilePathField.setEditable(false);
        importPanel.add(importfilePathField, BorderLayout.CENTER);
        browseButton = new JButton("Обзор...");
        importPanel.add(browseButton, BorderLayout.EAST);
        inputPanel.add(importPanel);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                importfilePathField.setText(fileChooser.getSelectedFile().getPath());
            }
        });

        
        JPanel sheetPanel = new JPanel(new BorderLayout());
        sheetPanel.add(new JLabel("Номер листа: "), BorderLayout.WEST);
        sheetIndexField = new JTextField();
        sheetPanel.add(sheetIndexField, BorderLayout.CENTER);
        inputPanel.add(sheetPanel);


        JPanel exportPanel = new JPanel(new BorderLayout());
        exportPanel.add(new JLabel("Файл для экспорта: "), BorderLayout.WEST);
        exportfilePathField = new JTextField();
        exportfilePathField.setEditable(false);
        exportPanel.add(exportfilePathField, BorderLayout.CENTER);
        browseButton2 = new JButton("Обзор...");
        exportPanel.add(browseButton2, BorderLayout.EAST);
        inputPanel.add(exportPanel);

        browseButton2.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fileChooser.getSelectedFile().getPath();
                if (!selectedFilePath.endsWith(".xlsx")) {
                    selectedFilePath += ".xlsx"; 
                }
        exportfilePathField.setText(selectedFilePath); 
    }
});

   
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        importButton = new JButton("Импорт");
        statsButton = new JButton("Статистика");
        exportButton = new JButton("Экспорт");
        exitButton = new JButton("Выход");

        buttonPanel.add(importButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(exitButton);
        inputPanel.add(buttonPanel);

     
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

       
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getImportFilePath() {
        return importfilePathField.getText();
    }

    public int getSheetIndex() {
        try {
            return Integer.parseInt(sheetIndexField.getText())-1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getExportFilePath() {
        String filePath = exportfilePathField.getText();
    return filePath;
}

    public void setResultText(String text) {
        resultArea.setText(text);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public void addImportListener(ActionListener listener) {
        importButton.addActionListener(listener);
    }

    public void addExportListener(ActionListener listener) {
        exportButton.addActionListener(listener);
    }

    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void addStatsListener(ActionListener listener) {
        statsButton.addActionListener(listener);
    }
}

