package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class EditStudentViewController extends GenericUIController {
    @FXML
    private StudentFormFieldsController studentFormFieldsController;
    public void setStudent(Student student) {
        studentFormFieldsController.setStudent(student);
    }

    public void populate() throws SQLException {
        studentFormFieldsController.populate();
    }

    @FXML private void onSave() {
        studentFormFieldsController.updateStudent();
    }
}
