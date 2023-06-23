package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.Main;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class NewStudentViewController extends NewController {
    @FXML private StudentFormFieldsController studentFormFieldsController;

    @Override
    protected void createElement() throws SQLException {
        Student student = studentFormFieldsController.toNewStudent();
        student.createStudent(Main.getDb(), Main.getDb().getConn());
    }

    @Override
    protected FormFieldsController getFormFieldsController() {
        return studentFormFieldsController;
    }
}
