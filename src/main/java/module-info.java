module com.example.spaceinvadersfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spaceinvadersfx to javafx.fxml;
    exports com.example.spaceinvadersfx;
}