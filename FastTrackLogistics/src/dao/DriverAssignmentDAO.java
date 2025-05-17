package dao;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DriverAssignmentDAO {

    public boolean assignDriverToShipment(int shipmentId, int driverId) {
        String sql = "INSERT INTO driver_assignments (shipment_id, driver_id, assigned_at, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shipmentId);
            ps.setInt(2, driverId);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setString(4, "Assigned");

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
