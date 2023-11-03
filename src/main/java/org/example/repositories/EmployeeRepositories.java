package org.example.repositories;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositories {

    private final String URL = "jdbc:firebirdsql://localhost:3050/Bookkeeping";
    private final String USER_NAME = "sysdba";
    private final String PASSWORD = "sysdba";

    public String[] getDepartmentsFromDatabase() {
        List<String> departmentNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "SELECT NAME FROM Departments";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String departmentName = resultSet.getString("NAME");
                    departmentNames.add(departmentName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return departmentNames.toArray(new String[0]);
    }

    public int addEmployeeToDatabase(String name, String position, BigDecimal salary, int departmentId) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "INSERT INTO EMPLOYEES (NAME, POST, SALARY, DEPARTMENT_ID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, position);
                preparedStatement.setBigDecimal(3, salary);
                preparedStatement.setInt(4, departmentId);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSelectedDepartmentId(JComboBox<String> departmentList) {
        int selectedDepartmentId = -1;
        String selectedDepartmentName = (String) departmentList.getSelectedItem();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "SELECT ID FROM DEPARTMENTS WHERE NAME = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, selectedDepartmentName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    selectedDepartmentId = resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return selectedDepartmentId;
    }

    public int deleteEmployee(int employeeId) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String deleteQuery = "DELETE FROM Employees WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, employeeId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countRecords(ResultSet resultSet) throws SQLException {
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    public String[] getColumnNames(Connection connection, int columnCount) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Employees");
        ResultSetMetaData metaData = resultSet.getMetaData();

        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }
        return columnNames;
    }

    public boolean isEmployeeManager(int employeeId) {
        boolean isManager = false;
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "SELECT ID FROM DEPARTMENTS WHERE MANAGER_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, employeeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        isManager = true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isManager;
    }

    public void updateManagerIdToNull(int employeeId) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "UPDATE DEPARTMENTS SET MANAGER_ID = NULL WHERE MANAGER_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, employeeId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Object[][] getData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Employees");
        int rowCount = countRecords(resultSet);

        resultSet = statement.executeQuery("SELECT * FROM Employees");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        Object[][] data = new Object[rowCount][columnCount];

        int row = 0;
        while (resultSet.next()) {
            for (int col = 0; col < columnCount; col++) {
                data[row][col] = resultSet.getString(col + 1);
            }
            row++;
        }

        return data;
    }
}