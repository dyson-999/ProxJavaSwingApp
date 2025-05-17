import ui.AssignPanel;
import ui.DriverPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FastTrack Logistics - Management Dashboard");

            // Create tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Add Driver Management Tab
            DriverPanel driverPanel = new DriverPanel();
            tabbedPane.addTab("Driver Management", driverPanel.getRootPanel());

            // Add Assign Driver to Shipment Tab
            AssignPanel assignPanel = new AssignPanel();
            tabbedPane.addTab("Assign Driver", assignPanel.getRootPanel());

            // Set up the frame
            frame.setContentPane(tabbedPane);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null); // center the window
            frame.setVisible(true);
        });
    }
}
