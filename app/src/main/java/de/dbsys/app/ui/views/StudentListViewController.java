package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.utils.comparators.FirstNameStudentComparator;
import de.dbsys.app.ui.utils.comparators.LastNameStudentComparator;
import de.dbsys.app.ui.utils.filters.CourseStudentFilter;
import de.dbsys.app.ui.utils.filters.NoCourseStudentFilter;
import de.dbsys.app.ui.utils.filters.NoneStudentFilter;
import de.dbsys.app.ui.utils.ui.StudentsListCellFactory;
import de.dbsys.app.ui.utils.ui.UiStyler;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StudentListViewController extends GenericUIController {
    @FXML private ComboBox<Comparator<Student>> cbSort;
    @FXML private ComboBox<Predicate<Student>> cbFilter;
    @FXML private ListView<Student> lvElements;
    @FXML private EditStudentViewController editStudentViewController;

    private List<Student> students;

    private final List<Comparator<Student>> comparators = List.of(
            new FirstNameStudentComparator(),
            new LastNameStudentComparator()
    );

    /**
     * Reloads the list view with all students from the database.
     */
    public void reload() {
        try {
            populate();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Laden der Studenten: ");
        }
    }


    /**
     * Populates the list view with all students from the database.
     * @throws SQLException if an error occurs while querying the database
     */
    public void populate() throws SQLException {
        loadStudents();

        populateSortBox();
        populateFilterBox();

        populateList();
    }

    /**
     * Populates the list view with all students from the database.
     * @throws SQLException if an error occurs while querying the database
     */
    private void loadStudents() throws SQLException {
        students = new DatabaseCrawler().selectAllStudents(Main.getDb().getConn());

        try {
            editStudentViewController.setVisible(false);
        } catch (Exception e) {
            handleException(e, "Fehler beim Aktualisieren der Elemente: ");
        }
    }

    /**
     * Populates the sort box with all comparators.
     */
    private void populateSortBox() {
        UiStyler.makeSortBox(cbSort);
        cbSort.getItems().clear();
        cbSort.getItems().addAll(comparators);
        cbSort.getSelectionModel().select(0);
    }

    /**
     * Populates the filter box with all filters.
     * @throws SQLException if an error occurs while querying the courses for filters
     */
    private void populateFilterBox() throws SQLException {
        UiStyler.makeFilterBox(cbFilter);
        cbFilter.getItems().clear();
        cbFilter.getItems().add(new NoneStudentFilter());
        cbFilter.getItems().add(new NoCourseStudentFilter());
        List<Course> courses = new DatabaseCrawler().selectAllCourses(Main.getDb().getConn());
        cbFilter.getItems().addAll(courses.stream().map(CourseStudentFilter::new).toList());
        cbFilter.getSelectionModel().select(0);
    }

    /**
     * Populates the list view with all students.
     */
    private void populateList() {
        lvElements.setCellFactory(new StudentsListCellFactory(false));
        lvElements.getItems().clear();
        lvElements.getItems().addAll(students.stream().filter(cbFilter.getSelectionModel().getSelectedItem()).toList());
        lvElements.getItems().sort(cbSort.getSelectionModel().getSelectedItem());
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        super.onAfterShow(stage);
        lvElements.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Student>) c -> onSelectionChanged()
        );
        cbSort.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null)
                        lvElements.getItems().sort(newValue);
                }
        );
        cbFilter.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null && cbSort.getSelectionModel().getSelectedItem() != null) {
                        lvElements.getItems().clear();
                        lvElements.getItems().addAll(students.stream().filter(newValue).toList());
                        lvElements.getItems().sort(cbSort.getSelectionModel().getSelectedItem());
                    }
                }
        );
        editStudentViewController.setVisible(false);
        try {
            populate();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Laden der Studenten: ");
        }

    }

    /**
     * Sets the selected student in the edit student view and shows or hides it appropriately.
     */
    private void onSelectionChanged() {
        Student student = lvElements.getSelectionModel().getSelectedItem();
        if(student == null) {
            try {
                editStudentViewController.setVisible(false);
            } catch (Exception e) {
                handleException(e, "Fehler beim Laden der Studenten: ");
            }
            return;
        }
        editStudentViewController.setStudent(student);
        try {
            editStudentViewController.populate();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Laden der Studenten: ");
        }
        try {
            editStudentViewController.setVisible(true);
            editStudentViewController.populate();
        } catch (Exception e) {
            handleException(e, "Fehler beim Laden der Studenten: ");
        }
    }
}
