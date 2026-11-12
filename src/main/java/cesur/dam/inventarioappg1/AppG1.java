package cesur.dam.inventarioappg1;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class AppG1 extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneNavigator.setStage(stage);
        SceneNavigator.setDAO(new EquipoJdbcDAO(DataSourceFactory.getDataSource()));
        SceneNavigator.navigateToInjection("login-view.fxml", "Login");
        stage.show();
    }
}