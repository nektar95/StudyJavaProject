package restaurant;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import restaurant.clients.Customer;
import restaurant.transport.Provider;

import java.io.Serializable;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class Controller implements Initializable, Serializable {
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

    public void onNewMealClick(){

    }

    public void onNewOrderClick(){
        int i = ThreadLocalRandom.current().nextInt(0,Container.get().getCustomerList().size());
        try {
            Container.get().getMutex().acquire();

            Container.get().getOrderList().add(Container.get().generateOrder(Container.get().getCustomerList().get(i)));

            Container.get().getMutex().release();
            Container.get().getSemaphore().release();
        }catch (InterruptedException ie){
            System.out.println(ie);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
