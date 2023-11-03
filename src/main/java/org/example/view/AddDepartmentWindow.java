package org.example.view;

import org.example.services.DepartmentService;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDepartmentWindow {
    private JFrame addDepartmentFrame;
    private final DepartmentService departmentService;

    public AddDepartmentWindow(DepartmentService departmentService) {
        this.departmentService = departmentService;
        prepareAddDepartmentFrame();
    }

    private void prepareAddDepartmentFrame() {
        addDepartmentFrame = new JFrame("Добавить отдел");
        addDepartmentFrame.setSize(400, 300);
        addDepartmentFrame.setLayout(null);

        JLabel departmentNameLabel = new JLabel("Название отдела:");
        departmentNameLabel.setBounds(50, 30, 120, 20);
        addDepartmentFrame.add(departmentNameLabel);

        JTextField departmentNameField = new JTextField();
        departmentNameField.setBounds(200, 30, 150, 20);
        addDepartmentFrame.add(departmentNameField);

        JLabel phoneNumberLabel = new JLabel("Рабочий телефон:");
        phoneNumberLabel.setBounds(50, 70, 120, 20);
        addDepartmentFrame.add(phoneNumberLabel);

        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setBounds(200, 70, 150, 20);
        addDepartmentFrame.add(phoneNumberField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 120, 20);
        addDepartmentFrame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(200, 110, 150, 20);
        addDepartmentFrame.add(emailField);

        JButton addButton = new JButton("Добавить");
        addButton.setBounds(150, 200, 100, 30);

        addButton.addActionListener(e -> {
            String departmentName = departmentNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();

            int rowsAffected = departmentService.addDepartmentToDatabase(departmentName, phoneNumber, email);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(addDepartmentFrame, "Отдел успешно добавлен в базу данных.", "Успех", JOptionPane.INFORMATION_MESSAGE);
                addDepartmentFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(addDepartmentFrame, "Не удалось добавить отдел.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        addDepartmentFrame.add(addButton);
        addDepartmentFrame.add(addButton);

        addDepartmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDepartmentFrame.setVisible(true);
    }
}