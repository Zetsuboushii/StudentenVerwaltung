package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseFormFieldsController extends GenericUIController {
    private Course course;
    @FXML
    private VBox root;
    @FXML
    private TextField tfCourseName;
    @FXML
    private TextField tfRoom;
    @FXML
    private ListView<Student> lvAvailableStudents;
    @FXML
    private ListView<Student> lvAssignedStudents;
    public Pair<String, String> getCourseData() {
        return new Pair<>(tfCourseName.getText(), tfRoom.getText());
    }

    public void setCourseData() {
        tfCourseName.setText(course.getcName());
        tfRoom.setText(course.getRoom());
    }

    public void setCourse(Course course) {
        this.course = course;
        setCourseData();
    }

    @Override
    public void onBeforeShow(Stage stage) throws Exception {
        loadStudents();
    }

    private void loadStudents() throws Exception {
        initListViews();
        Connection con = Main.getDb().getConn();
        DatabaseCrawler crawler = new DatabaseCrawler();
        List<Student> allStudents = crawler.selectAllStudents(con);
        List<Student> assignedStudents = crawler.selectAllStudents(con);
        List<Student> availableStudents = allStudents.stream()
                .filter(s -> !assignedStudents.contains(s))
                .toList();
        lvAvailableStudents.getItems().addAll(availableStudents);
        lvAssignedStudents.getItems().addAll(assignedStudents);
    }

    private void initListViews() {
        lvAvailableStudents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAvailableStudents.getItems().clear();
        lvAssignedStudents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAssignedStudents.getItems().clear();
    }

    @FXML
    private void onAssign() {
        DatabaseConnector db = Main.getDb();
        List<Integer> indizes = lvAvailableStudents.getSelectionModel().getSelectedIndices();
        // Assign student
        indizes.forEach(idx -> {
            Student student = lvAssignedStudents.getItems().get(idx);
            lvAssignedStudents.getItems().add(student);
            try {
                student.editCourse(db, course);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Ein Fehler bei der Änderunge ist aufgetreten.\n"+ e.getMessage()).show();
            }
        });
        // Remove from available students
        indizes.forEach(idx -> {
            Student student = lvAssignedStudents.getItems().get(idx);
            lvAvailableStudents.getItems().remove(student);
        });
    }

    @FXML
    private void onUnassign() {
        DatabaseConnector db = Main.getDb();
        List<Integer> indizes = lvAssignedStudents.getSelectionModel().getSelectedIndices();
        // Unassign student
        indizes.forEach(idx -> {
            Student student = lvAssignedStudents.getItems().get(idx);
            try {
                student.editCourse(db, null);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Ein Fehler beim Ändern ist aufgetreten.\n" + e.getMessage()).show();
            }
            lvAvailableStudents.getItems().add(student);
        });
        // Remove from assigned students
        indizes.forEach(idx -> {
            Student student = lvAssignedStudents.getItems().get(idx);
            lvAssignedStudents.getItems().remove(student);
        });
    }

    private void save() {
        DatabaseConnector db = Main.getDb();
        try {
            if (!tfRoom.getText().equals(course.getRoom())) {
                course.editRoom(db, tfRoom.getText());
            }
            if (!tfCourseName.getText().equals(course.getcName())) {
                course.editCname(db, tfCourseName.getText());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Ein Fehler beim Speichern ist aufgetreten.\n" + e.getMessage()).show();
        }
    }

    @Override
    public void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }
}
