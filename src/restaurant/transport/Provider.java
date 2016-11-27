package restaurant.transport;

import restaurant.Adress;
import restaurant.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Provider implements Serializable {
    private String name;
    private String surname;
    private String PESEL;
    private Map<Integer,WorkDay> workDaysMap;
    private List<VehicleType> drivingLicenses;
    private Vehicle vehicle;
    private Adress position;
    private List<Order> orders;

    public Provider(String name, String surname, String PESEL) {
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        workDaysMap = new HashMap<>();
        drivingLicenses = new ArrayList<>();
        orders = new ArrayList<>();
        //position = ControlPanel.get().getAdress();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public Map<Integer, WorkDay> getWorkDaysMap() {
        return workDaysMap;
    }

    public void setWorkDaysMap(Map<Integer, WorkDay> workDaysMap) {
        this.workDaysMap = workDaysMap;
    }

    public List<VehicleType> getDrivingLicenses() {
        return drivingLicenses;
    }

    public void setDrivingLicenses(List<VehicleType> drivingLicenses) {
        this.drivingLicenses = drivingLicenses;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Adress getPosition() {
        return position;
    }

    public void setPosition(Adress position) {
        this.position = position;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
