package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NewCourseViewController extends GenericUIController {
    @FXML
    private CourseFormFieldsController courseFormFieldsController;

    @FXML
    private void onCancel() {
        stage.close();
    }

    @FXML
    private void onSave() {
        if(!courseFormFieldsController.isComplete()) {
            new Alert(Alert.AlertType.WARNING, "Bitte das Formular vollständig ausfüllen.").show();
            return;
        }
        Course course = courseFormFieldsController.toNewCourse();
        try{
            course.createCourse(Main.getDb(), Main.getDb().getConn());
            courseFormFieldsController.assignAllStudents(course);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Ein Fehler beim Erstellen ist aufgetreten.\n" + e.getMessage()).show();
            return;
        }
        Main.getMainController().reload();
        stage.close();
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        super.onAfterShow(stage);
        courseFormFieldsController.onAfterShow(stage);
        try {
            courseFormFieldsController.populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden des Studenten.").show();
        }
        stage.minWidthProperty().setValue(400);
    }
}
