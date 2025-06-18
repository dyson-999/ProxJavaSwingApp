package dao;

import model.Driver;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    // Create - Insert new driver
    public boolean addDriver(Driver driver) {
        String sql = "INSERT INTO drivers (name, phone, schedule, routes, delivery_history) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, driver.getName());
            ps.setString(2, driver.getPhone());
            ps.setString(3, driver.getSchedule());
            ps.setString(4, driver.getRoutes());
            ps.setString(5, driver.getDeliveryHistory());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read - Get all drivers
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers ORDER BY driver_id DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("schedule"),
                        rs.getString("routes"),
                        rs.getString("delivery_history")
                );
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    // Update driver by ID
    public boolean updateDriver(Driver driver) {
        String sql = "UPDATE drivers SET name = ?, phone = ?, schedule = ?, routes = ?, delivery_history = ? WHERE driver_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, driver.getName());
            ps.setString(2, driver.getPhone());
            ps.setString(3, driver.getSchedule());
            ps.setString(4, driver.getRoutes());
            ps.setString(5, driver.getDeliveryHistory());
            ps.setInt(6, driver.getDriverId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete driver by ID
    public boolean deleteDriver(int driverId) {
        String sql = "DELETE FROM drivers WHERE driver_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, driverId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Optional: get driver by ID
    public Driver getDriverById(int driverId) {
        String sql = "SELECT * FROM drivers WHERE driver_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, driverId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Driver(
                            rs.getInt("driver_id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("schedule"),
                            rs.getString("routes"),
                            rs.getString("delivery_history")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Driver> getAllDriversBasic() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT driver_id, name FROM drivers ORDER BY name ASC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Use constructor with only ID and name (create one if not exists)
                Driver driver = new Driver();
                driver.setDriverId(rs.getInt("driver_id"));
                driver.setName(rs.getString("name"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }
}
