package restaurant.clients;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import restaurant.Adress;
import restaurant.Container;
import restaurant.DrawingShape;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Customer extends DrawingShape implements Serializable, Runnable {
    private String name;
    private int code;
    private String phoneNumber;
    private Date orderTime;
    private String eMail;
    transient private CustomerController controller;

    public Customer(String name, int code, String phoneNumber, Adress deliveryAdress, Date orderTime) {
        super(deliveryAdress, Color.BLUE);
        this.name = name;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.orderTime = orderTime;
        getShape().setOnMouseClicked(event -> {
            try {
                getShape().setFill(Color.CYAN);
                customerInfoBox();
            } catch (IOException e){

            }
        });
        drawMove();
    }

    public void reCreate(){
        setColor(Color.BLUE);
        draw();
        drawMove();
        getShape().setOnMouseClicked(event -> {
            try {
                customerInfoBox();
            } catch (IOException e){

            }
        });
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("CUSTOMER");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(2000,20000));
                Container.get().getMutex().acquire(); //podnosi semafor binanry

                Container.get().getOrderList().add(Container.get().generateOrder(this));

                System.out.println("NEW ORDER");
            } catch (InterruptedException ie){
                Thread.currentThread().interrupt();
                remove();
                break;
            } finally {
                Container.get().getMutex().release();
                Container.get().getSemaphore().release();
            }
        }
        System.out.println("CUSTOMER DELETED");
    }

    public void customerInfoBox() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Customer Info");
        stage.setOnCloseRequest(event -> {
            getShape().setFill(getColor());
        });
        stage.show();
        Thread thread = new Thread(() -> {
            try {
                controller.getListViewProvider().setItems(getListInfo());
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        });
        thread.setDaemon(false);
        thread.start();
    }

    public ObservableList<String> getListInfo(){
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Name:" + getName());
        list.add("Surname:" + getPhoneNumber());
        list.add("Order time:" + getOrderTime());
        list.add("Mail:" + geteMail());

        return list;
    }

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
