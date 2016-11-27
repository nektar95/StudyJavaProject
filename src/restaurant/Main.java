package restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import restaurant.clients.Customer;
import restaurant.meals.Meal;
import restaurant.meals.MealSet;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Restaurant Simulation");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        Meal meal = new Meal();
        meal.setPrice(4);
        Meal meal1 = new Meal();
        meal1.setPrice(10);
        Meal meal2 = new Meal();
        meal2.setPrice(1);
        MealSet set = new MealSet(10);
        set.addMeal(meal1);
        set.addMeal(meal2);

        Order order = new Order(new Customer());
        order.addToOrder(meal);
        order.addToOrder(set);

        System.out.println("Price "+order.getPrice());
    }
}
