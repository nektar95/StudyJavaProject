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
}
