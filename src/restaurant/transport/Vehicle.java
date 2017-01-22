package restaurant.transport;

import java.io.Serializable;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Vehicle implements Serializable {
    private VehicleType type;
    private long speed;
    private double fuel;
    private double fuelCapacity;
    private int capacity;
    private int usedCapacity;
    private String registrationNumber;

    public Vehicle(VehicleType type, long speed, double fuel, double fuelCapacity, int capacity, String registrationNumber) {
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
                +"\n\tCapacity: "+Double.toString(capacity)
                +"\n\tUsed capacity: "+Double.toString(usedCapacity);
    }

    public VehicleType getType() {
        return type;
    }

    public int getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public void fuelUsed(){
        fuel -= 0.1;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
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
