module com.example.folderpractice {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens Controller to javafx.fxml;
    exports Controller;
    exports model;
    opens model to javafx.fxml;
}