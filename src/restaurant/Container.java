package restaurant;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import restaurant.clients.CorporateCustomer;
import restaurant.clients.Customer;
import restaurant.clients.RegularCustomer;
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

    private Polygon[][] polygonMap;// 0 - nothing 1 - customer 2- provider 3-restaurant
    private int polygonSize = 20;
    private int mapSize = 30;
    private Adress restaurantAdress;
    private Map<Integer,Thread> threadsMap;

    private static ObservableList<Node> paneChildren;
    private static String names[] = {"Antoni", "Jarosław","Mariusz","Donald","Beata","Janusz","Grażyna","Wiesław","Eustachy",
        "Gromosław","Gwidon","Juliusz","Adam","Ewa","Ludolfina","Maria"};
    private static String surnames[] = {"Wieczyński","Macierewicz","Gęsiński","Klaszczak","Mlaszczak","Cebulowy","Kryciński",
        "Słowacki","Mickiewicz","Reymont","Curie","Carbon"};
    private static String meals[] = {"Pizza","Pasta","Tort","Ciasto","Sernik z rodzynkami","Kiełbasa","Chleb z bułką","Bułka z chlebem"};
    private static String ingriedents[] = {"Sól","Pieprz","Oregano","Czosnek","Musztarda","Papryka","Ser","Dżem","Miód","Bułka tarta"};

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
        threadsMap =  new HashMap<>();

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
        Customer customer = null;
        Random random = new Random();
        int type = random.nextInt(3);
        int name = random.nextInt(names.length);
        switch (type){
            case 0: {
                customer = new Customer(names[name],customerList.size()+1,Integer.toString(random.nextInt(20000)),adress,new Date());
                break;
            }
            case 1: {
                customer = new CorporateCustomer(names[name],customerList.size()+1,Integer.toString(random.nextInt(20000)),adress,new Date(),"","","");
                break;
            }
            case 2:{
                customer = new RegularCustomer(names[name],customerList.size()+1,Integer.toString(random.nextInt(20000)),adress,new Date(),random.nextInt(20),
                        random.nextInt(100));
                break;
            }
        }
        customerList.add(customer);
        return customer;
    }

    public Provider generateProvider(){
        System.out.println("GENERATING PROVIDER");
        Provider provider = new Provider("dddddwadwa","Bolec","324655");
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

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public Map< Integer,Thread> getThreadsMap() {
        return threadsMap;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    public List<MealInterface> getMealsList() {
        return mealsList;
    }

    public void setMealsList(List<MealInterface> mealsList) {
        this.mealsList = mealsList;
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
