
package restaurant;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import restaurant.clients.CorporateCustomer;
import restaurant.clients.Customer;
import restaurant.meals.Meal;
import restaurant.meals.MealSet;
import restaurant.transport.Provider;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        ObservableList<Node> paneChildren = ((Pane) root.lookup("#anchorMap")).getChildren();
        primaryStage.setTitle("Restaurant Simulation");
        primaryStage.setScene(new Scene(root));

        Rectangle rect = new Rectangle(Container.get().getRestaurantAdress().getX()* Container.get().getPolygonSize(),
                Container.get().getRestaurantAdress().getY()* Container.get().getPolygonSize(),
                Container.get().getPolygonSize(),
                Container.get().getPolygonSize());
        rect.setFill(Color.GREEN);
        rect.toFront();

        /*
        try{
            ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("container.ser")));
            String nagłówek = (String) in.readObject();
            Container.set((Container) in.readObject());
            in.close();
        }catch (IOException io){
            System.out.println("IO saving error"+io.toString());
        }
        */
        Container.get().setPaneChildren(paneChildren);

        Container.getPaneChildren().add(rect);

        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("resources\\ic_store_black_36dp_2x.png")));
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            String nazwaPliku = "container.ser";
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(nazwaPliku)));
                out.writeObject("Container");
                out.writeObject(Container.get());
                out.close();
                System.out.println("Saving state");
            }catch (IOException io){
                System.out.println("IO saving error: "+io.toString());
            }
            if(!Container.get().getThreadsMap().isEmpty()) {
                Container.get().getThreadsMap().forEach((i, t) -> {
                    t.interrupt();
                });
            }
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
