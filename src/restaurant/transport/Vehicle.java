package restaurant.transport;

import java.io.Serializable;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Vehicle implements Serializable {
    private VehicleType type;
    private double speed;
    private double fuel;
    private double fuelCapacity;
    private int capacity;
    private String registrationNumber;

    public Vehicle(VehicleType type, double speed, double fuel, double fuelCapacity, int capacity, String registrationNumber) {
        this.type = type;
        this.speed = speed;
        this.fuel = fuel;
        this.fuelCapacity = fuelCapacity;
        this.capacity = capacity;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "\n\tRegistration: " + registrationNumber
                +"\n\tType: " + type
                +"\n\tFuel capacity: "+Double.toString(fuelCapacity)
                +"\n\tFuel: "+Double.toString(fuel)
                +"\n\tSpeed: "+Double.toString(speed)
                +"\n\tCapacity: "+Double.toString(capacity);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
