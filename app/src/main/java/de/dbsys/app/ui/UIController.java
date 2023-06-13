package de.dbsys.app.ui;

import javafx.stage.Stage;

/**
 * Interface that defines basic functionality for Ui Controllers.
 */
public interface UIController {
    /**
     * Called before the Ui Component is shown.
     * @param stage The stage that the component is shown on.
     * @throws Exception If any error occurs while initializing.
     */
    void onBeforeShow(Stage stage) throws Exception;

    /**
     * Called after the Ui Component is shown.
     * @param stage The stage that the component is shown on.
     * @throws Exception If any error occurs while initializing.
     */
    void onAfterShow(Stage stage) throws Exception;
}
