module com.example.ds {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;


    opens com.example.ds to javafx.fxml;
    exports com.example.ds;
}