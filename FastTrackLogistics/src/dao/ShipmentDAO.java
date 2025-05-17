package dao;

import model.Shipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/fasttrack_logistics";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        String sql = "SELECT shipment_id, sender_name, receiver_name, status FROM shipments";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Shipment shipment = new Shipment();
                shipment.setShipmentId(rs.getInt("shipment_id"));
                shipment.setSenderName(rs.getString("sender_name"));
                shipment.setReceiverName(rs.getString("receiver_name"));
                shipment.setStatus(rs.getString("status"));
                shipments.add(shipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shipments;
    }
}
