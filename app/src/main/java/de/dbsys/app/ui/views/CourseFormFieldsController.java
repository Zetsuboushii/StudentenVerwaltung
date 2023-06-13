package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.utils.comparators.FirstNameStudentComparator;
import de.dbsys.app.ui.utils.comparators.NoCourseFirstStudentComparator;
import de.dbsys.app.ui.utils.ui.StudentsListCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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

    public void populate() {
        if(course != null) {
            setCourseData();
        }
        try {
            loadStudents();
        } catch (Exception exc) {
            handleException(exc, "Fehler beim Laden der Studenten: ");
        }
    }

    public void setCourseData() {
        tfCourseName.setText(course.getcName());
        tfRoom.setText(course.getRoom());
    }

    public void setCourse(Course course) {
        this.course = course;
        setCourseData();
        try {
            loadStudents();
        } catch (Exception exc) {
            handleException(exc, "Fehler beim Laden der Studenten: ");
        }
    }

    private void loadStudents() {
        initListViews();
        Connection con = Main.getDb().getConn();
        DatabaseCrawler crawler = new DatabaseCrawler();
        try {
            List<Student> allStudents = crawler.selectAllStudents(con);
            List<Student> assignedStudents;
            if (course != null) {
                assignedStudents = course.getStudents(con).stream().sorted(new FirstNameStudentComparator()).toList();
            } else {
                assignedStudents = List.of();
            }
            List<Student> availableStudents = allStudents.stream()
                    .filter(s -> !assignedStudents.contains(s))
                    .sorted(new NoCourseFirstStudentComparator())
                    .toList();
            lvAvailableStudents.setCellFactory(new StudentsListCellFactory(true));
            lvAvailableStudents.getItems().addAll(availableStudents);
            lvAssignedStudents.setCellFactory(new StudentsListCellFactory(false));
            lvAssignedStudents.getItems().addAll(assignedStudents);
        } catch (Exception exc) {
            handleException(exc, "Fehler beim Laden der Studenten: ");
            initListViews();
        }
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
            Student student = lvAvailableStudents.getItems().get(idx);
            lvAssignedStudents.getItems().add(student);
            if(course != null) {
                try {
                    student.editCourse(db, course);
                } catch (SQLException e) {
                    handleException(e, "Ein Fehler bei der Änderung ist aufgetreten: ");
                }
            }
        });
        // Remove from available students
        indizes.forEach(idx -> {
            Student student = lvAvailableStudents.getItems().get(idx);
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
            if(course != null) {
                try {
                    student.editCourse(db, null);
                } catch (SQLException e) {
                    handleException(e, "Fehler beim Ändern ist aufgetreten: ");
                }
            }
            lvAvailableStudents.getItems().add(student);
        });
        // Remove from assigned students
        indizes.forEach(idx -> {
            Student student = lvAssignedStudents.getItems().get(idx);
            lvAssignedStudents.getItems().remove(student);
        });
    }

    public void save() {
        DatabaseConnector db = Main.getDb();
        try {
            if (!tfRoom.getText().equals(course.getRoom())) {
                course.editRoom(db, tfRoom.getText());
            }
            if (!tfCourseName.getText().equals(course.getcName())) {
                course.editCname(db, tfCourseName.getText());
            }
        } catch (SQLException e) {
            handleException(e, "Fehler beim Speichern aufgetreten");
        }
    }

    public Course toNewCourse() {
        if(!isComplete()) {
            throw new IllegalStateException("Form incomplete");
        }
        return new Course(
                tfCourseName.getText(),
                tfRoom.getText()
        );
    }

    public void assignAllStudents(Course course) {
        if(!isComplete() || course == null) {
            throw new IllegalStateException("Form incomplete");
        }
        for(Student s : lvAssignedStudents.getItems()) {
            try {
                s.editCourse(Main.getDb(), course);
            } catch (Exception exc) {
                handleException(exc, "Fehler beim Speichern der Kursteilnehmer: ");
            }
        }
    }

    public boolean isComplete() {
        return tfCourseName.getText() != null && !tfCourseName.getText().isBlank()
                && tfRoom.getText() != null && !tfRoom.getText().isBlank();
    }

    public void deleteCourse() {
        if(course == null) {
            throw new IllegalStateException("Form incomplete");
        }
        try {
            course.deleteCourse(Main.getDb());
        } catch (Exception exc) {
            handleException(exc, "Fehler beim Löschen des Kurses: ");
        }
    }

    @Override
    public void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }
}
