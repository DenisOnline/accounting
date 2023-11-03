package org.example.view;

import lombok.AllArgsConstructor;
import org.example.services.EmployeeService;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
public class DeleteEmployeeWindow {
    private JFrame deleteEmployeeFrame;
    private final String URL = "jdbc:firebirdsql://localhost:3050/Bookkeeping";
    private final String USER_NAME = "sysdba";
    private final String PASSWORD = "sysdba";

    private final EmployeeService employeeService;

    public DeleteEmployeeWindow(EmployeeService employeeService) {
        this.employeeService = employeeService;
        prepareDeleteEmployeeFrame();
    }

    private void prepareDeleteEmployeeFrame() {
        deleteEmployeeFrame = new JFrame("Удалить сотрудника");
        deleteEmployeeFrame.setSize(400, 200);
        deleteEmployeeFrame.setLayout(null);

        JLabel searchLabel = new JLabel("Поиск сотрудника по ID:");
        searchLabel.setBounds(50, 30, 150, 20);
        deleteEmployeeFrame.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(200, 30, 100, 20);
        deleteEmployeeFrame.add(searchField);

        JButton deleteButton = new JButton("Удалить");
        deleteButton.setBounds(150, 80, 100, 30);
        deleteButton.addActionListener(e -> {
            int employeeIdToDelete = Integer.parseInt(searchField.getText());
            int rowsAffected = employeeService.deleteEmployee(employeeIdToDelete);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(deleteEmployeeFrame, "Сотрудник успешно удален.", "Успех", JOptionPane.INFORMATION_MESSAGE);
                deleteEmployeeFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(deleteEmployeeFrame, "Сотрудника с таким ID нет.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteEmployeeFrame.add(deleteButton);

        deleteEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteEmployeeFrame.setVisible(true);
    }

    private void deleteEmployee(int employeeId) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String deleteQuery = "DELETE FROM Employees WHERE ID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, employeeId);
                int deletedRows = preparedStatement.executeUpdate();

                if (deletedRows > 0) {
                    JOptionPane.showMessageDialog(null, "Сотрудник с ID " + employeeId + " удалён.");
                } else {
                    JOptionPane.showMessageDialog(null, "Сотрудник с ID " + employeeId + " не найден.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}