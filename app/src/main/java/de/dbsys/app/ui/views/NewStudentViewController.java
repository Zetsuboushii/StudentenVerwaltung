package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NewStudentViewController extends GenericUIController {
    @FXML private StudentFormFieldsController studentFormFieldsController;

    /**
     * Close the windows as the user wants to cancel.
     */
    @FXML
    private void onCancel() {
        stage.close();
    }

    /**
     * Save the new student.
     */
    @FXML
    private void onSave() {
        if(!studentFormFieldsController.isComplete()) {
            new Alert(Alert.AlertType.WARNING, "Bitte das Formular vollständig ausfüllen.").show();
            return;
        }
        Student student = null;
        try {
            student = studentFormFieldsController.toNewStudent();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Erstellen des Studenten ist aufgetreten: ");
        }
        try {
            student.createStudent(Main.getDb(), Main.getDb().getConn());
        } catch (SQLException e) {
            handleException(e, "Ein Fehler beim Erstellen ist aufgetreten: ");
            return;
        }
        Main.getMainController().reload();
        stage.close();
    }

    @Override
    public void onAfterShow(Stage stage) {
        studentFormFieldsController.onAfterShow(stage);
        try {
            studentFormFieldsController.populate();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Laden der Studenten: ");
        }
        stage.minWidthProperty().setValue(400);
    }

    @Override
    protected void setRootVisible(boolean visible) {
        throw new UnsupportedOperationException("Not supported on controllers that are directly loaded onto scenes.");
    }
}
