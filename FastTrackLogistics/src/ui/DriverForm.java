package ui;

import controller.DriverController;
import model.Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DriverForm extends JDialog {
    private JPanel rootPanel;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField scheduleField;
    private JTextField routesField;
    private JTextField deliveryHistoryField;
    private JButton saveButton;
    private JButton cancelButton;


    private DriverController controller = new DriverController();
    private Integer driverId = null; // Track driverId for update mode

    public DriverForm() {
        this(null); // Call overloaded constructor
    }

    public DriverForm(Driver existingDriver) {
        setTitle(existingDriver == null ? "Add Driver" : "Update Driver");
        setContentPane(rootPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        // If updating, populate fields and store ID
        if (existingDriver != null) {
            this.driverId = existingDriver.getDriverId();
            setName(existingDriver.getName());
            setPhone(existingDriver.getPhone());
            setSchedule(existingDriver.getSchedule());
            setRoutes(existingDriver.getRoutes());
            setDeliveryHistory(existingDriver.getDeliveryHistory());
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void onSave() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String schedule = scheduleField.getText().trim();
        String routes = routesField.getText().trim();
        String deliveryHistory = deliveryHistoryField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Phone are required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success;
        if (driverId == null) {
            // Add new driver
            success = controller.addDriver(name, phone, schedule, routes, deliveryHistory);
            if (success) {
                JOptionPane.showMessageDialog(this, "Driver added successfully.");
            }
        } else {
            // Update existing driver
            success = controller.updateDriver(driverId, name, phone, schedule, routes, deliveryHistory);
            if (success) {
                JOptionPane.showMessageDialog(this, "Driver updated successfully.");
            }
        }

        if (success) {
            dispose(); // Close dialog on success
        } else {
            JOptionPane.showMessageDialog(this, "Operation failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    // Getters to retrieve input data (optional)
    public String getName() {
        return nameField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getSchedule() {
        return scheduleField.getText();
    }

    public String getRoutes() {
        return routesField.getText();
    }

    public String getDeliveryHistory() {
        return deliveryHistoryField.getText();
    }

    // Setters for pre-filling form fields when updating
    public void setName(String name) {
        nameField.setText(name);
    }

    public void setPhone(String phone) {
        phoneField.setText(phone);
    }

    public void setSchedule(String schedule) {
        scheduleField.setText(schedule);
    }

    public void setRoutes(String routes) {
        routesField.setText(routes);
    }

    public void setDeliveryHistory(String history) {
        deliveryHistoryField.setText(history);
    }
}