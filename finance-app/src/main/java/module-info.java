module com.kletinich {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kletinich to javafx.fxml;
    exports com.kletinich;
}
