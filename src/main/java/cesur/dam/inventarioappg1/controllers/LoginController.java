package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public void login(ActionEvent actionEvent) {
        SceneNavigator.navigateToInjection("main-view.fxml", "Main");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}