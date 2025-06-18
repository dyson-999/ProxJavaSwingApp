package dao;

import model.Shipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ShipmentAssignmentInfo;

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

    public List<ShipmentAssignmentInfo> getShipmentsWithAssignments() {
        List<ShipmentAssignmentInfo> list = new ArrayList<>();

        String sql = "SELECT s.shipment_id, s.sender_name, s.receiver_name, s.status AS shipment_status, " +
                "d.name AS driver_name, da.status AS assignment_status " +
                "FROM shipments s " +
                "LEFT JOIN driver_assignments da ON s.shipment_id = da.shipment_id " +
                "LEFT JOIN drivers d ON da.driver_id = d.driver_id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ShipmentAssignmentInfo(
                        rs.getInt("shipment_id"),
                        rs.getString("sender_name"),
                        rs.getString("receiver_name"),
                        rs.getString("shipment_status"),
                        rs.getString("driver_name"),
                        rs.getString("assignment_status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
