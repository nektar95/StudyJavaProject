
package restaurant;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.*;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        ObservableList<Node> paneChildren = ((Pane) root.lookup("#anchorMap")).getChildren();
        primaryStage.setTitle("Restaurant Simulation");
        primaryStage.setScene(new Scene(root));

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

        Container.get().setPaneChildren(paneChildren);

        Container.get().createRestaurant();
        Container.get().reCreate();

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