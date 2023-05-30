module de.dbsys.app {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens de.dbsys.app to javafx.fxml;
    exports de.dbsys.app;
}