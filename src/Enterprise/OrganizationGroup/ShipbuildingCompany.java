package Enterprise.OrganizationGroup;

public class ShipbuildingCompany extends Organization {
    private int numberOfShipsBuilt;

    public ShipbuildingCompany(String name, String location, int employees, int numberOfShipsBuilt) {
        super(name, location, employees);
        setNumberOfShipsBuilt(numberOfShipsBuilt);
    }

    public int getNumberOfShipsBuilt() {
        return numberOfShipsBuilt;
    }

    public void setNumberOfShipsBuilt(int numberOfShipsBuilt) {
        if (numberOfShipsBuilt >= 0) {
            this.numberOfShipsBuilt = numberOfShipsBuilt;
        } else {
            System.out.println("Число кораблей не может быть отрицательным.");
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Судостроительная компания: " + getName() + ", Местоположение: " + getLocation() +
                ", Сотрудников: " + getEmployees() + ", Построено кораблей: " + numberOfShipsBuilt);
    }

}

