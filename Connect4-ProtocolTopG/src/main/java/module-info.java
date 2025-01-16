module com.example.connect4protocoltopg {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.connect4protocoltopg to javafx.fxml;
    exports com.example.connect4protocoltopg;
}