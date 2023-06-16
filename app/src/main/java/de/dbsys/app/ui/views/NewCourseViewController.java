package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NewCourseViewController extends GenericUIController {
    @FXML
    private CourseFormFieldsController courseFormFieldsController;

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
        if(!courseFormFieldsController.isComplete()) {
            new Alert(Alert.AlertType.WARNING, "Bitte das Formular vollständig ausfüllen.").show();
            return;
        }
        Course course = courseFormFieldsController.toNewCourse();
        try{
            course.createCourse(Main.getDb(), Main.getDb().getConn());
            courseFormFieldsController.assignAllStudents(course);
        } catch (SQLException e) {
            handleException(e, "Ein Fehler beim Erstellen ist aufgetreten: ");
            return;
        }
        Main.getMainController().reload();
        stage.close();
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        super.onAfterShow(stage);
        courseFormFieldsController.onAfterShow(stage);
        courseFormFieldsController.populate();
        stage.minWidthProperty().setValue(400);
    }

    @Override
    protected void setRootVisible(boolean visible) {
        throw new UnsupportedOperationException("Not supported on controllers that are directly loaded onto scenes.");
    }
}
