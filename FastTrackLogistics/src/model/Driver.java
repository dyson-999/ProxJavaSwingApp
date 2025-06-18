package model;

public class Driver {
    private int driverId;
    private String name;
    private String phone;
    private String schedule;
    private String routes;
    private String deliveryHistory;

    // Constructors
    public Driver() {}

    public Driver(int driverId, String name, String phone, String schedule, String routes, String deliveryHistory) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.schedule = schedule;
        this.routes = routes;
        this.deliveryHistory = deliveryHistory;
    }

    public Driver(String name, String phone, String schedule, String routes, String deliveryHistory) {
        this.name = name;
        this.phone = phone;
        this.schedule = schedule;
        this.routes = routes;
        this.deliveryHistory = deliveryHistory;
    }

    // Getters and Setters
    public int getDriverId() {
        return driverId;
    }
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public String getRoutes() {
        return routes;
    }
    public void setRoutes(String routes) {
        this.routes = routes;
    }
    public String getDeliveryHistory() {
        return deliveryHistory;
    }
    public void setDeliveryHistory(String deliveryHistory) {
        this.deliveryHistory = deliveryHistory;
    }

    public String toString() {
        return this.name; // This ensures only the name appears in JComboBox
    }
}
