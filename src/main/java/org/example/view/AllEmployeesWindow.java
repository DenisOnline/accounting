package org.example.view;

import org.example.services.EmployeeService;

import javax.swing.*;
import java.sql.*;

public class AllEmployeesWindow {

    private JFrame allEmployeesFrame;
    private final String URL = "jdbc:firebirdsql://localhost:3050/Bookkeeping";
    private final String USER_NAME = "sysdba";
    private final String PASSWORD = "sysdba";
    private final EmployeeService employeeService;

    public AllEmployeesWindow(EmployeeService employeeService) {
        this.employeeService = employeeService;
        prepareAllEmployeesFrame();
    }

    private void prepareAllEmployeesFrame() {
        allEmployeesFrame = new JFrame("Все сотрудники");
        allEmployeesFrame.setSize(1200, 400);

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            Object[][] data = employeeService.getData(connection);
            String[] columnNames = {"id", "Имя сотрудника", "Должность", "Заработная плата", "id отдела"};

            JTable employeesTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(employeesTable);
            allEmployeesFrame.add(scrollPane);

            allEmployeesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            allEmployeesFrame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}