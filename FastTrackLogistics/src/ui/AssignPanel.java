package ui;

import controller.AssignController;
import model.Driver;
import model.Shipment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssignPanel extends JPanel {
    private JPanel rootPanel;
    private JComboBox<Shipment> shipmentComboBox;
    private JComboBox<Driver> driverComboBox;
    private JButton assignButton;
    private AssignController assignController;

    public AssignPanel() {
        // Initialize controller
        assignController = new AssignController();

        // Set up the panel with GridLayout
        setLayout(new BorderLayout());

        // Create the main panel that will hold our content
        rootPanel = new JPanel(new GridLayout(4, 1, 10, 15));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Create and add the title
        JLabel titleLabel = new JLabel("Assign Driver to Shipment", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Create shipment selection panel
        JPanel shipmentPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JLabel shipmentLabel = new JLabel("Select Shipment:", JLabel.TRAILING);
        shipmentComboBox = new JComboBox<>();
        shipmentPanel.add(shipmentLabel);
        shipmentPanel.add(shipmentComboBox);

        // Create driver selection panel
        JPanel driverPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JLabel driverLabel = new JLabel("Select Driver:", JLabel.TRAILING);
        driverComboBox = new JComboBox<>();
        driverPanel.add(driverLabel);
        driverPanel.add(driverComboBox);

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        assignButton = new JButton("Assign");
        assignButton.setBackground(new Color(46, 139, 87)); // SeaGreen color
        assignButton.setForeground(Color.WHITE);
        assignButton.setFont(new Font("Arial", Font.BOLD, 14));
        assignButton.setFocusPainted(false);

        // Add empty panels on either side of the button to center it
        buttonPanel.add(new JPanel());
        buttonPanel.add(assignButton);
        buttonPanel.add(new JPanel());

        // Add components to the root panel
        rootPanel.add(titleLabel);
        rootPanel.add(shipmentPanel);
        rootPanel.add(driverPanel);
        rootPanel.add(buttonPanel);

        // Load data into combo boxes
        assignController.loadShipments(shipmentComboBox);
        assignController.loadDrivers(driverComboBox);

        // Set up assign action
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shipment selectedShipment = (Shipment) shipmentComboBox.getSelectedItem();
                Driver selectedDriver = (Driver) driverComboBox.getSelectedItem();
                assignController.assignDriverToShipment(selectedShipment, selectedDriver);
            }
        });

        // Add the root panel to this JPanel
        add(rootPanel, BorderLayout.CENTER);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}