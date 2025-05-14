package ManageShipmentsForm;

import javax.swing.*;

public class ManageShipmentsApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Manage Shipments");
        frame.setContentPane(new ManageShipmentsFormui().mainPanel); // Load your UI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true); // Show the form
    }
}
