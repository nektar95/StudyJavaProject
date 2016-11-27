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

    public Meal(double price, MealCategory category, MealSize size, List<String> ingredients) {
        this.price = price;
        this.category = category;
        this.size = size;
        this.ingredients = ingredients;
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
