module com.kletinich {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.kletinich to javafx.fxml;
    exports com.kletinich;
}
