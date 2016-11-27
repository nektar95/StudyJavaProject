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
    private Customer customer;
    private List<MealInterface> mealsList;

    public Order(Customer customer) {
        mealsList = new ArrayList<>();
        this.customer = customer;
    }

    private void countOrderCost(){
        //CHECKING DISTANCE
        price = 0;
        for(MealInterface item : mealsList){
            price += item.countPrice();
        }
    }

    public void addToOrder(MealInterface item) {
        mealsList.add(item);
    }

    public double getPrice() {
        countOrderCost();
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
