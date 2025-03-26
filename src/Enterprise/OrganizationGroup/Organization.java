package Enterprise.OrganizationGroup;

public abstract class Organization {
    private String name;
    private String location;
    private int employees;

    public Organization(String name, String location, int employees) {
        this.name = name;
        this.location = location;
        setEmployees(employees);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        if (employees >= 0) {
            this.employees = employees;
        } else {
            System.out.println("Количество сотрудников не может быть отрицательным.");
        }
    }

    public abstract void displayInfo();
}