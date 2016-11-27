package restaurant.meals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class MealSet implements Serializable,MealInterface {
    private double discount;
    private List<Meal> mealList;

    public MealSet(double discount) {
        this.discount = discount;
        mealList = new ArrayList<>();
    }

    @Override
    public int countAmount() {
        int i=0;
        for(Meal meal : mealList){
            i +=meal.countAmount();
        }
        return i;
    }

    @Override
    public double countPrice() {
        double i=0;
        for(Meal meal : mealList){
            i +=meal.countPrice();
        }
        return i-i*(discount/100);
    }

    public void addMeal(Meal meal){
        mealList.add(meal);
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }
}
