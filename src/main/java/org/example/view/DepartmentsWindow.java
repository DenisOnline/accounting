package org.example.view;

import org.example.services.DepartmentService;

import javax.swing.*;
import java.sql.*;

public class DepartmentsWindow {
    private JFrame departmentsFrame;
    private final String URL = "jdbc:firebirdsql://localhost:3050/Bookkeeping";
    private final String USER_NAME = "sysdba";
    private final String PASSWORD = "sysdba";
    private final DepartmentService departmentService;

    public DepartmentsWindow(DepartmentService departmentService) {
        this.departmentService = departmentService;
        prepareDepartmentsFrame();
    }

    private int countRecords(ResultSet rs) throws SQLException {
        int count = 0;
        while (rs.next()) {
            count++;
        }
        return count;
    }

    private void prepareDepartmentsFrame() {
        departmentsFrame = new JFrame("Департаменты");
        departmentsFrame.setSize(600, 400);

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Departments");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            int rowCount =  departmentService.countRecords(resultSet); // Получаем количество строк в результате

            resultSet = statement.executeQuery("SELECT * FROM Departments"); // Новый запрос, чтобы вернуться к началу

            Object[][] data = new Object[rowCount][columnCount];

            int row = 0;
            while (resultSet.next()) {
                for (int col = 0; col < columnCount; col++) {
                    data[row][col] = resultSet.getString(col + 1);
                }
                row++;
            }

            String[] columnNames = {"id", "Название отдела", "Рабочий телефон", "Email", "id начальника"};

            JTable departmentsTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(departmentsTable);
            departmentsFrame.add(scrollPane);

            departmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            departmentsFrame.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}