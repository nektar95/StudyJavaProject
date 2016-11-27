package restaurant.clients;

import restaurant.Adress;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class CorporateCustomer extends Customer implements Serializable {
    private String correspondenceAdress;
    private String bankAccount;
    private String REGON;

    public CorporateCustomer(String name, int code, String phoneNumber, Adress deliveryAdress, Date orderTime, String correspondenceAdress, String bankAccount, String REGON) {
        super(name, code, phoneNumber, deliveryAdress, orderTime);
        this.correspondenceAdress = correspondenceAdress;
        this.bankAccount = bankAccount;
        this.REGON = REGON;
    }

    public String getCorrespondenceAdress() {
        return correspondenceAdress;
    }

    public void setCorrespondenceAdress(String correspondenceAdress) {
        this.correspondenceAdress = correspondenceAdress;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getREGON() {
        return REGON;
    }

    public void setREGON(String REGON) {
        this.REGON = REGON;
    }
}
