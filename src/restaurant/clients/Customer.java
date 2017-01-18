package restaurant.clients;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import restaurant.Adress;
import restaurant.Container;
import restaurant.DrawingShape;
import restaurant.Order;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Customer extends DrawingShape implements Serializable, Runnable {
    private String name;
    private int code;
    private String phoneNumber;
    private Date orderTime;
    private String eMail;

    public Customer(String name, int code, String phoneNumber, Adress deliveryAdress, Date orderTime) {
        super(deliveryAdress, Color.BLUE);
        this.name = name;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.orderTime = orderTime;
        drawMove();
    }

    @Override
    public void run() {
        while (true){
            System.out.println("CUSTOMER");
            try {
                Thread.sleep(5000); // LOSOWY OKRES MA BYC
                Container.get().getMutex().acquire(); //podnosi semafor binanry
                Order order = new Order(this);

                Container.get().getOrderList().add(order);

                Container.get().getMutex().release();

                System.out.println("NEW ORDER");
                //method generating order in container, static, synchronized?
                Container.get().getSemaphore().release();

            } catch (InterruptedException ie){
                //HANDLE THIS BY DRAWING BACK TRACE
            }
        }
    }

    public void addPoints(int p){}

    public boolean checkDiscount(){
        return false;
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
