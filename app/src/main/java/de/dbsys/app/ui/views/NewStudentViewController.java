package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NewStudentViewController extends GenericUIController {
    @FXML private StudentFormFieldsController studentFormFieldsController;

    @FXML
    private void onCancel() {
        stage.close();
    }

    @FXML
    private void onSave() {
        if(!studentFormFieldsController.isComplete()) {
            new Alert(Alert.AlertType.WARNING, "Bitte das Formular vollständig ausfüllen.").show();
            return;
        }
        Student student = studentFormFieldsController.toNewStudent();
        try {
            student.createStudent(Main.getDb(), Main.getDb().getConn());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Ein Fehler beim Erstellen ist aufgetreten.\n" + e.getMessage()).show();
            return;
        }
        stage.close();
    }

    @Override
    public void onAfterShow(Stage stage) {
        studentFormFieldsController.onAfterShow(stage);
        try {
            studentFormFieldsController.populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden des Studenten.").show();
        }
        stage.minWidthProperty().setValue(400);
    }
}
