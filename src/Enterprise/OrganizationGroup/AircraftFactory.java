package Enterprise.OrganizationGroup;

public class AircraftFactory extends Organization {
    private int aircraftProduced;

    public AircraftFactory(String name, String location, int employees, int aircraftProduced) {
        super(name, location, employees);
        setAircraftProduced(aircraftProduced);
    }

    public int getAircraftProduced() {
        return aircraftProduced;
    }

    public void setAircraftProduced(int aircraftProduced) {
        if (aircraftProduced >= 0) {
            this.aircraftProduced = aircraftProduced;
        } else {
            System.out.println("Число произведённых самолётов не может быть отрицательным.");
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Авиазавод: " + getName() + ", Местоположение: " + getLocation() +
                ", Сотрудников: " + getEmployees() + ", Произведено самолётов: " + aircraftProduced);
    }
}
