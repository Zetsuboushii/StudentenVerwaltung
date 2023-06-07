package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class StudentFormFieldsController extends GenericUIController {
    @FXML
    private VBox root;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private ComboBox<Course> cbClass;
    @FXML
    private TextField tfCompany;
    @FXML
    private Slider slJavaExp;

    private Student student;

    public void populate() throws SQLException {
        populateCourses();
        if (student != null) {
            tfFirstName.setText(student.getFname());
            tfLastName.setText(student.getSname());
            tfCompany.setText(student.getCompany());
            slJavaExp.setValue(student.getJavaSkill());
        }
    }

    public void populateCourses() throws SQLException {
        cbClass.getItems().clear();
        cbClass.getItems().add(new Course("Kein Kurs"));
        cbClass.getSelectionModel().select(0);
        Connection conn = Main.getDb().getConn();
        DatabaseCrawler crawler = new DatabaseCrawler();
        List<Course> courses = crawler.selectAllCourses(conn);
        courses.forEach(course -> cbClass.getItems().add(course));
        if(student != null && student.getCourse() != null) {
            cbClass.getSelectionModel().select(student.getCourse());
        }
    }

    public void updateStudent() throws IllegalStateException {
        if(!isComplete() || student == null) {
            throw new IllegalStateException("Formular ist nicht vollständig.");
        }
        DatabaseConnector dbc = Main.getDb();
        if(!Objects.equals(tfFirstName.getText(), student.getFname()))
            student.editFname(dbc, tfFirstName.getText());
        if(!Objects.equals(tfLastName.getText(), student.getSname()))
            student.editSname(dbc, tfLastName.getText());
        if(!Objects.equals(tfCompany.getText(), student.getCompany()))
            student.editCompany(dbc, tfCompany.getText());
        if(student.getJavaSkill() != slJavaExp.getValue())
            student.editJavaSkill(dbc, (int)slJavaExp.getValue());
        if(!Objects.equals(cbClass.getValue(), student.getCourse())) {
            if(cbClass.getValue().getcName().equals("Kein Kurs")) {
                student.editCourse(dbc, null);
            } else {
                student.editCourse(dbc, cbClass.getValue());
            }
        }
    }

    public Student toNewStudent() throws IllegalStateException {
        if(!isComplete()) {
            throw new IllegalStateException("Formular ist nicht vollständig.");
        }
        if(cbClass.getValue().getcName().equals("Kein Kurs")) {
            return new Student(0, tfFirstName.getText(), tfLastName.getText(), tfCompany.getText(), (int) slJavaExp.getValue());
        }
        return new Student(0, tfFirstName.getText(), tfLastName.getText(), tfCompany.getText(), cbClass.getValue(), (int) slJavaExp.getValue());
    }

    public boolean isComplete() {
        return
                tfFirstName.getText() != null && !tfFirstName.getText().isEmpty()
                && tfLastName.getText() != null && !tfLastName.getText().isEmpty()
                && tfCompany.getText() != null && !tfCompany.getText().isEmpty()
                && slJavaExp.getValue() > 0;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public void setVisible(boolean visible) {
        root.setVisible(visible);
    }

    @Override
    public void onAfterShow(Stage stage) {
        cbClass.getItems().clear();
    }
}
