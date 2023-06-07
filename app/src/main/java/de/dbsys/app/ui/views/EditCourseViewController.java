package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;

public class EditCourseViewController extends GenericUIController {
    @FXML
    private CourseFormFieldsController courseFormFieldsController;
    @FXML
    private void onSave() {
        courseFormFieldsController.save();
    }

    public void setCourse(Course course) {
        courseFormFieldsController.setCourse(course);
    }
}
