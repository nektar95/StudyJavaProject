package restaurant.clients;

import restaurant.Adress;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Customer implements Serializable {
    private String name;
    private int code;
    private String phoneNumber;
    private Adress deliveryAdress;
    private Date orderTime;
    private String eMail;

    public Customer(String name, int code, String phoneNumber, Adress deliveryAdress, Date orderTime) {
        this.name = name;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.deliveryAdress = deliveryAdress;
        this.orderTime = orderTime;
    }

    public void addPoints(int p){}

    public boolean checkDiscount(){
        return false;
    }

    public boolean draw()
    {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Adress getDeliveryAdress() {
        return deliveryAdress;
    }

    public void setDeliveryAdress(Adress deliveryAdress) {
        this.deliveryAdress = deliveryAdress;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
