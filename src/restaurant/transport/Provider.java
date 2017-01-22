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
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

    /**
     * provider constructor
     * @param name
     * @param surname
     * @param PESEL
     */
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
        int fuel = ThreadLocalRandom.current().nextInt(100,200);
        int type = ThreadLocalRandom.current().nextInt(0,100);

        vehicle = new Vehicle((type <70) ? VehicleType.CAR : VehicleType.SCOOTER
                , ThreadLocalRandom.current().nextLong(90,200)
                ,fuel
                ,fuel
                ,ThreadLocalRandom.current().nextInt(20,40)
                ,"PZ"+ThreadLocalRandom.current().nextInt(10000,20000));

        type = ThreadLocalRandom.current().nextInt(0,100);
        if(type>50){
            drivingLicenses.add(VehicleType.SCOOTER);
        }

        workDaysMap.put(1,new WorkDay(DayOfWeek.FRIDAY,LocalTime.of(ThreadLocalRandom.current().nextInt(6,14),0),LocalTime.of(ThreadLocalRandom.current().nextInt(14,22),0)));
        workDaysMap.put(2,new WorkDay(DayOfWeek.THURSDAY,LocalTime.of(ThreadLocalRandom.current().nextInt(6,14),0),LocalTime.of(ThreadLocalRandom.current().nextInt(14,22),0)));
        workDaysMap.put(3,new WorkDay(DayOfWeek.WEDNESDAY,LocalTime.of(ThreadLocalRandom.current().nextInt(6,14),0),LocalTime.of(ThreadLocalRandom.current().nextInt(14,22),0)));
        workDaysMap.put(4,new WorkDay(DayOfWeek.TUESDAY,LocalTime.of(ThreadLocalRandom.current().nextInt(6,14),0),LocalTime.of(ThreadLocalRandom.current().nextInt(14,22),0)));
        workDaysMap.put(5,new WorkDay(DayOfWeek.MONDAY,LocalTime.of(ThreadLocalRandom.current().nextInt(6,14),0),LocalTime.of(ThreadLocalRandom.current().nextInt(14,22),0)));

        getShape().setOnMouseClicked(event -> {
            try {
                getShape().setFill(Color.ORANGE);
                providerInfoBox();
            } catch (IOException e){

            }
        });
    }

    /**
     * called when loading previous map
     */

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

    public boolean checkLicense(){
        if(drivingLicenses.contains(vehicle.getType())){
            return true;
        }
        return false;
    }

    /**
     * main functionality of class
     */

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                Container.get().getSemaphore().acquire();
                Container.get().getMutex().acquire();

                System.out.println("PROVIDER");

                if (!Container.get().getOrderList().isEmpty() && getPosition() == Container.get().getRestaurantAdress() && checkLicense()) {
                    Container.get().getOrderList().forEach(order -> {
                        if(vehicle.getUsedCapacity() + order.getQuantify() < vehicle.getCapacity()){
                            vehicle.setUsedCapacity(order.getQuantify());
                            orders.add(order);
                        }
                    });
                    Container.get().getOrderList().removeAll(orders);
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
                    vehicle.setUsedCapacity(0);
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

    /**
     * function which simulate going to next customer
     * @throws InterruptedException
     */
    private void goToCustomer() throws InterruptedException{
        while (!orders.isEmpty()&&getPosition() != orders.peek().getCustomer().getPosition() &&!Thread.currentThread().isInterrupted() && !goingBack) {
            Thread.sleep(vehicle.getSpeed());
            deliver(WayType.ORDER);
            drawMove();
        }
    }

    /**
     * function which simulate going back to restaurant
     * @throws InterruptedException
     */
    private void goBack() throws InterruptedException{
        while (getPosition() != Container.get().getRestaurantAdress()&&!Thread.currentThread().isInterrupted()) {
            Thread.sleep(vehicle.getSpeed());
            deliver(WayType.HOME);
            drawMove();
        }
        fillIn();
        goingBack = false;
    }

    /**
     * drawing move and checking fuel
     */
    @Override
    public void drawMove() {
        if(vehicle.getFuel()<0){
            System.out.println(name + surname + " Out of fuel");
        } else {
            super.drawMove();
            vehicle.fuelUsed();
            if (isOpen) {
                Platform.runLater(() -> {
                    controller.getListViewProvider().setItems(getListInfo());
                });
            }
        }
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    /**
     * opening dialog with info
     * @throws IOException
     */

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

    /**
     * genereting info for dialog box
     * @return
     */
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

    /**
     * choose next position to move
     * @param type
     */
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

    /**
     * checking boundaries of map
     * @param x
     * @param y
     * @return
     */
    private boolean checkBoundaries(int x, int y){
        Container.get().getMapSize();
        if(x<Container.get().getMapSize() && x>=0 && y<Container.get().getMapSize() && y>=0){
            return true;
        }
        return false;
    }

    /**
     * caounting distance to target
     * @param type
     * @param x
     * @param y
     * @return
     */
    private double countDistnace(WayType type,int x, int y){
        if(type==WayType.ORDER && !orders.isEmpty()){
            return Math.sqrt(Math.pow(orders.peek().getCustomer().getPosition().getX()-x,2) + Math.pow(orders.peek().getCustomer().getPosition().getY()-y,2));
        } else {
            return Math.sqrt(Math.pow(Container.get().getRestaurantAdress().getX()-x,2) + Math.pow(Container.get().getRestaurantAdress().getY()-y,2));
        }
    }

    /**
     * filling fuel to full level
     */
    public void fillIn(){
        vehicle.setFuel(vehicle.getFuelCapacity());
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
