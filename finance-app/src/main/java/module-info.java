module com.kletinich {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.kletinich to javafx.fxml;
    opens com.kletinich.Controllers to javafx.fxml;
    exports com.kletinich;
}
