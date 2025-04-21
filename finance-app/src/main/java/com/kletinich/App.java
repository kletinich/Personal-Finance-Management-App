package com.kletinich;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Timestamp;

import com.kletinich.database.CategoryDAO;
import com.kletinich.database.TransactionDAO;
import com.kletinich.database.tables.Transaction;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainWindow"), 840, 500); 
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/kletinich/fxml/MainWindow.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        //Transaction t = new Transaction(0, "Income", 50, 5, "", null, null, null, STYLESHEET_CASPIAN);
        //TransactionDAO.insertTransaction(t);
        launch();
    }

}