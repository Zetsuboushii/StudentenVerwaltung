module de.dbsys.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens de.dbsys.app to javafx.fxml;
    exports de.dbsys.app.ui;
    opens de.dbsys.app.ui to javafx.fxml;
    exports de.dbsys.app.ui.views;
    opens de.dbsys.app.ui.views to javafx.fxml;
    exports de.dbsys.app.database;
    exports de.dbsys.app.database.entities;
}