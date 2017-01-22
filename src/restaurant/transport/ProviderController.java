package restaurant.transport;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import restaurant.*;
import restaurant.Container;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by Aleksander Kaźmierczak on 18.01.2017.
 */
public class ProviderController {

    @FXML
    private ListView<String> listViewProvider;

    private String pesel;

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public ListView<String> getListViewProvider() {
        return listViewProvider;
    }

    public void setListViewProvider(ListView<String> listViewProvider) {
        this.listViewProvider = listViewProvider;
    }

    public void onReturnClicked(){

        Provider provider = Container.get().getProviderList().stream()
                .filter(provider1 -> provider1.getPESEL().equals(pesel))
                .findFirst()
                .get();
        provider.setGoingBack(true);
    }

    public void onDeleteClicked(){
        Container.get().getThreadsMap().get(Integer.parseInt(pesel)).interrupt();
        Container.get().getThreadsMap().remove(pesel);
    }
}
