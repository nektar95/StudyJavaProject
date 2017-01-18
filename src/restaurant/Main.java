
package restaurant;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import restaurant.clients.CorporateCustomer;
import restaurant.clients.Customer;
import restaurant.meals.Meal;
import restaurant.meals.MealSet;
import restaurant.transport.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        ObservableList<Node> paneChildren = ((Pane) root.lookup("#anchorMap")).getChildren();
        Container.get().setPaneChildren(paneChildren);
        primaryStage.setTitle("Restaurant Simulation");
        primaryStage.setScene(new Scene(root));

        Rectangle rect = new Rectangle(Container.get().getRestaurantAdress().getX()* Container.get().getPolygonSize(),
                Container.get().getRestaurantAdress().getY()* Container.get().getPolygonSize(),
                Container.get().getPolygonSize(),
                Container.get().getPolygonSize());
        rect.setFill(Color.GREEN);
        rect.toFront();

        Container.getPaneChildren().add(rect);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
