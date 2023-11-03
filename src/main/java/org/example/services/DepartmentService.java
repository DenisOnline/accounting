package org.example.services;

import lombok.AllArgsConstructor;
import org.example.models.Department;
import org.example.repositories.DepartmentRepositories;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepositories departmentRepositories;

    public int addDepartmentToDatabase(String departmentName, String phoneNumber, String email) {
        return departmentRepositories.addDepartmentToDatabase(departmentName, phoneNumber, email);
    }


    public int countRecords(ResultSet resultSet) throws SQLException {
        return departmentRepositories.countRecords(resultSet);
    }

    public String[] getManagersFromDatabase() {
        return departmentRepositories.getManagersFromDatabase();
    }

    public int getManagerIdFromComboBox(JComboBox<String> managerList) {
        return departmentRepositories.getManagerIdFromComboBox(managerList);
    }

    public int saveDepartmentData(int departmentId, String name, String phone, String email, int managerId) {
        departmentRepositories.saveDepartmentData(departmentId, name, phone, email, managerId);
        return departmentId;
    }

    public Department findDepartmentById(int departmentId) {
        return departmentRepositories.findDepartmentById(departmentId);
    }
}