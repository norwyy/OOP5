package Enterprise;

import Enterprise.OrganizationGroup.AircraftFactory;
import Enterprise.OrganizationGroup.InsuranceCompany;
import Enterprise.OrganizationGroup.Organization;
import Enterprise.OrganizationGroup.ShipbuildingCompany;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private Enterprise enterprise = new Enterprise();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\nМеню управления организациями");
            System.out.println("1. Добавить организацию");
            System.out.println("2. Показать все организации");
            System.out.println("3. Редактировать организацию");
            System.out.println("4. Удалить организацию");
            System.out.println("5. Найти организацию");
            System.out.println("6. Выйти");
            System.out.print("Выберите пункт меню: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> addOrganization();
                case 2 -> displayOrganizations();
                case 3 -> editOrganization();
                case 4 -> removeOrganization();
                case 5 -> searchOrganization();
                case 6 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void addOrganization() {
        System.out.println("\nВыберите тип организации:");
        System.out.println("1. Судостроительная компания");
        System.out.println("2. Страховая компания");
        System.out.println("3. Авиазавод");
        System.out.print("Введите номер типа организации (1-3): ");

        int type = getIntInput();
        if (type < 1 || type > 3) {
            System.out.println("Неверный выбор. Попробуйте снова.");
            return;
        }


        String name;
        while (true) {
            System.out.print("Введите название организации: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty())
                break;
            System.out.println("Ошибка: название не может быть пустым.");
        }

        String location;
        while (true) {
            System.out.print("Введите местоположение: ");
            location = scanner.nextLine().trim();
            if (!location.isEmpty())
                break;
            System.out.println("Ошибка: местоположение не может быть пустым.");
        }

        System.out.print("Введите количество сотрудников: ");
        int employees = getIntInput();

        Organization org = null;
        switch (type) {
            case 1 -> {
                System.out.print("Введите количество построенных кораблей: ");
                int ships = getIntInput();
                org = new ShipbuildingCompany(name, location, employees, ships);
            }
            case 2 -> {
                System.out.print("Введите тип страхования: ");
                String insuranceType = scanner.nextLine().trim();
                org = new InsuranceCompany(name, location, employees, insuranceType);
            }
            case 3 -> {
                System.out.print("Введите количество произведённых самолётов: ");
                int aircraft = getIntInput();
                org = new AircraftFactory(name, location, employees, aircraft);
            }
        }

        enterprise.addOrganization(org);
        System.out.println("Организация успешно добавлена.");
    }

    private void displayOrganizations() {
        if (enterprise.getOrganizations().isEmpty())
            System.out.println("Список организаций пуст.");
        else
            enterprise.displayAllOrganizations();
    }

    private void editOrganization() {
        if (enterprise.getOrganizations().isEmpty()) {
            System.out.println("Список организаций пуст.");
            return;
        }

        System.out.print("\nВведите название организации для редактирования: ");
        String name = scanner.nextLine().trim();
        Organization org = enterprise.findOrganizationByName(name);

        if (org == null) {
            System.out.println("Организация с таким названием не найдена.");
            return;
        }

        System.out.println("Редактирование данных организации: " + org.getName());
        System.out.print("Новое название (Оставьте пустым, чтобы не изменять): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) org.setName(newName);

        System.out.print("Новое местоположение (Оставьте пустым, чтобы не изменять): ");
        String newLocation = scanner.nextLine().trim();
        if (!newLocation.isEmpty()) org.setLocation(newLocation);

        System.out.print("Новое количество сотрудников: ");
        org.setEmployees(getIntInput());

        if (org instanceof ShipbuildingCompany) {
            ShipbuildingCompany ship = (ShipbuildingCompany) org;
            System.out.print("Новое количество построенных кораблей: ");
            ship.setNumberOfShipsBuilt(getIntInput());
        } else if (org instanceof InsuranceCompany) {
            InsuranceCompany insurance = (InsuranceCompany) org;
            System.out.print("Новый тип страхования: ");
            insurance.setInsuranceType(scanner.nextLine().trim());
        } else if (org instanceof AircraftFactory) {
            AircraftFactory factory = (AircraftFactory) org;
            System.out.print("Новое количество произведённых самолётов: ");
            factory.setAircraftProduced(getIntInput());
        }

        System.out.println("Данные организации успешно обновлены.");
    }

    private void removeOrganization() {
        System.out.print("\nВведите название организации для удаления: ");
        String name = scanner.nextLine().trim();

        if (enterprise.removeOrganizationByName(name))
            System.out.println("Организация успешно удалена.");
        else
            System.out.println("Организация с таким названием не найдена.");
    }

    private void searchOrganization() {
        System.out.println("\nПоиск организации");
        System.out.println("Выберите критерий поиска:");
        System.out.println("1. По названию");
        System.out.println("2. По местоположению");
        System.out.println("3. По типу организации");

        int choice = getIntInput();
        String query = "";
        List<Organization> foundOrgs;

        switch (choice) {
            case 1:
                System.out.print("Введите название организации: ");
                query = scanner.nextLine().trim();
                foundOrgs = enterprise.findOrganizationsByName(query);
                break;
            case 2:
                System.out.print("Введите местоположение организации: ");
                query = scanner.nextLine().trim();
                foundOrgs = enterprise.findOrganizationsByLocation(query);
                break;
            case 3:
                System.out.println("\nВыберите тип организации:");
                System.out.println("1. Судостроительная компания");
                System.out.println("2. Страховая компания");
                System.out.println("3. Авиазавод");
                System.out.print("Введите номер типа организации (1-3): ");

                int type = getIntInput();
                if (type < 1 || type > 3) {
                    System.out.println("Неверный выбор. Попробуйте снова.");
                    return;
                }
                if (type == 1)
                    query = "ShipbuildingCompany";
                else if (type == 2)
                    query = "InsuranceCompany";
                else
                    query = "AircraftFactory";
                foundOrgs = enterprise.findOrganizationsByType(query);
                break;
            default:
                System.out.println("Неверный выбор. Возврат в главное меню.");
                return;
        }

        if (!foundOrgs.isEmpty()) {
            System.out.println("\nНайденные организации:");
            for (Organization org : foundOrgs)
                org.displayInfo();
        } else
            System.out.println("Организация не найдена.");
    }



    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ошибка: введите корректное число: ");
            }
        }
    }
}
