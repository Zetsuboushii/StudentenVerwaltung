package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
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
            new Alert(Alert.AlertType.ERROR, "Bitte das Formular vollständig ausfüllen.").show();
            return;
        }
        Student student = studentFormFieldsController.toNewStudent();
        // TODO: Alert failure
        student.createStudent(Main.getDb(), Main.getDb().getConn());
        stage.close();
    }

    @Override
    public void onAfterShow(Stage stage) {
        studentFormFieldsController.onAfterShow(stage);
        try {
            studentFormFieldsController.setStudent(new DatabaseCrawler().selectAllStudents(Main.getDb().getConn()).get(0));
            studentFormFieldsController.populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden des Studenten.").show();
        }
        stage.minWidthProperty().setValue(400);
    }
}
