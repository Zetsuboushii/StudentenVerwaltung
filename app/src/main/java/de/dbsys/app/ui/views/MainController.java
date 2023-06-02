package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.utils.UILoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;


public class MainController extends GenericUIController {
    @Override
    public void onBeforeShow(Stage stage) {
        registerKeyboardShortcuts();
    }

    private void registerKeyboardShortcuts() {
        registerNewStudentKeyboardShortcut();
        registerNewCourseKeyboardShortcut();
    }

    private void registerNewStudentKeyboardShortcut() {
        KeyCodeCombination newStudent = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);
        // Button just temporary until the shortcut can be sent to the existing button
        Button btn = new Button("");
        btn.setOnAction(e -> neuerStudierender());
        stage.getScene().addMnemonic(new Mnemonic(btn, newStudent));
    }

    private void registerNewCourseKeyboardShortcut() {
        KeyCodeCombination newCourse = new KeyCodeCombination(KeyCode.N, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN);
        // Button just temporary until the shortcut can be sent to the existing button
        Button newCourseBtn = new Button("");
        newCourseBtn.setOnAction(e -> neuerKurs());
        stage.getScene().addMnemonic(new Mnemonic(newCourseBtn, newCourse));
    }

    @FXML
    public void neuerStudierender() {
        try {
            UILoader.showFXML("new-student-view", "Neue:r Studierende:r");
        } catch (Exception exc) {
            handleException(exc);
        }
    }

    @FXML
    public void neuerKurs() {
        try {
            UILoader.showFXML("new-course-view", "Neuer Kurs");
        } catch (Exception exc) {
            handleException(exc);
        }
    }
}