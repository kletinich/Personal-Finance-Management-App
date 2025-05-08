package com.kletinich.Controllers;

import java.sql.Date;

import com.kletinich.SessionData;
import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TransactionWindowController2 {
        @FXML private Label balanceLabel;

    @FXML private ComboBox<String> typeFilter;
    @FXML private ComboBox<Category> categoryFilter;
    @FXML private TextField amountTextField;
    @FXML private Button filterButton;
    @FXML private Button resetFilterButton;

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, Date> dateColumn;
    @FXML private TableColumn<Transaction, String> budgetSavingColumn;
    @FXML private TableColumn<Transaction, String> noteColumn;
    @FXML private TableColumn<Transaction, Button> deleteColumn;
    @FXML private TableColumn<Transaction, Button> updateColumn;

    @FXML private Button newTransactionButton;

    private UpdateTransactionWindowController updateTransactionWindowController;

    @FXML
    public void initialize(){
        SessionData.setTotalBalance(0);

        transactionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}
