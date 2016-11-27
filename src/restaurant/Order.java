package restaurant;

import restaurant.clients.Customer;
import restaurant.meals.MealInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Order implements Serializable{
    private double price;
    private int quantify;
    private Customer customer;
    private List<MealInterface> mealsList;

    public Order(Customer customer) {
        mealsList = new ArrayList<>();
        this.customer = customer;
        if(customer.checkDiscount()){
            //very primitive way
            price = -100;
        }else {
            price = 0;
        }
        quantify = 0;
    }

    public int getQuantify() {
        return quantify;
    }

    public void setQuantify(int quantify) {
        this.quantify = quantify;
    }

    public void addToOrder(MealInterface item) {
        mealsList.add(item);
        quantify += item.countAmount();
        //CHECK DISTANCE
        price += item.countPrice();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<MealInterface> getMealsList() {
        return mealsList;
    }
}
