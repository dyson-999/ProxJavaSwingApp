package ui;

import controller.AssignController;
import model.Driver;
import model.Shipment;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AssignPanel extends JPanel {
    private JPanel rootPanel;
    private JTable shipmentTable;
    private JComboBox<Driver> driverComboBox;
    private JButton assignButton;

    private AssignController assignController;
    private Shipment selectedShipment; // Store selected shipment

    public AssignPanel() {
        assignController = new AssignController();
        setLayout(new BorderLayout());

        rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // === Title ===
        JLabel titleLabel = new JLabel("Assign Driver to Shipment", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rootPanel.add(titleLabel, BorderLayout.NORTH);

        // === Shipment Table ===
        shipmentTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(shipmentTable);
        rootPanel.add(tableScrollPane, BorderLayout.CENTER);
        populateShipmentTable();

        // === Bottom Panel for Driver and Button ===
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        bottomPanel.add(new JLabel("Select Driver:"));
        driverComboBox = new JComboBox<>();
        assignController.loadDrivers(driverComboBox);
        bottomPanel.add(driverComboBox);

        bottomPanel.add(new JLabel(""));
        assignButton = new JButton("Assign Driver");
        assignButton.setBackground(new Color(46, 139, 87));
        assignButton.setForeground(Color.WHITE);
        assignButton.setFont(new Font("Arial", Font.BOLD, 14));
        assignButton.setFocusPainted(false);
        bottomPanel.add(assignButton);

        rootPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rootPanel);

        // === Selection Listener ===
        shipmentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = shipmentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int shipmentId = (int) shipmentTable.getValueAt(selectedRow, 0);
                    String sender = (String) shipmentTable.getValueAt(selectedRow, 1);
                    String receiver = (String) shipmentTable.getValueAt(selectedRow, 2);
                    String status = (String) shipmentTable.getValueAt(selectedRow, 3);
                    selectedShipment = new Shipment(shipmentId, sender, receiver, status);
                }
            }
        });

        // === Assign Button Action ===
        assignButton.addActionListener((ActionEvent e) -> {
            Driver selectedDriver = (Driver) driverComboBox.getSelectedItem();
            if (selectedShipment != null && selectedDriver != null) {
                assignController.assignDriverToShipment(selectedShipment, selectedDriver);
                JOptionPane.showMessageDialog(rootPanel, "Driver assigned successfully!");
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Please select a shipment and a driver.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void populateShipmentTable() {
        List<Shipment> shipments = assignController.getShipments();
        String[] columnNames = {"ID", "Sender", "Receiver", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Shipment s : shipments) {
            model.addRow(new Object[]{s.getShipmentId(), s.getSenderName(), s.getReceiverName(), s.getStatus()});
        }

        shipmentTable.setModel(model);
        shipmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
