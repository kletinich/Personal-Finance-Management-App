module com.kletinich {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires transitive javafx.graphics;

    opens com.kletinich.database to javafx.fxml;
    opens com.kletinich to javafx.fxml;
    opens com.kletinich.Controllers to javafx.fxml;
    opens com.kletinich.database.tables to javafx.base;
    exports com.kletinich;
    exports com.kletinich.database.tables;
}
