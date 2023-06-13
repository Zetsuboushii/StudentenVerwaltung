package de.dbsys.app.ui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Implements basic functionality for all Ui Controllers.
 */
public abstract class GenericUIController implements  UIController{
    protected Stage stage;

    @Override
    public void onBeforeShow(Stage stage) throws Exception {}

    @Override
    public void onAfterShow(Stage stage) throws Exception {}

    /**
     * Sets the Component visible and calls the before and after show methods.
     * @param visible Whether the component should be visible or not.
     * @throws Exception If any error occurs while initializing.
     */
    public void setVisible(boolean visible) throws Exception {
        onBeforeShow(null);
        setRootVisible(visible);
        onAfterShow(null);
    }

    /**
     * Sets the components root element visible. Used by the setVisible method.
     * @param visible Whether the component should be visible or not.
     */
    protected abstract void setRootVisible(boolean visible);

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * Show the exception in an alert.
     *
     * @param exc Exception to show
     * @param message Custom error message used to introduce the user to the exception
     */
    protected void handleException(Exception exc, String message) {
        exc.printStackTrace();
        new Alert(Alert.AlertType.ERROR, message + exc).show();
    }

    /**
     * Show the exception in an alert.
     *
     * @param exc Exception to show
     */
    protected void handleException(Exception exc) {
        handleException(exc, "Ein Fehler ist aufgetreten: ");
    }
}
