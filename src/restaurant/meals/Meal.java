package restaurant.meals;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Meal implements Serializable,MealInterface {
    private double price;
    private MealCategory category;
    private MealSize size;
    private List<String> ingredients;
    private String name;

    public Meal(String name,double price, MealCategory category, MealSize size, List<String> ingredients) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.size = size;
        this.ingredients.addAll(ingredients);
    }

    @Override
    public String toString() {
        return 	"\n\tName: " + name
                +"\n\tPrice: " + Double.toString(price)
                +"\n\tMeal size: " + size
                +"\n\tIngredietns: " + ingredients.toString()
                +"\n\tCategory: " + category;
    }

    @Override
    public double countPrice() {
        return price;
    }

    @Override
    public int countAmount() {
        return size.getValue();
    }

    public MealCategory getCategory() {
        return category;
    }

    public void setCategory(MealCategory category) {
        this.category = category;
    }

    public MealSize getSize() {
        return size;
    }

    public void setSize(MealSize size) {
        this.size = size;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
