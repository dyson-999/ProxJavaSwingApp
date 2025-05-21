package dao;

import utils.DBConnection;

import java.sql.*;

public class DriverAssignmentDAO {

    // Insert or update driver assignment for the given shipment
    public boolean upsertAssignment(int shipmentId, int driverId) {
        String checkSql = "SELECT assignment_id FROM driver_assignments WHERE shipment_id = ?";
        String updateSql = "UPDATE driver_assignments SET driver_id = ?, assigned_at = ?, status = ? WHERE shipment_id = ?";
        String insertSql = "INSERT INTO driver_assignments (shipment_id, driver_id, assigned_at, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, shipmentId);
                ResultSet rs = checkStmt.executeQuery();

                Timestamp now = new Timestamp(System.currentTimeMillis());

                if (rs.next()) {
                    // Assignment exists, update it
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, driverId);
                        updateStmt.setTimestamp(2, now);
                        updateStmt.setString(3, "Assigned"); // Reset status to "Assigned" on new driver assignment
                        updateStmt.setInt(4, shipmentId);
                        int updated = updateStmt.executeUpdate();
                        return updated > 0;
                    }
                } else {
                    // No assignment yet, insert new
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, shipmentId);
                        insertStmt.setInt(2, driverId);
                        insertStmt.setTimestamp(3, now);
                        insertStmt.setString(4, "Assigned");
                        int inserted = insertStmt.executeUpdate();
                        return inserted > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update only the assignment status of a shipment
    public boolean updateAssignmentStatus(int shipmentId, String newStatus) {
        String sql = "UPDATE driver_assignments SET status = ? WHERE shipment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, shipmentId);

            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
