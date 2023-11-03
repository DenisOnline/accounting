package org.example.view;

import org.example.services.EmployeeService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class AddEmployeeWindow {
    private JFrame addEmployeeFrame;
    private final EmployeeService employeeService;
    public AddEmployeeWindow(EmployeeService employeeService) {
        this.employeeService = employeeService;
        prepareAddEmployeeFrame();
    }

    private void prepareAddEmployeeFrame() {

        addEmployeeFrame = new JFrame("Добавить сотрудника");
        addEmployeeFrame.setSize(400, 300);
        addEmployeeFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Имя сотрудника:");
        nameLabel.setBounds(50, 50, 120, 20);
        addEmployeeFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(180, 50, 150, 20);
        addEmployeeFrame.add(nameField);

        JLabel positionLabel = new JLabel("Должность:");
        positionLabel.setBounds(50, 80, 120, 20);
        addEmployeeFrame.add(positionLabel);

        JTextField positionField = new JTextField();
        positionField.setBounds(180, 80, 150, 20);
        addEmployeeFrame.add(positionField);

        JLabel salaryLabel = new JLabel("Заработная плата:");
        salaryLabel.setBounds(50, 110, 120, 20);
        addEmployeeFrame.add(salaryLabel);

        JTextField salaryField = new JTextField();
        salaryField.setBounds(180, 110, 150, 20);
        addEmployeeFrame.add(salaryField);

        JLabel departmentLabel = new JLabel("Отдел:");
        departmentLabel.setBounds(50, 140, 120, 20);
        addEmployeeFrame.add(departmentLabel);

        String[] departments = employeeService.getDepartmentsFromDatabase();
        JComboBox<String> departmentList = new JComboBox<>(departments);
        departmentList.setBounds(180, 140, 150, 20);
        addEmployeeFrame.add(departmentList);

        JButton addButton = new JButton("Добавить");
        addButton.setBounds(150, 200, 100, 30);
        addButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText();
            String position = positionField.getText();
            BigDecimal salary = new BigDecimal(salaryField.getText());
            int departmentId = employeeService.getSelectedDepartmentId(departmentList);

            int rowsAffected = employeeService.addEmployeeToDatabase(name, position, salary, departmentId);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(addEmployeeFrame, "Сотрудник успешно добавлен в базу данных.", "Успех", JOptionPane.INFORMATION_MESSAGE);
                addEmployeeFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(addEmployeeFrame, "Не удалось добавить сотрудника.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        addEmployeeFrame.add(addButton);

        addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addEmployeeFrame.setVisible(true);
    }
}