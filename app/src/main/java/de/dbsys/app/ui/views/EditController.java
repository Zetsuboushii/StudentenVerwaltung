package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;

public abstract class EditController extends GenericUIController {
    @FXML
    private Button btSave;

    /**
     * Populate the view with the data
     * @throws SQLException if there's an error loading the data from the DB.
     */
    public void populate() throws SQLException {
        System.out.println("EditController.populate()");
        getFormFieldsController().populate();
        btSave.setDisable(true);
        getFormFieldsController().onChange(() -> btSave.setDisable(!getFormFieldsController().isComplete()));
    }

    @Override
    public void setVisible(boolean visible) throws Exception {
        System.out.println("EditController.setVisible(" + visible + ")");
        getFormFieldsController().setVisible(visible);
        super.setVisible(visible);
    }

    /**
     * Save date element.
     */
    @FXML
    protected void onSave() {
        if (!getFormFieldsController().isComplete()) {
            new Alert(Alert.AlertType.WARNING, "Bitte das Formular vollständig ausfüllen.").show();
        } else {
            try {
                getFormFieldsController().save();
            } catch (SQLException e) {
                handleException(e, "Ein Fehler beim Speichern ist aufgetreten: ");
            }
        }
        btSave.setDisable(true);
    }

    /**
     * Delete the element.
     */
    @FXML
    private void onDelete() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Soll dieses Element wirklich gelöscht werden?");
        confirm.showAndWait();
        if(confirm.getResult().getButtonData().isCancelButton()) {
            return;
        }
        try {
            getFormFieldsController().delete();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Löschen der Daten: ");
        }
        Main.getMainController().reload();
    }

    protected abstract FormFieldsController getFormFieldsController();
}
