package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.SceneNavigator;
import cesur.dam.inventarioappg1.data.DAO;
import cesur.dam.inventarioappg1.models.Equipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtName;
    @FXML
    private ChoiceBox<String> choiceCategory;
    @FXML
    private CheckBox chkAvailable;
    @FXML
    private Spinner<Integer> spinnerId;
    @FXML
    private Button btnDelete;

    private final DAO<Equipo> dao;
    private final Equipo equipoSeleccionado;

    public DetailsController(DAO<Equipo> dao, Equipo equipoSeleccionado) {
        this.dao = dao;
        this.equipoSeleccionado = equipoSeleccionado;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerId.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1));
        spinnerId.setDisable(true);

        ObservableList<String> categories = FXCollections.observableArrayList(
                "Keyboard", "Laptop", "Gamepad", "Monitor", "Computer", "Printer"
        );
        choiceCategory.setItems(categories);
        choiceCategory.getSelectionModel().selectFirst();

        if (equipoSeleccionado != null) {
            welcomeText.setText("Modificar equipo");
            spinnerId.getValueFactory().setValue(equipoSeleccionado.getId().intValue());
            txtName.setText(equipoSeleccionado.getNombre());
            choiceCategory.setValue(equipoSeleccionado.getCategoria());
            chkAvailable.setSelected(Boolean.TRUE.equals(equipoSeleccionado.getDisponible()));
        } else {
            welcomeText.setText("Nuevo equipo");
            btnDelete.setVisible(false);
            btnDelete.setManaged(false);
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (hasEmptyFields()) {
            return;
        }

        String name = txtName.getText();
        String category = choiceCategory.getSelectionModel().getSelectedItem();
        boolean available = chkAvailable.isSelected();

        Equipo equipment = (equipoSeleccionado != null) ? equipoSeleccionado : new Equipo();
        equipment.setNombre(name);
        equipment.setCategoria(category);
        equipment.setDisponible(available);

        if (equipoSeleccionado == null) {
            dao.save(equipment);
        } else {
            dao.update(equipment);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardar");
        alert.setHeaderText("Guardando");
        alert.setContentText(name + " | " + category + " | " + (available ? "disponible" : "no disponible"));
        alert.showAndWait();

        SceneNavigator.navigateToInjection("main-view.fxml", "Main");
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        if (equipoSeleccionado == null) {
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Borrar equipo");
        confirm.setHeaderText("¿Eliminar " + equipoSeleccionado.getNombre() + "?");
        confirm.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dao.deleteById(equipoSeleccionado.getId());
            SceneNavigator.navigateToInjection("main-view.fxml", "Main");
        }
    }

    private boolean hasEmptyFields() {
        return txtName.getText().isBlank();
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneNavigator.navigateToInjection("main-view.fxml", "Main");
    }
}