package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.Main;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class NewCourseViewController extends NewController {
    @FXML
    private CourseFormFieldsController courseFormFieldsController;

    @Override
    protected void createElement() throws SQLException {
        Course course = courseFormFieldsController.toNewCourse();
        course.createCourse(Main.getDb(), Main.getDb().getConn());
        courseFormFieldsController.assignAllStudents(course);
    }

    @Override
    protected FormFieldsController getFormFieldsController() {
        return courseFormFieldsController;
    }
}
