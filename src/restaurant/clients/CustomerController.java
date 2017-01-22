package restaurant.clients;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import restaurant.Container;
import restaurant.transport.Provider;

/**
 * Created by Aleksander Kaźmierczak on 18.01.2017.
 */
public class CustomerController {
    @FXML
    private ListView<String> listViewProvider;

    public ListView<String> getListViewProvider() {
        return listViewProvider;
    }
}
