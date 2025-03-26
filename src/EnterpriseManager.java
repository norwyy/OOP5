import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Enterprise.Enterprise;
import Enterprise.OrganizationGroup.*;

public class EnterpriseManager extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private Enterprise enterprise;

    public EnterpriseManager() {
        enterprise = new Enterprise();

        setTitle("Управление организациями");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new Object[]{"Название", "Тип", "Местоположение", "Сотрудники"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton searchButton = new JButton("Поиск");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> deleteSelectedRow());
        searchButton.addActionListener(e -> showSearchDialog());
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField employeesField = new JTextField();
        String[] types = {"Судостроительная компания", "Страховая компания", "Авиазавод"};
        JComboBox<String> typeBox = new JComboBox<>(types);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Название:"));
        panel.add(nameField);
        panel.add(new JLabel("Тип:"));
        panel.add(typeBox);
        panel.add(new JLabel("Местоположение:"));
        panel.add(locationField);
        panel.add(new JLabel("Сотрудники:"));
        panel.add(employeesField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Добавить организацию",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Название не может быть пустым",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int employees;
            try {
                String employeesText = employeesField.getText().trim();
                employees = employeesText.isEmpty() ? 0 : Integer.parseInt(employeesText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Количество сотрудников должно быть числом",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Organization org;
            String type = (String)typeBox.getSelectedItem();

            switch(type) {
                case "Судостроительная компания":
                    org = new ShipbuildingCompany(name, locationField.getText(), employees, 0);
                    break;
                case "Страховая компания":
                    org = new InsuranceCompany(name, locationField.getText(), employees, "Не указан");
                    break;
                case "Авиазавод":
                    org = new AircraftFactory(name, locationField.getText(), employees, 0);
                    break;
                default:
                    return;
            }
            enterprise.addOrganization(org);
            updateTable();
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите строку для редактирования");
            return;
        }

        Organization org = enterprise.getOrganizations().get(selectedRow);
        JTextField nameField = new JTextField(org.getName());
        JTextField locationField = new JTextField(org.getLocation());
        JTextField employeesField = new JTextField(String.valueOf(org.getEmployees()));
        String[] types = {"Судостроительная компания", "Страховая компания", "Авиазавод"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        typeBox.setSelectedItem(getTypeString(org));

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Название:"));
        panel.add(nameField);
        panel.add(new JLabel("Тип:"));
        panel.add(typeBox);
        panel.add(new JLabel("Местоположение:"));
        panel.add(locationField);
        panel.add(new JLabel("Сотрудники:"));
        panel.add(employeesField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Редактировать организацию", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Название не может быть пустым",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int employees;
            try {
                String employeesText = employeesField.getText().trim();
                employees = employeesText.isEmpty() ? 0 : Integer.parseInt(employeesText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Количество сотрудников должно быть числом",
                        "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                return;
            }

            org.setName(name);
            org.setLocation(locationField.getText());
            org.setEmployees(employees);
            updateTable();
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            enterprise.getOrganizations().remove(selectedRow);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Выберите строку для удаления");
        }
    }

    private void showSearchDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField nameField = new JTextField();
        String[] types = {"Все", "Судостроительная компания", "Страховая компания", "Авиазавод"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        JTextField locationField = new JTextField();

        panel.add(new JLabel("Название:"));
        panel.add(nameField);
        panel.add(new JLabel("Тип:"));
        panel.add(typeBox);
        panel.add(new JLabel("Местоположение:"));
        panel.add(locationField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Поиск организаций", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            searchOrganizations(nameField.getText(), (String)typeBox.getSelectedItem(), locationField.getText());
        }
    }

    private void searchOrganizations(String name, String type, String location) {
        tableModel.setRowCount(0); // Clear table

        for (Organization org : enterprise.getOrganizations()) {
            boolean matches = name.isEmpty() || org.getName().contains(name);

            if (!type.equals("Все") && !getTypeString(org).equals(type)) {
                matches = false;
            }

            if (!location.isEmpty() && !org.getLocation().contains(location)) {
                matches = false;
            }

            if (matches) {
                tableModel.addRow(new Object[]{org.getName(), getTypeString(org),
                        org.getLocation(), org.getEmployees()});
            }
        }
    }

    private String getTypeString(Organization org) {
        if (org instanceof ShipbuildingCompany) return "Судостроительная компания";
        if (org instanceof InsuranceCompany) return "Страховая компания";
        if (org instanceof AircraftFactory) return "Авиазавод";
        return "";
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Organization org : enterprise.getOrganizations()) {
            tableModel.addRow(new Object[]{org.getName(), getTypeString(org),
                    org.getLocation(), org.getEmployees()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnterpriseManager().setVisible(true));
    }
}