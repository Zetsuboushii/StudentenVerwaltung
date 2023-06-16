package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.Main;
import de.dbsys.app.ui.utils.comparators.FirstNameStudentComparator;
import de.dbsys.app.ui.utils.comparators.LastNameStudentComparator;
import de.dbsys.app.ui.utils.filters.CourseStudentFilter;
import de.dbsys.app.ui.utils.filters.NoCourseStudentFilter;
import de.dbsys.app.ui.utils.filters.NoneStudentFilter;
import de.dbsys.app.ui.utils.ui.StudentsListCellFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StudentListViewController extends ListViewController {
    @FXML private SplitPane root;
    @FXML private ComboBox<Comparator<Student>> cbSort;
    @FXML private ComboBox<Predicate<Student>> cbFilter;
    @FXML private ListView<Student> lvElements;
    @FXML private EditStudentViewController editStudentViewController;
    @FXML private ListSearchViewController<Student> listSearchViewController;

    private List<Student> students;

    private final List<Comparator<Student>> comparators = List.of(
            new FirstNameStudentComparator(),
            new LastNameStudentComparator()
    );


    @Override
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
        makeSortBox(cbSort);
        cbSort.getItems().clear();
        cbSort.getItems().addAll(comparators);
        cbSort.getSelectionModel().select(0);
    }

    /**
     * Populates the filter box with all filters.
     * @throws SQLException if an error occurs while querying the courses for filters
     */
    private void populateFilterBox() throws SQLException {
        makeFilterBox(cbFilter);
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
        listSearchViewController.setSourceItems(students);
        listSearchViewController.filterItems();
        try {
            editStudentViewController.setVisible(false);
        } catch (Exception e) {
            handleException(e, "Fehler beim Aktualisieren der Elemente: ");
        }
    }

    @Override
    public void onBeforeShow(Stage stage) throws Exception {
        System.out.println("StudentListViewController.onBeforeShow");
        lvElements.getSelectionModel().getSelectedItems().addListener(
                this::onSelectionChanged
        );
        cbSort.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    listSearchViewController.setComparator(newValue);
                    listSearchViewController.filterItems();
                }
        );
        cbFilter.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    listSearchViewController.setFilter(newValue);
                    listSearchViewController.filterItems();
                }
        );
        editStudentViewController.setVisible(false);
        super.onBeforeShow(stage);

        listSearchViewController.initialize(students, cbSort.getSelectionModel().getSelectedItem());
        lvElements.setItems(listSearchViewController.getFilteredItems());
    }

    /**
     * Sets the selected student in the edit student view and shows or hides it appropriately.
     */
    private void onSelectionChanged(ListChangeListener.Change<? extends Student> change) {
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
        } catch (Exception e) {
            handleException(e, "Fehler beim Laden der Studenten: ");
        }
    }

    @Override
    protected void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }
}
