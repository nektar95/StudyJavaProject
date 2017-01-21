package restaurant;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import restaurant.clients.Customer;
import restaurant.transport.Provider;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    AnchorPane anchorMap;

    public void onNewCustomerClick(){
        Customer customer = Container.get().generateCustomer();
        Thread thread = new Thread(customer);
        thread.start();
        Container.get().getThreadsMap().put(customer.getCode(),thread);
    }

    public void onNewProviderClick(){
        Provider provider = Container.get().generateProvider();
        new Thread(provider).start();
        Thread thread =new Thread(provider);
        thread.start();
        Container.get().getThreadsMap().put(Integer.parseInt(provider.getPESEL()),thread);
    }

    public void onNewMealClick(){

    }

    public void onNewOrderClick(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
