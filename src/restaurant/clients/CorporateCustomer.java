package restaurant.clients;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @Override
    public ObservableList<String> getListInfo() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Name:" + getName());
        list.add("Phone number:" + getPhoneNumber());
        list.add("Order time:" + getOrderTime());
        list.add("Correspondence adress:" + getCorrespondenceAdress());
        list.add("Bank account:" + getBankAccount());
        list.add("REGON:" + getREGON());

        return list;
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
