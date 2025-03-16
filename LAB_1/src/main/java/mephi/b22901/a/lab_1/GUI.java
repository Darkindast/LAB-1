/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.lab_1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextField filePathField;
    private JTextField sheetIndexField;
    private JTextField exportfilePathField;
    private JButton importButton;
    private JButton exportButton;
    private JButton exitButton;
    private JButton statsButton;
    private JButton browseButton;
    private JTextArea resultArea;

    public GUI() {
        setTitle("Data Importer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Главная панель для ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 1, 5, 5)); // 6 строк, 1 столбец, отступ 5px

        // Панель для импорта (все в одной строке)
        JPanel importPanel = new JPanel(new BorderLayout());
        importPanel.add(new JLabel("Файл для импорта: "), BorderLayout.WEST);
        filePathField = new JTextField("D:\\Лаба_1 образцы данных (1).xlsx");
        importPanel.add(filePathField, BorderLayout.CENTER);
        browseButton = new JButton("Обзор...");
        importPanel.add(browseButton, BorderLayout.EAST);
        inputPanel.add(importPanel);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getPath());
            }
        });

        // Поле выбора листа (отдельная строка)
        JPanel sheetPanel = new JPanel(new BorderLayout());
        sheetPanel.add(new JLabel("Номер листа: "), BorderLayout.WEST);
        sheetIndexField = new JTextField();
        sheetPanel.add(sheetIndexField, BorderLayout.CENTER);
        inputPanel.add(sheetPanel);

        // Панель для экспорта (все в одной строке)
        JPanel exportPanel = new JPanel(new BorderLayout());
        exportPanel.add(new JLabel("Путь для экспорта: "), BorderLayout.WEST);
        exportfilePathField = new JTextField("D:\\output.xlsx");
        exportPanel.add(exportfilePathField, BorderLayout.CENTER);
        inputPanel.add(exportPanel);

        // Панель для кнопок (каждая кнопка с новой строки)
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

        // Поле вывода результатов
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Добавляем компоненты в окно
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getFilePath() {
        return filePathField.getText();
    }

    public int getSheetIndex() {
        try {
            return Integer.parseInt(sheetIndexField.getText());
        } catch (NumberFormatException e) {
            return -1; // Некорректный ввод
        }
    }

    public String getExportFilePath() {
        return exportfilePathField.getText();
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

