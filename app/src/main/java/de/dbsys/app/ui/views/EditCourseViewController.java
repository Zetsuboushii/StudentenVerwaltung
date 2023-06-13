package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

public class EditCourseViewController extends GenericUIController {
    @FXML
    private VBox root;
    @FXML
    private CourseFormFieldsController courseFormFieldsController;
    @FXML
    private void onSave() {
        courseFormFieldsController.save();
    }
    @FXML
    private void onDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Soll der Kurs wirklich gelöscht werden?\nDabei wird der Kurs aller zugeordneten Studenten entfernt!");
        alert.showAndWait();
        if(alert.getResult().getButtonData().isCancelButton()) {
            return;
        }
        courseFormFieldsController.deleteCourse();
        Main.getMainController().reload();
    }

    public void setCourse(Course course) {
        courseFormFieldsController.setCourse(course);
    }

    @Override
    public void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }
}
