module com.example.btl {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.btl to javafx.fxml;
    exports com.example.btl;
}