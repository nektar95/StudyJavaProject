package restaurant.transport;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import restaurant.Adress;
import restaurant.Container;
import restaurant.DrawingShape;
import restaurant.Order;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.math.*;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
enum WayType {
    ORDER, HOME
}
public class Provider extends DrawingShape implements Serializable, Runnable {
    private String name;
    private String surname;
    private String PESEL;
    private Map<Integer,WorkDay> workDaysMap;
    private List<VehicleType> drivingLicenses;
    private Vehicle vehicle;
    private Stack<Order> orders;
    transient private ProviderController controller;
    private boolean goingBack;
    transient private Stage stage;
    private boolean isOpen;


    public Provider(String name, String surname, String PESEL) {
        super(Container.get().getRestaurantAdress(),Color.RED);
        this.name = name;
        this.surname = surname;
        this.PESEL = PESEL;
        isOpen = false;
        workDaysMap = new HashMap<>();
        drivingLicenses = new ArrayList<>();
        drivingLicenses.add(VehicleType.CAR);
        orders = new Stack<>();
        goingBack = false;
        vehicle = new Vehicle(VehicleType.CAR,3,3,4,3,"dd");

        getShape().setOnMouseClicked(event -> {
            try {
                getShape().setFill(Color.ORANGE);
                providerInfoBox();
            } catch (IOException e){

            }
        });
    }

    public void reCreate(){
        setColor(Color.RED);
        draw();
        drawMove();
        getShape().setOnMouseClicked(event -> {
            try {
                providerInfoBox();
            } catch (IOException e){

            }
        });
    }

    @Override
    public String toString(){
        return " Name: " + name +
                "Surname: " + surname +
                "PESEL: " + PESEL +
                "Vehicle: " + vehicle+
                "Work days: " + getWorkDaysMap().toString() +
                        "Orders: " + getOrders().toString();


    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                Container.get().getSemaphore().acquire();
                Container.get().getMutex().acquire();

                System.out.println("PROVIDER");

                if (!Container.get().getOrderList().isEmpty() && getPosition() == Container.get().getRestaurantAdress()) {
                    orders.addAll(Container.get().getOrderList());
                    Container.get().getOrderList().clear();
                }
                Container.get().getMutex().release();


                if (orders != null) {
                    while (orders.size()>0) {
                        goToCustomer();
                        if(!goingBack) {
                            orders.pop();
                        }else {
                            break;
                        }
                    }
                    goingBack = true;
                }
                if(goingBack){
                    goBack();
                    goingBack = false;
                }
            } catch (InterruptedException ie){
                Thread.currentThread().interrupt();
                orders.clear();

                remove();
                Container.get().getMutex().release();
                break;
            }
        }
        Platform.runLater(() -> {
            Platform.setImplicitExit(false);
            if(stage!=null) {
                stage.close();
            }
        });
        System.out.println("DELETED");
    }

    private void goToCustomer() throws InterruptedException{
        while (!orders.isEmpty()&&getPosition() != orders.peek().getCustomer().getPosition() &&!Thread.currentThread().isInterrupted() && !goingBack) {
            Thread.sleep(100);
            deliver(WayType.ORDER);
            drawMove();
        }
    }
    private void goBack() throws InterruptedException{
        while (getPosition() != Container.get().getRestaurantAdress()&&!Thread.currentThread().isInterrupted()) {
            Thread.sleep(100);
            deliver(WayType.HOME);
            drawMove();
        }
        goingBack = false;
    }

    @Override
    public void drawMove() {
        super.drawMove();
        if(isOpen) {
            Platform.runLater(() -> {
                controller.getListViewProvider().setItems(getListInfo());
            });
        }
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void providerInfoBox() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("provider.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Provider Info");
        stage.setOnCloseRequest(event -> {
            getShape().setFill(getColor());
            isOpen = false;
        });
        stage.show();
        isOpen = true;
        Thread thread = new Thread(() -> {
            try {
                controller.getListViewProvider().setItems(getListInfo());
                controller.setPesel(PESEL);
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
        list.add("Surname:" + getSurname());
        list.add("PESEL:" + getPESEL());
        list.add("Work Schedule:" + getWorkDaysMap().toString());
        list.add("Driving Licence:" + getDrivingLicenses().toString());
        list.add("Vehicle:" + getVehicle().toString());
        String orders;
        if (getOrders().size() == 0) {
            orders = "Empty";
        } else {
            orders = getOrders().peek().toString();
        }
        list.add("Orders:" + orders
        );
        return list;
    }

    public void deliver(WayType type){
        synchronized (Container.get().getPolygonMap()){
            double shortest = Double.MAX_VALUE;
            Adress adress = getPosition();
            int x = getPosition().getX();
            int y = getPosition().getY();
            double temp;
            if(!(Container.get().getPolygonMap()[getPosition().getX()][getPosition().getY()].getType()==1||Container.get().getPolygonMap()[getPosition().getX()][getPosition().getY()].getType()==3)) {
                Container.get().getPolygonMap()[getPosition().getX()][getPosition().getY()].setType(0);
            }

            if(checkBoundaries(x,y+1)&&Container.get().getPolygonMap()[x][y+1].getType()==0){
                temp = countDistnace(type,x,y+1);

                if(temp< shortest){
                    shortest = temp;
                    adress = new Adress(x,y+1);
                }
            }
            if(checkBoundaries(x,y-1)&&Container.get().getPolygonMap()[x][y-1].getType()==0){
                temp = countDistnace(type,x,y-1);

                if(temp< shortest){
                    shortest = temp;
                    adress = new Adress(x,y-1);
                }
            }
            if(checkBoundaries(x+1,y)&&Container.get().getPolygonMap()[x+1][y].getType()==0){
                temp = countDistnace(type,x+1,y);

                if(temp< shortest){
                    shortest = temp;
                    adress = new Adress(x+1,y);
                }
            }
            if(checkBoundaries(x-1,y)&&Container.get().getPolygonMap()[x-1][y].getType()==0){
                temp = countDistnace(type,x-1,y);

                if(temp< shortest){
                    shortest=temp;
                    adress = new Adress(x-1,y);
                }
            }
            if(shortest<2){
                if(type==WayType.ORDER){
                    setPosition(orders.peek().getCustomer().getPosition());
                } else {
                    setPosition(Container.get().getRestaurantAdress());
                }
                return;
            } else {
                setPosition(adress);
            }
            Container.get().getPolygonMap()[getPosition().getX()][getPosition().getY()].setType(2);
        }
    }

    private boolean checkBoundaries(int x, int y){
        Container.get().getMapSize();
        if(x<Container.get().getMapSize() && x>=0 && y<Container.get().getMapSize() && y>=0){
            return true;
        }
        return false;
    }

    private double countDistnace(WayType type,int x, int y){
        if(type==WayType.ORDER && !orders.isEmpty()){
            return Math.sqrt(Math.pow(orders.peek().getCustomer().getPosition().getX()-x,2) + Math.pow(orders.peek().getCustomer().getPosition().getY()-y,2));
        } else {
            return Math.sqrt(Math.pow(Container.get().getRestaurantAdress().getX()-x,2) + Math.pow(Container.get().getRestaurantAdress().getY()-y,2));
        }
    }

    public void fillIn(){

    }

    public boolean isGoingBack() {
        return goingBack;
    }

    public void setGoingBack(boolean goingBack) {
        this.goingBack = goingBack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public Map<Integer, WorkDay> getWorkDaysMap() {
        return workDaysMap;
    }

    public void setWorkDaysMap(Map<Integer, WorkDay> workDaysMap) {
        this.workDaysMap = workDaysMap;
    }

    public List<VehicleType> getDrivingLicenses() {
        return drivingLicenses;
    }

    public void setDrivingLicenses(List<VehicleType> drivingLicenses) {
        this.drivingLicenses = drivingLicenses;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public Stack<Order> getOrders() {
        return orders;
    }

    public void setOrders(Stack<Order> orders) {
        this.orders = orders;
    }
}
