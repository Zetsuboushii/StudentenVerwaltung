package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.Main;
import de.dbsys.app.ui.utils.comparators.FirstNameStudentComparator;
import de.dbsys.app.ui.utils.comparators.NoCourseFirstStudentComparator;
import de.dbsys.app.ui.utils.ui.StudentsListCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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

    /**
     * Populate view with student and course data (if available).
     */
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

    /**
     * Apply course data to view's fields.
     */
    public void setCourseData() {
        tfCourseName.setText(course.getcName());
        tfRoom.setText(course.getRoom());
    }

    /**
     * Set the course of the view and update its fields.
     *
     * @param course new course
     */
    public void setCourse(Course course) {
        this.course = course;
        setCourseData();
        try {
            loadStudents();
        } catch (Exception exc) {
            handleException(exc, "Fehler beim Laden der Studenten: ");
        }
    }

    /**
     * Load students from DB and update views.
     */
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

    /**
     * Initialize/reset list views.
     */
    private void initListViews() {
        lvAvailableStudents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAvailableStudents.getItems().clear();
        lvAssignedStudents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAssignedStudents.getItems().clear();
    }

    /**
     * Assign selected students to course.
     */
    @FXML
    private void onAssign() {
        transferBetweenLists(lvAvailableStudents.getItems(), lvAssignedStudents.getItems(), lvAvailableStudents.getSelectionModel().getSelectedIndices());
    }

    /**
     * Unassign selected students from course.
     */
    @FXML
    private void onUnassign() {
        transferBetweenLists(lvAssignedStudents.getItems(), lvAvailableStudents.getItems(), lvAssignedStudents.getSelectionModel().getSelectedIndices());
    }

    /**
     * Transfer items from one list to another.
     * @param fromList List to transfer items from
     * @param toList List to transfer items to
     * @param indizes Indizes of items to transfer (regarding fromList, of course)
     * @param <T> Type of items in lists
     */
    private <T> void transferBetweenLists(List<T> fromList, List<T> toList, List<Integer> indizes) {
        List<T> itemsToTransfer = indizes.stream()
                .map(fromList::get)
                .toList();
        toList.addAll(itemsToTransfer);
        List<T> itemsToRemove = indizes.stream()
                .map(fromList::get)
                .toList();
        itemsToRemove.forEach(fromList::remove);
    }

    /**
     * Updates the database with changes in the form.
     */
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
        assignAllStudents(course);
        unassignAllStudents(course);
    }

    /**
     * Creates a new course from the form data.
     * @return The new course.
     */
    public Course toNewCourse() {
        if(!isComplete()) {
            throw new IllegalStateException("Form incomplete");
        }
        return new Course(
                tfCourseName.getText(),
                tfRoom.getText()
        );
    }

    /**
     * Assign all students selected to be assigned to the course.
     * @param course course to assign students to
     */
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

    /**
     * Unassign all selected students from the course.
     * @param course course to unassign students from
     */
    private void unassignAllStudents(Course course) {
        if (!isComplete() || course == null) {
            throw new IllegalStateException("Form incomplete");
        }
        DatabaseConnector db = Main.getDb();
        for(Student s: lvAvailableStudents.getItems()) {
            if (s.getCourse().equals(course)) {
                try {
                    s.editCourse(db, null);
                } catch (Exception exc) {
                    handleException(exc, "Fehler beim Speichern der Kursteilnehmer: ");
                }
            }
        }
    }

    /**
     * Checks if the form is complete.
     * @return True if the form is complete, false otherwise.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isComplete() {
        return tfCourseName.getText() != null && !tfCourseName.getText().isBlank()
                && tfRoom.getText() != null && !tfRoom.getText().isBlank();
    }

    /**
     * Deletes the course from the database.
     */
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
