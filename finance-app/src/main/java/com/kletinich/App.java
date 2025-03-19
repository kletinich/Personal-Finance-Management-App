package com.kletinich;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;

import com.kletinich.database.DatabaseConnector;
import com.kletinich.database.TransactionDAO;
import com.kletinich.database.tables.Transaction;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainWindow"), 640, 480); 
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        //launch();

        //Transaction transaction = new Transaction(0, "Income", 50, 1, new Timestamp(System.currentTimeMillis()), null, null, "Testing income");
        //TransactionDAO.insertTransaction(transaction);
        TransactionDAO.deleteTransactionByID(1);
    }

}