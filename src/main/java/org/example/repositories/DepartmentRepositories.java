package org.example.repositories;

import org.example.models.Department;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositories {

    String URL = "jdbc:firebirdsql://localhost:3050/Bookkeeping";
    String USER_NAME = "sysdba";
    String PASSWORD = "sysdba";

    public int countRecords(ResultSet rs) throws SQLException {
        int count = 0;
        while (rs.next()) {
            count++;
        }
        return count;
    }

    public String[] getManagersFromDatabase() {
        List<String> managerNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "SELECT NAME FROM Employees";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String managerName = resultSet.getString("NAME");
                    managerNames.add(managerName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        managerNames.add("Нет");
        return managerNames.toArray(new String[0]);
    }

    public Department findDepartmentById(int departmentId) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "SELECT NAME, PHONE, EMAIL FROM DEPARTMENTS WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, departmentId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("NAME");
                    String phone = resultSet.getString("PHONE");
                    String email = resultSet.getString("EMAIL");
                    return new Department(name, phone, email,departmentId);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getManagerIdFromComboBox(JComboBox<String> managerList) {
        int managerId = -1; // значение по умолчанию для случая, если не будет найдено совпадение
        String managerName = (String) managerList.getSelectedItem();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "SELECT ID FROM Employees WHERE NAME = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, managerName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    managerId = resultSet.getInt("ID");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return managerId;
    }

    public int saveDepartmentData(int departmentId, String name, String phone, String email, int managerId) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String query = "UPDATE DEPARTMENTS SET \"NAME\" = ?, PHONE = ?, EMAIL = ?, MANAGER_ID = ? WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, email);
                if (managerId != -1) {
                    preparedStatement.setInt(4, managerId);
                } else {
                    preparedStatement.setNull(4, java.sql.Types.INTEGER);
                }
                preparedStatement.setInt(5, departmentId);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return departmentId;
    }

    public int addDepartmentToDatabase(String departmentName, String phoneNumber, String email) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String sql = "INSERT INTO Departments (NAME, PHONE, EMAIL) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, departmentName);
                preparedStatement.setString(2, phoneNumber);
                preparedStatement.setString(3, email);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}