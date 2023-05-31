module de.dbsys.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens de.dbsys.app to javafx.fxml;
    exports de.dbsys.app;
}