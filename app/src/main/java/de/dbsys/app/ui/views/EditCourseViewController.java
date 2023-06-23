package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Course;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class EditCourseViewController extends EditController {
    @FXML
    private VBox root;
    @FXML
    private CourseFormFieldsController courseFormFieldsController;

    public void setCourse(Course course) {
        courseFormFieldsController.setCourse(course);
    }


    @Override
    public void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }

    @Override
    protected FormFieldsController getFormFieldsController() {
        return courseFormFieldsController;
    }
}
