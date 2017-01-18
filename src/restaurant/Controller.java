package restaurant;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import restaurant.clients.Customer;
import restaurant.transport.Provider;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    AnchorPane anchorMap;

    public void onNewCustomerClick(){
        Customer customer = Container.get().generateCustomer();
        new Thread(customer).start();
    }

    public void onNewProviderClick(){
        Provider provider = Container.get().generateProvider();
        new Thread(provider).start();
    }

    public void onNewMealClick(){
        System.out.println("CHUJ");
    }

    public void onNewOrderClick(){
        //pozniej to ogarnac bo losownie i wkaldane do nowego obiektu moze nie pyknac :/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
