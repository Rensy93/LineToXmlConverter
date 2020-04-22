module org.flomen {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.flomen to javafx.fxml;
    exports org.flomen;
}