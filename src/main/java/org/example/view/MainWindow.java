package org.example.view;

import org.example.repositories.DepartmentRepositories;
import org.example.repositories.EmployeeRepositories;
import org.example.services.DepartmentService;
import org.example.services.EmployeeService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private JFrame mainFrame;

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public MainWindow(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        prepareMainFrame();
    }

    private void prepareMainFrame() {
        mainFrame = new JFrame("Главное окно");
        mainFrame.setSize(400, 450);
        mainFrame.setLayout(null);

        JLabel appIcon = new JLabel(new ImageIcon("appIcon.png"));
        appIcon.setBounds(150, 20, 100, 100);
        mainFrame.add(appIcon);

        JButton allEmployeesBtn = new JButton("Сотрудники");
        allEmployeesBtn.setBounds(100, 140, 200, 30);
        allEmployeesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AllEmployeesWindow(employeeService);
            }
        });
        mainFrame.add(allEmployeesBtn);

        JButton addEmployeeBtn = new JButton("Добавить сотрудника");
        addEmployeeBtn.setBounds(100, 180, 200, 30);
        addEmployeeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddEmployeeWindow(employeeService);
            }
        });
        mainFrame.add(addEmployeeBtn);

        JButton deleteEmployeeBtn = new JButton("Удалить сотрудника");
        deleteEmployeeBtn.setBounds(100, 220, 200, 30);
        deleteEmployeeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteEmployeeWindow(employeeService);
            }
        });
        mainFrame.add(deleteEmployeeBtn);

        JButton departmentsBtn = new JButton("Отделы");
        departmentsBtn.setBounds(100, 260, 200, 30);
        departmentsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DepartmentsWindow(departmentService);
            }
        });
        mainFrame.add(departmentsBtn);

        JButton addDepartmentBtn = new JButton("Добавить отдел");
        addDepartmentBtn.setBounds(100, 300, 200, 30);
        addDepartmentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddDepartmentWindow(departmentService);
            }
        });
        mainFrame.add(addDepartmentBtn);

        JButton editDepartmentBtn = new JButton("Редактировать отдел");
        editDepartmentBtn.setBounds(100, 340, 200, 30);
        editDepartmentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new EditDepartmentWindow(departmentService);
            }
        });
        mainFrame.add(editDepartmentBtn);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow(new EmployeeService(new EmployeeRepositories()), new DepartmentService(new DepartmentRepositories())));
    }
}