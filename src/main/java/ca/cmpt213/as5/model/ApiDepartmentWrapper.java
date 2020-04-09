package ca.cmpt213.as5.model;

public class ApiDepartmentWrapper {
    private String department;
    private int id;


    public ApiDepartmentWrapper(String department, int id) {
        this.department = department;
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
