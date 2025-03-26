package Enterprise.OrganizationGroup;

public class InsuranceCompany extends Organization {
    private String insuranceType;

    public InsuranceCompany(String name, String location, int employees, String insuranceType) {
        super(name, location, employees);
        this.insuranceType = insuranceType;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    @Override
    public void displayInfo() {
        System.out.println("Страховая компания: " + getName() + ", Местоположение: " + getLocation() +
                ", Сотрудников: " + getEmployees() + ", Тип страхования: " + insuranceType);
    }
}
