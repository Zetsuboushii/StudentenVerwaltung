package de.dbsys.app.ui.utils.ui;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;

public class UiStyler {

    /**
     * Adds the sort icon to a ComboBox.
     * @param cb ComboBox to add the icon to.
     */
    public static void makeSortBox(ComboBox<?> cb) {
        Region arrow = (Region)cb.lookup(".arrow");
        if(arrow == null) {
            return;
        }
        SVGPath arrowShape = (SVGPath) arrow.getShape();
        SVGPath sortShape = new SVGPath();
        // Source: https://icons.getbootstrap.com/icons/sort-down/
        sortShape.setContent("M3.5 2.5a.5.5 0 0 0-1 0v8.793l-1.146-1.147a.5.5 0 0 0-.708.708l2 1.999.007.007a.497.497 0 0 0 .7-.006l2-2a.5.5 0 0 0-.707-.708L3.5 11.293V2.5zm3.5 1a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM7.5 6a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zm0 3a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1h-3zm0 3a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1h-1z");
        sortShape.setFillRule(arrowShape.getFillRule());
        sortShape.setFill(arrowShape.getFill());
        arrow.setShape(sortShape);
        arrow.setMinSize(18, 12);
        cb.layout();
    }

    /**
     * Adds the filter icon to a ComboBox.
     * @param cb ComboBox to add the icon to.
     */
    public static void makeFilterBox(ComboBox<?> cb) {
        Region arrow = (Region)cb.lookup(".arrow");
        if(arrow == null) {
            return;
        }
        SVGPath arrowShape = (SVGPath) arrow.getShape();
        SVGPath sortShape = new SVGPath();
        // Source: https://icons.getbootstrap.com/icons/funnel/
        sortShape.setContent("M1.5 1.5A.5.5 0 0 1 2 1h12a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.128.334L10 8.692V13.5a.5.5 0 0 1-.342.474l-3 1A.5.5 0 0 1 6 14.5V8.692L1.628 3.834A.5.5 0 0 1 1.5 3.5v-2zm1 .5v1.308l4.372 4.858A.5.5 0 0 1 7 8.5v5.306l2-.666V8.5a.5.5 0 0 1 .128-.334L13.5 3.308V2h-11z");
        sortShape.setFillRule(arrowShape.getFillRule());
        sortShape.setFill(arrowShape.getFill());
        arrow.setShape(sortShape);
        arrow.setMinSize(12, 12);
        cb.layout();
    }
}
