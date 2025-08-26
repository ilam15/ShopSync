package com.employee;

import java.util.*;

public abstract class Employee {
    private int employeeId;
    private String name;
    private String role;
    private double salary;
    private Set<String> tasks = new LinkedHashSet<>();
    private boolean loggedIn = false;

    public Employee(int employeeId, String name, String role, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    public boolean login(String username, String password) {
        // Dummy auth for demo
        this.loggedIn = username != null && password != null && !username.isBlank() && !password.isBlank();
        return loggedIn;
    }

    public void logout() { this.loggedIn = false; }

    public List<String> viewTasks() { return new ArrayList<>(tasks); }

    public void addTask(String task) { tasks.add(task); }

    public void updateSalary(double newSalary) { if (newSalary > 0) this.salary = newSalary; }

    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public double getSalary() { return salary; }
    public boolean isLoggedIn() { return loggedIn; }
}
