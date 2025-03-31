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
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;

public class TransactionWindowController {

    private double totalBalance;

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
    @FXML private TableColumn<Transaction, Button> deleteColumn;
    @FXML private TableColumn<Transaction, Button> updateColumn;

    @FXML private Button newTransactionButton;

    @FXML
    public void initialize(){
        totalBalance = 0;

        transactionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Transaction> transactionsList = TransactionDAO.getTransactions(null, null, null);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionsList);

        for(Transaction t: transactionsList){
            totalBalance += t.getAmount();
        }

        balanceLabel.setText("Total balance: " + String.valueOf(totalBalance));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName")); 
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        // setting buttons in the deleteColumn for dynamic deletion
        deleteColumn.setCellFactory(param -> new TableCell<>(){
            private final Button actionButton = new Button("Delete");

            {
                actionButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deleteButtonPressed(getIndex());
                    }
                });
            }
        
        
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

        // setting buttons in the updateColumn for dynamic update
        updateColumn.setCellFactory(param -> new TableCell<>(){
            private final Button actionButton = new Button("Update");

            {
                actionButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateButtonPressed(getIndex());
                    }
                });
            }
        
        
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

    // when a delete button is pressed, delete the transaction associated with the index
    public void deleteButtonPressed(int index){
        if(index >= 0){
            Transaction transaction = deleteColumn.getTableView().getItems().get(index);
            System.out.println("Deleting " + transaction);
            TransactionDAO.deleteTransactionByID(transaction.getTransactionID());
            deleteColumn.getTableView().getItems().remove(transaction);

            totalBalance -= transaction.getAmount();
            balanceLabel.setText("Total balance: " + String.valueOf(totalBalance));
        }
    }

    // when an update button is pressed, update the transaction associated with the index
    public void updateButtonPressed(int index){
        System.out.println("Update");
    }
    
}
