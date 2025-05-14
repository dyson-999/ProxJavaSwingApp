package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.YearMonth;
import java.util.Vector;

public class MonthlyReportPanel extends JPanel {
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> monthSelector;
    private JButton generateButton;

    public MonthlyReportPanel() {
        setLayout(new BorderLayout());

        // Top panel (filter and button)
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Month:"));

        monthSelector = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthSelector.addItem(String.format("%02d", i));
        }
        topPanel.add(monthSelector);

        generateButton = new JButton("Generate Report");
        topPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);

        // Table for displaying data
        tableModel = new DefaultTableModel();
        reportTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data on button click
        generateButton.addActionListener(e -> loadReportData());
    }

    private void loadReportData() {
        // Clear old data
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        String selectedMonth = (String) monthSelector.getSelectedItem();

        // DB Query
        String query = "SELECT id, sender, receiver, contents, status, delivery_date FROM shipments WHERE MONTH(delivery_date) = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fasttrack_logistics", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(selectedMonth));
            ResultSet rs = stmt.executeQuery();

            // Create columns
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(meta.getColumnName(i));
            }

            // Add rows
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
