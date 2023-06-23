package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import javafx.beans.value.ChangeListener;

import java.sql.SQLException;

public abstract class FormFieldsController extends GenericUIController {
    private Runnable changeCallback;

    /**
     * Loads all required data from the database and populates form fields appropriately.
     * @throws SQLException if an error occurs while loading data from the database
     */
    public abstract void populate() throws SQLException;

    /**
     * Updates the database with changes in the form.
     * @throws SQLException if an error occurs while updating the database
     */
    public abstract void save() throws SQLException;

    /**
     * Deletes the current entity from the database.
     * @throws SQLException if an error occurs while deleting the entity from the database
     */
    public abstract void delete() throws SQLException;

    /**
     * Checks if the form is complete.
     * @return True if the form is complete, false otherwise.
     */
    public abstract boolean isComplete();

    protected void onChange(Runnable changeCallback) {
        this.changeCallback = changeCallback;
    }

    protected <T> ChangeListener<T> getChangeCallback() {
        return (observable, oldValue, newValue) -> runChangeCallback();
    }

    protected void runChangeCallback() {
        if(changeCallback != null) {
            changeCallback.run();
        }
    }
}
