package restaurant;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import restaurant.clients.Customer;
import restaurant.meals.MealInterface;
import restaurant.transport.Provider;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by Aleksander Kaźmierczak on 16.01.2017.
 */
public class Container {
    private static Container container;
    private List<Customer> customerList;
    private List<Provider> providerList;
    private Stack<Order> orderList;
    private List<MealInterface> mealsList;
    private Semaphore semaphore = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);

    private AnchorPane anchorMap;
    private Polygon[][] polygonMap;// 0 - nothing 1 - customer 2- provider 3-restaurant
    private int polygonSize = 20;
    private int mapSize = 30;
    private Adress restaurantAdress;

    private static ObservableList<Node> paneChildren;

        public static Container get(){
        if(container==null){
            container = new Container();
        }
        return container;
    }

    public Container(){
        polygonSize = 20;
        mapSize = 30;
        customerList = new ArrayList<>();
        providerList = new ArrayList<>();
        mealsList = new ArrayList<>();
        orderList = new Stack<>();
        polygonMap = new Polygon[mapSize][mapSize];

        for (int i = 0; i < polygonMap.length; i++) {
            for (int j = 0; j < polygonMap[i].length; j++) {
                polygonMap[i][j] = new Polygon(i,j,0);
            }
        }

        restaurantAdress = generateAdress(0);
        polygonMap[restaurantAdress.getX()][restaurantAdress.getY()].setType(3);
    }

    public Customer generateCustomer(){
        System.out.println("GENERATING CUSTOMER");
        Adress adress = generateAdress(0);
        polygonMap[adress.getX()][adress.getY()].setType(1);
        Customer customer = new Customer("Arturo",3,"2222",adress,new Date());
        customerList.add(customer);
        return customer;
    }

    public Provider generateProvider(){
        System.out.println("GENERATING PROVIDER");
        Provider provider = new Provider("dddddwadwa","CHUJEC","22222fes");
        providerList.add(provider);
        return provider;
    }

    public Adress generateAdress(int type){
        Random random = new Random();
        int x,y;
        while (true){
            x = random.nextInt(mapSize);
            y = random.nextInt(mapSize);
            if( x<mapSize &&y<mapSize){
                if(polygonMap[x][y].getType()==type){
                    break;
                }
            }
        }
        return new Adress(x,y);
    }

    public int getPolygonSize() {
        return polygonSize;
    }

    public Polygon[][] getPolygonMap() {
        return polygonMap;
    }

    public Adress getRestaurantAdress() {
        return restaurantAdress;
    }

    public static ObservableList<Node> getPaneChildren() {
        return paneChildren;
    }

    public static void setPaneChildren(ObservableList<Node> paneChildren) {
        Container.paneChildren = paneChildren;
    }

    public int getMapSize() {
        return mapSize;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public Semaphore getMutex() {
        return mutex;
    }



    public  Stack<Order> getOrderList() {
        return orderList;
    }

    public static void setOrderList(Stack<Order> orderList) {
        orderList = orderList;
    }

    public  void putOrder(Order order){
            orderList.add(order);
    }

    public  Order getOrder(){
        if(orderList.size()>0){
            orderList.pop();
        }
        return null;
    }



}
