package com.bank.model;

 public class Employee {

    private Long empId;
    private String name;
    private String gmail;
    private String password;
    private Role role;

   
    public enum Role {
        MANAGER,
        DIRECTOR
    }

    public Employee() {}

    public Employee(Long empId, String name, String gmail, String password, Role role) {
        this.empId = empId;
        this.name = name;
        this.gmail = gmail;
        this.password = password;
        this.role = role;
    }

   
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", gmail='" + gmail + '\'' +
                ", role=" + role +
                '}';
    }
}
