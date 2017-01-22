package restaurant.meals;

import java.io.Serializable;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public enum MealSize implements Serializable{
    SMALL(1), MEDIUM(2), BIG(3), FAMILY(5);
    private final int value;
    private MealSize(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
