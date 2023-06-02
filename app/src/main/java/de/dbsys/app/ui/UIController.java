package de.dbsys.app.ui;

import javafx.stage.Stage;

public interface UIController {
    void onBeforeShow(Stage stage);
    void onAfterShow(Stage stage);
}
