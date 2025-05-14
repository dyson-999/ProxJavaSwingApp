package fasttrack.reports;

import fasttrack.model.DatabaseConnection;
import java.sql.*;
import java.time.YearMonth;

public class MonthlyReportGenerator {

    public String getReport(int year, int month) {
        StringBuilder report = new StringBuilder();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS total_shipments, " +
                    "SUM(CASE WHEN delivered = 1 THEN 1 ELSE 0 END) AS delivered_shipments " +
                    "FROM shipments WHERE MONTH(delivery_date) = ? AND YEAR(delivery_date) = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, month);
            stmt.setInt(2, year);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total_shipments");
                int delivered = rs.getInt("delivered_shipments");
                int pending = total - delivered;

                report.append("📆 Report for ").append(YearMonth.of(year, month)).append("\n\n");
                report.append("Total Shipments    : ").append(total).append("\n");
                report.append("Delivered Shipments: ").append(delivered).append("\n");
                report.append("Pending Shipments  : ").append(pending).append("\n");
            } else {
                report.append("No data available for ").append(YearMonth.of(year, month)).append(".");
            }

        } catch (SQLException e) {
            report.append("Database Error: ").append(e.getMessage());
        }
        return report.toString();
    }

}
