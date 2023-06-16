package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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

    /**
     * Loads all required data from the database and populates form fields if a student is set.
     * @throws SQLException if an error occurs while loading data from the database
     */
    public void populate() throws SQLException {
        populateCourses();
        if (student != null) {
            tfFirstName.setText(student.getFname());
            tfLastName.setText(student.getSname());
            tfCompany.setText(student.getCompany());
            slJavaExp.setValue(student.getJavaSkill());
        }
    }

    /**
     * Loads all courses from the database and populates the course combobox, selecting the appropriate course.
     * @throws SQLException if an error occurs while loading data from the database
     */
    public void populateCourses() throws SQLException {
        cbClass.getItems().clear();
        cbClass.getItems().add(new Course("Kein Kurs"));
        cbClass.getSelectionModel().select(0);
        Connection conn = Main.getDb().getConn();
        DatabaseCrawler crawler = new DatabaseCrawler();
        List<Course> courses = crawler.selectAllCourses(conn);
        courses.forEach(course -> cbClass.getItems().add(course));
        cbClass.getSelectionModel().select(0);
        if(student != null && student.getCourse() != null) {
            cbClass.getSelectionModel().select(student.getCourse());
        }
    }

    /**
     * Updates the student in the database with all changes values.
     * @throws IllegalStateException if the form is incomplete or no student is set
     */
    public void updateStudent() throws IllegalStateException {
        if(!isComplete() || student == null) {
            throw new IllegalStateException("Formular ist nicht vollständig.");
        }
        try {
            DatabaseConnector dbc = Main.getDb();
            if (!Objects.equals(tfFirstName.getText(), student.getFname()))
                student.editFname(dbc, tfFirstName.getText());
            if (!Objects.equals(tfLastName.getText(), student.getSname()))
                student.editSname(dbc, tfLastName.getText());
            if (!Objects.equals(tfCompany.getText(), student.getCompany()))
                student.editCompany(dbc, tfCompany.getText());
            if (student.getJavaSkill() != slJavaExp.getValue())
                student.editJavaSkill(dbc, (int) slJavaExp.getValue());
            if(student.getCourse() != null) {
                if (!Objects.equals(cbClass.getValue(), student.getCourse())) {
                    if (cbClass.getValue().getcName().equals("Kein Kurs")) {
                        student.editCourse(dbc, null);
                    } else {
                        student.editCourse(dbc, cbClass.getValue());
                    }
                }
            } else  {
                if (!cbClass.getValue().getcName().equals("Kein Kurs")) {
                    student.editCourse(dbc, cbClass.getValue());
                }
            }
        } catch (SQLException e) {
            handleException(e, "Fehler beim Ändern aufgetreten: ");
        }
    }

    /**
     * Deletes the student from the database.
     * @throws IllegalStateException if no student is set
     */
    public void deleteStudent() throws SQLException {
        if(student == null) {
            throw new IllegalStateException("Formular ist nicht vollständig.");
        }
        student.deleteStudent(Main.getDb());
    }

    /**
     * Creates a new student with all values from the form.
     * @return the new student
     * @throws IllegalStateException if the form is incomplete
     * @throws SQLException if an error occurs while calculating the mNr
     */
    public Student toNewStudent() throws IllegalStateException, SQLException {
        if(!isComplete()) {
            throw new IllegalStateException("Formular ist nicht vollständig.");
        }

        if(cbClass.getValue().getcName().equals("Kein Kurs")) {
            return new Student(calculateMNr(), tfFirstName.getText(), tfLastName.getText(), tfCompany.getText(), (int) slJavaExp.getValue());
        }
        return new Student(calculateMNr(), tfFirstName.getText(), tfLastName.getText(), tfCompany.getText(), cbClass.getValue(), (int) slJavaExp.getValue());
    }

    // This isn't pretty, but it removes the need for matriculation numbers to be handled in the ui
    private int calculateMNr() throws SQLException {
        List<Integer> students = new DatabaseCrawler().selectAllStudents(Main.getDb().getConn()).stream()
                .map(Student::getmNr).toList();
        int random = (int)(Math.random()*10000);
        while(students.contains(random)) {
            random = (int)(Math.random()*10000);
        }
        return random;
    }

    /**
     * Checks if the form is complete.
     * @return true if the form is complete, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
    public void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }

    @Override
    public void onAfterShow(Stage stage) {
        cbClass.getItems().clear();
    }
}
