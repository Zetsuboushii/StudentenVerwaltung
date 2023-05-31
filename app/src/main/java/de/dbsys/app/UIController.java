package de.dbsys.app;

import javafx.stage.Stage;

public interface UIController {
    void onBeforeShow(Stage stage);
    void onAfterShow(Stage stage);
}
