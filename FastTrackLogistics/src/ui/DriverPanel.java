package ui;

import controller.DriverController;
import model.Driver;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DriverPanel extends JPanel {
    private JTable driverTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel rootPanel;

    private DriverController driverController;
    private DefaultTableModel tableModel;

    public DriverPanel() {
        driverController = new DriverController();

        // Setup table model and columns
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Phone", "Schedule", "Routes", "Delivery History"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // disable editing cells directly
            }
        };
        driverTable.setModel(tableModel);
        driverTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadDriverData();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddDriver();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateDriver();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteDriver();
            }
        });
    }

    private void loadDriverData() {
        tableModel.setRowCount(0); // clear table
        List<Driver> drivers = driverController.getAllDrivers();
        for (Driver d : drivers) {
            tableModel.addRow(new Object[]{
                    d.getDriverId(),
                    d.getName(),
                    d.getPhone(),
                    d.getSchedule(),
                    d.getRoutes(),
                    d.getDeliveryHistory()
            });
        }
    }

    private void onAddDriver() {
        DriverForm form = new DriverForm();
        form.setLocationRelativeTo(this);
        form.setVisible(true);

        // After dialog closes, reload table (you can expand to save driver)
        loadDriverData();
    }

    private void onUpdateDriver() {
        int selectedRow = driverTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a driver to update.");
            return;
        }

        int driverId = (int) tableModel.getValueAt(selectedRow, 0);
        Driver existingDriver = driverController.getDriverById(driverId);

        if (existingDriver == null) {
            JOptionPane.showMessageDialog(this, "Selected driver not found.");
            return;
        }

        // Open form in update mode
        DriverForm form = new DriverForm(existingDriver);
        form.setLocationRelativeTo(this);
        form.setVisible(true);

        // Reload after update
        loadDriverData();
    }


//    private void onUpdateDriver() {
//        int selectedRow = driverTable.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Please select a driver to update.");
//            return;
//        }
//        int driverId = (int) tableModel.getValueAt(selectedRow, 0);
//
//        // Load existing driver data from controller
//        Driver existingDriver = driverController.getDriverById(driverId);
//        if (existingDriver == null) {
//            JOptionPane.showMessageDialog(this, "Selected driver not found.");
//            return;
//        }
//
//        DriverForm form = new DriverForm();
//
//        // Pre-fill form fields
//        form.getRootPanel().setVisible(true);
//        // Assuming you add setter methods or directly set fields here if you expose them (simplify for now)
//        // Example: form.setName(existingDriver.getName());
//
//        form.setLocationRelativeTo(this);
//        form.setVisible(true);
//
//        // After dialog closes, reload table (expand saving changes logic)
//        loadDriverData();
//    }

    private void onDeleteDriver() {
        int selectedRow = driverTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a driver to delete.");
            return;
        }
        int driverId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this driver?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = driverController.deleteDriver(driverId);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Driver deleted successfully.");
                loadDriverData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete driver.");
            }
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}