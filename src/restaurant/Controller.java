package restaurant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import restaurant.clients.Customer;
import restaurant.transport.Provider;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class Controller implements Serializable {
    @FXML
    AnchorPane anchorMap;

    public void onNewCustomerClick(){
        Customer customer = Container.get().generateCustomer();
        Thread thread = new Thread(customer);
        thread.setDaemon(false);
        thread.start();
        Container.get().getThreadsMap().put(customer.getCode(),thread);
    }

    public void onNewProviderClick(){
        Provider provider = Container.get().generateProvider();
        new Thread(provider).start();
        Thread thread =new Thread(provider);
        thread.setDaemon(false);
        thread.start();
        Container.get().getThreadsMap().put(Integer.parseInt(provider.getPESEL()),thread);
    }

    public void onNewMapClick(){
        Container.get().reset();
    }

    public void onNewMealClick(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("meals\\meal.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Meal add");
            stage.show();
        } catch (IOException e){
            System.out.println("Meal add error");
        }
    }

    public void onNewOrderClick(){
        if(Container.get().getCustomerList().size()>0) {
            int i = ThreadLocalRandom.current().nextInt(0,Container.get().getCustomerList().size());
            try {
                Container.get().getMutex().acquire();

                Container.get().getOrderList().add(Container.get().generateOrder(Container.get().getCustomerList().get(i)));

                Container.get().getMutex().release();
                Container.get().getSemaphore().release();
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        }
    }

}
