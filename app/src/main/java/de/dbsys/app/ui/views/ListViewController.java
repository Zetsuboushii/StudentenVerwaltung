package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.sql.SQLException;

public abstract class ListViewController extends GenericUIController {
    /**
     * Reloads the list view with all data from the database.
     */
    public void reload() {
        try {
            populate();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Laden der Kurse: ");
        }
    }

    /**
     * Populates the list view.
     * @throws SQLException if an error occurs while querying the database
     */
    public abstract void populate() throws SQLException;

    /**
     * Adds the sort icon to a ComboBox.
     * @param cb ComboBox to add the icon to.
     */
    protected void makeSortBox(ComboBox<?> cb) {
        setComboBoxIcon(
                cb,
                // Source: https://icons.getbootstrap.com/icons/sort-down/
                "M3.5 2.5a.5.5 0 0 0-1 0v8.793l-1.146-1.147a.5.5 0 0 0-.708.708l2 1.999.007.007a.497.497 0 0 0 .7-.006l2-2a.5.5 0 0 0-.707-.708L3.5 11.293V2.5zm3.5 1a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM7.5 6a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zm0 3a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1h-3zm0 3a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1h-1z",
                18, 12
        );
    }

    /**
     * Adds the filter icon to a ComboBox.
     * @param cb ComboBox to add the icon to.
     */
    protected void makeFilterBox(ComboBox<?> cb) {
        setComboBoxIcon(
                cb,
                // Source: https://icons.getbootstrap.com/icons/funnel/
                "M1.5 1.5A.5.5 0 0 1 2 1h12a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.128.334L10 8.692V13.5a.5.5 0 0 1-.342.474l-3 1A.5.5 0 0 1 6 14.5V8.692L1.628 3.834A.5.5 0 0 1 1.5 3.5v-2zm1 .5v1.308l4.372 4.858A.5.5 0 0 1 7 8.5v5.306l2-.666V8.5a.5.5 0 0 1 .128-.334L13.5 3.308V2h-11z",
                12, 12
        );
    }

    /**
     * Sets the icon of a ComboBox to the specified SVG-Path and the specified size. Waits for JavaFX to fully initialize the ComboBox
     * @param cb ComboBox to set the icon of.
     * @param svgPath SVG-Path of the icon.
     * @param sizeX Horizontal size of the icon.
     * @param sizeY Vertical size of the icon.
     */
    private void setComboBoxIcon(ComboBox<?> cb, String svgPath, int sizeX, int sizeY) {
        new Thread(() -> {
            Region arrow;
            try {
                arrow = getArrow(cb);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            SVGPath arrowShape = (SVGPath) arrow.getShape();
            SVGPath sortShape = new SVGPath();
            sortShape.setContent(svgPath);
            sortShape.setFillRule(arrowShape.getFillRule());
            sortShape.setFill(arrowShape.getFill());
            arrow.setShape(sortShape);
            arrow.setMinSize(sizeX, sizeY);
            Platform.runLater(cb::layout);
        }).start();
    }

    /**
     * Gets the arrow region of a ComboBox. Waits for the arrow to be initialized by JavaFX.
     * @param cb ComboBox to get the arrow from.
     * @return Arrow region.
     * @throws InterruptedException if the thread is interrupted while waiting for the arrow to be initialized.
     */
    private Region getArrow(ComboBox<?> cb) throws InterruptedException {
        Region arrow = null;
        while (arrow == null) {
            Thread.sleep(50);
            arrow = (Region)cb.lookup(".arrow");
        }
        return arrow;
    }

    @Override
    public void onBeforeShow(Stage stage) throws Exception {
        super.onBeforeShow(stage);
        reload();
    }
}
