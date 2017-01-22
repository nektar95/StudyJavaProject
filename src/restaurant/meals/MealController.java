package restaurant.meals;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import restaurant.Container;
import restaurant.transport.Provider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksander Kaźmierczak on 18.01.2017.
 */
public class MealController {

    @FXML
    private ComboBox sizeCombo;

    @FXML
    private ComboBox categoryCombo;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    public void onAddMealClicked(){
        MealSize size =MealSize.MEDIUM;
        MealCategory category=MealCategory.PIZZA;
        if(categoryCombo.getValue().equals("Pizza")){
            category = MealCategory.PIZZA;
        }
        if(categoryCombo.getValue().equals("Pasta")){
            category =MealCategory.PASTA ;
        }
        if(categoryCombo.getValue().equals("Main")){
            category =MealCategory.MAIN;
        }
        if(categoryCombo.getValue().equals("Dessert")){
            category = MealCategory.DESSERT;
        }

        if(sizeCombo.getValue().equals("Small")){
            size =MealSize.SMALL ;
        }
        if(sizeCombo.getValue().equals("Medium")){
            size =MealSize.MEDIUM;
        }
        if(sizeCombo.getValue().equals("Big")){
            size =MealSize.BIG;
        }
        if(sizeCombo.getValue().equals("Family")){
            size =MealSize.FAMILY;
        }
        double price = Double.parseDouble(priceText.getText());

        List<String> list = Container.get().generateIngriedents();
        Meal meal = new Meal(nameText.getText(),price,category,size,list);
        Container.get().getMealsList().add(meal);
        System.out.println(meal);
    }
}
