package com.kletinich.Controllers;

import java.sql.Timestamp;
import java.util.List;

import com.kletinich.database.TransactionDAO;
import com.kletinich.database.tables.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionWindowController {

    @FXML private Label balanceLabel;

    @FXML private ComboBox<String> typeFilter;
    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField amountTextField;
    @FXML private Button filterButton;

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, Integer> idColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, Timestamp> dateColumn;
    @FXML private TableColumn<Transaction, String> budgetSavingColumn;
    @FXML private TableColumn<Transaction, String> noteColumn;
    @FXML private TableColumn<Transaction, Button> actionsColumn;

    @FXML private Button newTransactionButton;

    @FXML
    public void initialize(){
        transactionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Transaction> transactionsList = TransactionDAO.getTransactions(null, null, null);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionsList);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName")); 
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        actionsColumn.setCellFactory(param -> new TableCell<>(){
            private final Button actionButton = new Button("Delete");
        
        
            @Override
            protected void updateItem(Button btn, boolean isEmpty){
                super.updateItem(btn, isEmpty);

                if(isEmpty){
                    setGraphic(null);
                }

                else{
                    setGraphic(actionButton);
                }
            }
        }
        );


        transactionsTable.setItems(transactions);
    }
    
}
