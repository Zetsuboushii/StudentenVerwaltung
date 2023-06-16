package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.sql.SQLException;

public abstract class NewController extends GenericUIController {
    @FXML private Button btSave;

    /**
     * Close the window as the user wants to cancel.
     */
    @FXML
    private void onCancel() {
        stage.close();
    }

    /**
     * Save the new course.
     */
    @FXML
    private void onSave() {
        if(!getFormFieldsController().isComplete()) {
            new Alert(Alert.AlertType.WARNING, "Bitte das Formular vollständig ausfüllen.").show();
            return;
        }
        try {
            createElement();
        } catch (SQLException e) {
            handleException(e, "Ein Fehler beim Erstellen ist aufgetreten: ");
        }
        Main.getMainController().reload();
        stage.close();
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        btSave.setDisable(true);
        btSave.setTooltip(new Tooltip("Bitte das Formular vollständig ausfüllen."));
        try {
            getFormFieldsController().populate();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Laden der Studenten: ");
        }
        getFormFieldsController().onChange(() -> {
            if(getFormFieldsController().isComplete()) {
                btSave.setDisable(false);
                btSave.setTooltip(null);
            }
            else {
                btSave.setDisable(true);
                btSave.setTooltip(new Tooltip("Bitte das Formular vollständig ausfüllen."));
            }
        });
        stage.minWidthProperty().setValue(400);
        super.onAfterShow(stage);
    }

    protected abstract void createElement() throws SQLException;

    protected abstract FormFieldsController getFormFieldsController();

    @Override
    protected void setRootVisible(boolean visible) {}
}
