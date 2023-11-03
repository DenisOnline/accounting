package org.example.services;

import lombok.AllArgsConstructor;
import org.example.repositories.EmployeeRepositories;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepositories employeeRepositories;

    public String[] getDepartmentsFromDatabase() {
        return employeeRepositories.getDepartmentsFromDatabase();
    }

    public int getSelectedDepartmentId(JComboBox<String> departmentList) {
        return employeeRepositories.getSelectedDepartmentId(departmentList);
    }

    public int addEmployeeToDatabase(String name, String position, BigDecimal salary, int departmentId) {
        employeeRepositories.addEmployeeToDatabase(name, position, salary, departmentId);
        return departmentId;
    }

    public int deleteEmployee(int employeeId) {
        boolean isManager = employeeRepositories.isEmployeeManager(employeeId);

        if (isManager) {
            employeeRepositories.updateManagerIdToNull(employeeId);
        }
        int deleteEmployee = employeeRepositories.deleteEmployee(employeeId);
        if (deleteEmployee > 0) {
            return deleteEmployee;
        } else {
            return -1;
        }
    }

    public Object[][] getData(Connection connection) throws SQLException {
        return employeeRepositories.getData(connection);
    }

    public int countRecords(ResultSet resultSet) throws SQLException {
        return employeeRepositories.countRecords(resultSet);
    }
}