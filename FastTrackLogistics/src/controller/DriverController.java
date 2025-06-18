package controller;

import dao.DriverDAO;
import model.Driver;

import java.util.List;

public class DriverController {
    private final DriverDAO driverDAO = new DriverDAO();

    public boolean addDriver(String name, String phone, String schedule, String routes, String deliveryHistory) {
        Driver driver = new Driver(name, phone, schedule, routes, deliveryHistory);
        return driverDAO.addDriver(driver);
    }

    public boolean addDriver(Driver driver) {
        return driverDAO.addDriver(driver);
    }



    public List<Driver> getAllDrivers() {
        return driverDAO.getAllDrivers();
    }

    public boolean updateDriver(int driverId, String name, String phone, String schedule, String routes, String deliveryHistory) {
        Driver driver = new Driver(driverId, name, phone, schedule, routes, deliveryHistory);
        return driverDAO.updateDriver(driver);
    }

    public boolean deleteDriver(int driverId) {
        return driverDAO.deleteDriver(driverId);
    }

    public Driver getDriverById(int driverId) {
        return driverDAO.getDriverById(driverId);
    }
}
