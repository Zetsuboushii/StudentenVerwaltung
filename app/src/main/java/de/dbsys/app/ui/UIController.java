package de.dbsys.app.ui;

import javafx.stage.Stage;

public interface UIController {
    void onBeforeShow(Stage stage) throws Exception;
    void onAfterShow(Stage stage) throws Exception;
}
