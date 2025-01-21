module com.example.idontknow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.idontknow to javafx.fxml;
    exports com.example.idontknow;
}