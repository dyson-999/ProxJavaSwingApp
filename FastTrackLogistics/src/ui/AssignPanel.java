package ui;

import controller.AssignController;
import model.Driver;
import model.Shipment;
import model.ShipmentAssignmentInfo;

import javax.swing.*;
import javax.swing.DefaultCellEditor;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AssignPanel extends JPanel {
    private JPanel rootPanel;
    private JTable shipmentTable;
    private JComboBox<Driver> driverComboBox;
    private JButton assignButton;
    private JButton updateStatusButton;

    private AssignController assignController;
    private Shipment selectedShipment;

    private DefaultTableModel model;

    public AssignPanel() {
        assignController = new AssignController();
        setLayout(new BorderLayout());

        rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Assign Driver to Shipment", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rootPanel.add(titleLabel, BorderLayout.NORTH);

        shipmentTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(shipmentTable);
        rootPanel.add(tableScrollPane, BorderLayout.CENTER);
        populateShipmentTable();

        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        bottomPanel.add(new JLabel("Select Driver:"));
        driverComboBox = new JComboBox<>();
        assignController.loadDrivers(driverComboBox); // Initial load
        bottomPanel.add(driverComboBox);

        bottomPanel.add(new JLabel(""));
        assignButton = new JButton("Assign / Update Driver");
        assignButton.setBackground(new Color(46, 139, 87));
        assignButton.setForeground(Color.WHITE);
        assignButton.setFont(new Font("Arial", Font.BOLD, 14));
        assignButton.setFocusPainted(false);
        bottomPanel.add(assignButton);

        bottomPanel.add(new JLabel(""));
        updateStatusButton = new JButton("Update Status");
        updateStatusButton.setBackground(new Color(70, 130, 180));
        updateStatusButton.setForeground(Color.WHITE);
        updateStatusButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateStatusButton.setFocusPainted(false);
        bottomPanel.add(updateStatusButton);

        rootPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rootPanel);

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

        assignButton.addActionListener((ActionEvent e) -> {
            Driver selectedDriver = (Driver) driverComboBox.getSelectedItem();
            if (selectedShipment != null && selectedDriver != null) {
                boolean success = assignController.assignOrUpdateDriverAssignment(selectedShipment, selectedDriver);
                if (success) {
                    populateShipmentTable();
                }
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Please select a shipment and a driver.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateStatusButton.addActionListener((ActionEvent e) -> {
            int selectedRow = shipmentTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(rootPanel, "Please select a shipment row to update status.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int shipmentId = (int) shipmentTable.getValueAt(selectedRow, 0);
            String newStatus = (String) shipmentTable.getValueAt(selectedRow, 5);

            boolean success = assignController.updateAssignmentStatus(shipmentId, newStatus);

            if (success) {
                JOptionPane.showMessageDialog(rootPanel, "Assignment status updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                populateShipmentTable();
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Failed to update assignment status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void populateShipmentTable() {
        List<ShipmentAssignmentInfo> shipmentInfos = assignController.getShipmentsWithAssignments();
        String[] columnNames = {"ID", "Sender", "Receiver", "Shipment Status", "Driver Name", "Assignment Status"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only assignment status column is editable
            }
        };

        for (ShipmentAssignmentInfo info : shipmentInfos) {
            model.addRow(new Object[]{
                    info.getShipmentId(),
                    info.getSenderName(),
                    info.getReceiverName(),
                    info.getShipmentStatus(),
                    info.getDriverName() != null ? info.getDriverName() : "Not Assigned",
                    info.getAssignmentStatus() != null ? info.getAssignmentStatus() : "N/A"
            });
        }

        shipmentTable.setModel(model);
        shipmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String[] statusOptions = {"Assigned", "Completed", "Canceled"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        TableColumn statusColumn = shipmentTable.getColumnModel().getColumn(5);
        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    // Method used in Main.java to refresh drivers when tab is selected
    public void reloadDrivers() {
        assignController.loadDrivers(driverComboBox);
    }
}
