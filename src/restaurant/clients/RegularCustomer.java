package restaurant.clients;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    public void addPoints(int p) {
        points +=p;
    }

    @Override
    public ObservableList<String> getListInfo() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Name:" + getName());
        list.add("Phone number:" + getPhoneNumber());
        list.add("Order time:" + getOrderTime());
        list.add("Points:" + getPoints());
        list.add("Discount:" + getDiscount());

        return list;
    }

    @Override
    public boolean checkDiscount() {
        if (points>10) {
            return true;
        }
        return false;
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
