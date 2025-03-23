package com.kletinich.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

// controller of the main window
public class MainWindowController {
    @FXML private Button transactionsButton;
    @FXML private Button budgetsButton;
    @FXML private Button savingsButton;
    @FXML private Button statisticsButton;
    @FXML private Button exitButton;

    @FXML private StackPane windowsStackPane;

    @FXML
    public void exitApp(){
        System.exit(0);
    }
}
