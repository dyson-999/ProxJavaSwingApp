import utils.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        System.out.println("Testing FastTrack Logistics Database Connection...");
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM shipments")) {
            System.out.println("Successfully connected to MySQL database!");
            System.out.println("Fetching data from shipments table:");
            while (rs.next()) {
                int id = rs.getInt("shipment_id");
                String senderName = rs.getString("sender_name");
                String receiverName = rs.getString("receiver_name");
                String status = rs.getString("status");
                System.out.printf("Shipment ID: %d, Sender: %s, Receiver: %s, Status: %s%n",
                        id, senderName, receiverName, status);
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}