package org.example.view;

import org.example.models.Department;
import org.example.services.DepartmentService;

import javax.swing.*;
import java.awt.*;

public class EditDepartmentWindow {
    private JFrame editDepartmentFrame;
    private final DepartmentService departmentService;

    public EditDepartmentWindow(DepartmentService departmentService) {
        this.departmentService = departmentService;
        prepareEditDepartmentFrame();
    }

    private void prepareEditDepartmentFrame() {
        editDepartmentFrame = new JFrame("Редактировать отдел");
        editDepartmentFrame.setSize(400, 300);
        editDepartmentFrame.setLayout(null);

        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
        idPanel.setBounds(50, 20, 300, 30);
        editDepartmentFrame.add(idPanel);

        JLabel idLabel = new JLabel("ID отдела: ");
        idPanel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(150, 20));
        idPanel.add(idField);

        JButton searchButton = new JButton("Найти");

        idPanel.add(searchButton);

        JLabel nameLabel = new JLabel("Название отдела:");
        nameLabel.setBounds(50, 50, 120, 20);
        editDepartmentFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(180, 50, 150, 20);
        editDepartmentFrame.add(nameField);

        JLabel phoneLabel = new JLabel("Рабочий телефон:");
        phoneLabel.setBounds(50, 80, 120, 20);
        editDepartmentFrame.add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setBounds(180, 80, 150, 20);
        editDepartmentFrame.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 120, 20);
        editDepartmentFrame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(180, 110, 150, 20);
        editDepartmentFrame.add(emailField);

        JLabel managerLabel = new JLabel("Начальник отдела:");
        managerLabel.setBounds(50, 140, 120, 20);
        editDepartmentFrame.add(managerLabel);

        String[] managers = departmentService.getManagersFromDatabase();
        JComboBox<String> managerList = new JComboBox<>(managers);
        managerList.setBounds(180, 140, 150, 20);
        editDepartmentFrame.add(managerList);

        JButton saveButton = new JButton("Сохранить");
        saveButton.setBounds(150, 200, 100, 30);
        saveButton.addActionListener(e -> {
            int departmentId = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            int managerId = departmentService.getManagerIdFromComboBox(managerList);

            int rowsAffected = departmentService.saveDepartmentData(departmentId, name, phone, email, managerId);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(editDepartmentFrame, "Данные отдела обновлены", "Успех", JOptionPane.INFORMATION_MESSAGE);
                editDepartmentFrame.dispose();
            } else {
                System.out.println("Не удалось обновить данные отдела.");
                JOptionPane.showMessageDialog(editDepartmentFrame, "Не удалось обновить данные отдела.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> {
            int departmentId = Integer.parseInt(idField.getText());
            Department department = departmentService.findDepartmentById(departmentId);

            if (department != null) {
                nameField.setText(department.getName());
                phoneField.setText(department.getPhone());
                emailField.setText(department.getEmail());
            }
        });
        editDepartmentFrame.add(saveButton);

        editDepartmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editDepartmentFrame.setVisible(true);
    }
}