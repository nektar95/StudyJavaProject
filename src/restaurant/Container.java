package restaurant;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import restaurant.clients.CorporateCustomer;
import restaurant.clients.Customer;
import restaurant.clients.RegularCustomer;
import restaurant.meals.*;
import restaurant.transport.Provider;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Aleksander Kaźmierczak on 16.01.2017.
 */
public class Container implements Serializable{
    private static Container container;
    private List<Customer> customerList;
    private List<Provider> providerList;
    private Stack<Order> orderList;
    private List<MealInterface> mealsList = new ArrayList<>();
    private Semaphore semaphore = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);

    private Polygon[][] polygonMap;// 0 - nothing 1 - customer 2- provider 3-restaurant
    private int polygonSize = 20;
    private int mapSize = 30;
    private Adress restaurantAdress;
    transient private Map<Integer,Thread> threadsMap;

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

    public void reset(){
        if(!Container.get().getThreadsMap().isEmpty()) {
            Container.get().getThreadsMap().forEach((i, t) -> {
                t.interrupt();
            });
        }

        paneChildren.clear();
        providerList.clear();
        customerList.clear();
        mealsList.clear();
        threadsMap.clear();
        orderList.clear();
        startValues();
        container.createRestaurant();
    }

    public void createRestaurant(){
        Rectangle rect = new Rectangle(Container.get().getRestaurantAdress().getX()* Container.get().getPolygonSize(),
                Container.get().getRestaurantAdress().getY()* Container.get().getPolygonSize(),
                Container.get().getPolygonSize(),
                Container.get().getPolygonSize());
        rect.setFill(Color.GREEN);
        rect.toFront();
        Container.getPaneChildren().add(rect);
    }

    public static void set(Container c){
        container =c;
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

        startValues();
    }

    private void startValues(){
        for (int i = 0; i < polygonMap.length; i++) {
            for (int j = 0; j < polygonMap[i].length; j++) {
                polygonMap[i][j] = new Polygon(i,j,0);
            }
        }

        restaurantAdress = generateAdress(0);
        polygonMap[restaurantAdress.getX()][restaurantAdress.getY()].setType(3);

        List<String> list = new ArrayList<>();
        list.add("Sól");

        mealsList.add(new Meal("Pyzy",10, MealCategory.MAIN, MealSize.BIG,list));
        mealsList.add(new Meal("Pyzy",7, MealCategory.MAIN, MealSize.SMALL,list));

        list.clear();
        list.add("Pieprz");
        list.add("Musztarda");
        list.add("Miód");

        mealsList.add(new Meal("Zupka chińska",11, MealCategory.MAIN, MealSize.BIG,list));
        mealsList.add(new Meal("Zupka chińska",8, MealCategory.MAIN, MealSize.SMALL,list));

        list.clear();

        mealsList.add(new Meal("Budyń",8, MealCategory.DESSERT, MealSize.MEDIUM,list));
        mealsList.add(new Meal("Budyń",10, MealCategory.DESSERT, MealSize.BIG,list));

        list.clear();
        list.add("Pieprz");
        list.add("Papryka");
        list.add("Czosnek");
        list.add("Ser");
        list.add("Oregano");
        list.add("Musztarda");
        list.add("Miód");
        list.add("Mięso");

        mealsList.add(new Meal("Turbo pizza americana",25, MealCategory.PIZZA, MealSize.BIG,list));
        mealsList.add(new Meal("Turbo pizza americana",22, MealCategory.PIZZA, MealSize.MEDIUM,list));
        mealsList.add(new Meal("Turbo pizza americana",19, MealCategory.PIZZA, MealSize.SMALL,list));

        list.clear();
        list.add("Pieprz");
        list.add("Brokuł");

        mealsList.add(new Meal("Penne z brokułami",15, MealCategory.PASTA, MealSize.BIG,list));

        list.clear();
        list.add("Mango");
        list.add("Kiwi");
        list.add("Granat");

        mealsList.add(new Meal("Lody",13, MealCategory.DESSERT, MealSize.BIG,list));
        mealsList.add(new Meal("Lody",11, MealCategory.DESSERT, MealSize.MEDIUM,list));
        mealsList.add(new Meal("Lody",8, MealCategory.DESSERT, MealSize.SMALL,list));

        MealSet mealSet = new MealSet("Egzotyczny deser",10);
        mealSet.addMeal(new Meal("Lody",13, MealCategory.DESSERT, MealSize.BIG,list));
        list.clear();
        list.add("Jablko");

        mealSet.addMeal(new Meal("Koktajl",10, MealCategory.DESSERT, MealSize.BIG,list));

        mealsList.add(mealSet);
        list.clear();
        list.add("Bekon");
        list.add("Kurczak");
        list.add("Mielone");

        MealSet mealSet2 = new MealSet("Tłusta wyżerka",10);
        mealSet.addMeal(new Meal("Pizza rzeźnicka",25, MealCategory.PIZZA, MealSize.BIG,list));
        list.clear();
        list.add("Bekon");
        list.add("jajko");
        list.add("Sól");
        mealSet.addMeal(new Meal("Bekon z jajkiem",18, MealCategory.PIZZA, MealSize.BIG,list));

        mealsList.add(mealSet2);

        MealSet mealSet3 = new MealSet("Syty obiad",10);
        list.clear();
        list.add("Mięso");
        list.add("Ser");
        list.add("Oliwa");
        mealSet.addMeal(new Meal("Mieso z serem",28, MealCategory.MAIN, MealSize.BIG,list));
        list.clear();
        list.add("Sos z tytki");
        mealSet.addMeal(new Meal("Makaron z sosem pieczeniowym",10, MealCategory.PASTA, MealSize.MEDIUM,list));

        mealsList.add(mealSet3);
    }

    public void reCreate(){
        threadsMap =  new HashMap<>();
        customerList.forEach(customer -> {
            customer.reCreate();
            Thread thread = new Thread(customer);
            thread.setDaemon(false);
            thread.start();
            Container.get().getThreadsMap().put(customer.getCode(),thread);
        });
        providerList.forEach(provider -> {
            provider.reCreate();
            new Thread(provider).start();
            Thread thread =new Thread(provider);
            thread.setDaemon(false);
            thread.start();
            Container.get().getThreadsMap().put(Integer.parseInt(provider.getPESEL()),thread);
        });
    }

    public Order generateOrder(Customer customer){
        Order order = new Order(customer);
        int size = ThreadLocalRandom.current().nextInt(0,10);

        for (int i=0;i<size;i++){
            if(ThreadLocalRandom.current().nextInt(0,1)==1){

            } else {
                order.addToOrder(mealsList.get(ThreadLocalRandom.current().nextInt(0,mealsList.size())));
            }
        }

        return order;
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
        Random random = new Random();
        int surname = random.nextInt(surnames.length);
        int name = random.nextInt(names.length);
        Provider provider = new Provider(names[name],surnames[surname],Integer.toString(random.nextInt(10000)));
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
