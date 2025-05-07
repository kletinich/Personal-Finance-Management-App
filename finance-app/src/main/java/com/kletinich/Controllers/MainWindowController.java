package com.kletinich.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

// controller of the main window
public class MainWindowController {
    @FXML private Button transactionsButton;
    @FXML private Button budgetsButton;
    @FXML private Button savingsButton;
    @FXML private Button statisticsButton;
    @FXML private Button exitButton;

    @FXML private StackPane windowsStackPane;

    @FXML private Pane transactionWindow;
    @FXML private Pane budgetWindow;
    @FXML private Pane savingWindow;
    @FXML private Pane statisticsWindow;

    @FXML
    public void initialize(){
        hideAllWindows();
        transactionWindow.setVisible(true);
    }

    private void hideAllWindows(){
        transactionWindow.setVisible(false);
        budgetWindow.setVisible(false);
        savingWindow.setVisible(false);
        statisticsWindow.setVisible(false);
    }

    @FXML
    public void switchToTransactions(){
        hideAllWindows();
        transactionWindow.setVisible(true);
    }

    @FXML
    public void switchToBudgets(){
        hideAllWindows();
        budgetWindow.setVisible(true);
    }

    @FXML
    public void switchToSavings(){
        hideAllWindows();
        savingWindow.setVisible(true);
    }

    @FXML
    public void switchToStatistics(){
        hideAllWindows();
        statisticsWindow.setVisible(true);
    }
    
    @FXML
    public void exitApp(){
        System.exit(0);
    }
}
