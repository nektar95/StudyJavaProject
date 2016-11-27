package restaurant.clients;

import restaurant.Adress;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class RegularCustomer extends Customer implements Serializable {
    private int points;
    private double discount;

    public RegularCustomer(String name, int code, String phoneNumber, Adress deliveryAdress, Date orderTime, int points, double discount) {
        super(name, code, phoneNumber, deliveryAdress, orderTime);
        this.points = points;
        this.discount = discount;
    }

    @Override
    public void addPoints(int p) {
        points +=p;
    }

    @Override
    public boolean checkDiscount() {
        //check if points are below some value
        return true;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
