package fasttrack.view;

import fasttrack.reports.MonthlyReportGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReportUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReportUI().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("📦 FastTrack Monthly Report");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Center window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("📊 Monthly Shipment Report");
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(heading, gbc);

        gbc.gridwidth = 1; // Reset
        // Year
        JLabel yearLabel = new JLabel("Enter Year:");
        yearLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(yearLabel, gbc);

        JTextField yearField = new JTextField("2025");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(yearField, gbc);

        // Month
        JLabel monthLabel = new JLabel("Enter Month (1-12):");
        monthLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(monthLabel, gbc);

        JTextField monthField = new JTextField("5");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(monthField, gbc);

        // Button
        JButton generateButton = new JButton("Generate Report");
        generateButton.setBackground(new Color(59, 89, 152));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(generateButton, gbc);

        // Output Area
        JTextArea resultArea = new JTextArea(8, 40);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        // Add panel to frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);

        // Action Listener
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int year = Integer.parseInt(yearField.getText().trim());
                    int month = Integer.parseInt(monthField.getText().trim());
                    if (month < 1 || month > 12) throw new NumberFormatException("Month out of range");

                    MonthlyReportGenerator generator = new MonthlyReportGenerator();
                    String report = generator.getReport(year, month);
                    resultArea.setText(report);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid year and month (1-12).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
