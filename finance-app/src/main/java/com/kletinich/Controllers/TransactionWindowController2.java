package com.kletinich.Controllers;

import java.sql.Date;
import java.util.List;

import com.kletinich.SessionData;
import com.kletinich.SessionData2;
import com.kletinich.database.CategoryDAO;
import com.kletinich.database.TransactionDAO;
import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;
import com.kletinich.database.tables.Transaction2;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionWindowController2 {
        @FXML private Label balanceLabel;

    @FXML private ComboBox<String> typeFilter;
    @FXML private ComboBox<Category> categoryFilter;
    @FXML private TextField amountTextField;
    @FXML private Button filterButton;
    @FXML private Button resetFilterButton;

    @FXML private TableView<Transaction2> transactionsTable;
    @FXML private TableColumn<Transaction2, String> typeColumn;
    @FXML private TableColumn<Transaction2, Double> amountColumn;
    @FXML private TableColumn<Transaction2, String> categoryColumn;
    @FXML private TableColumn<Transaction2, Date> dateColumn;
    @FXML private TableColumn<Transaction2, String> budgetSavingColumn;
    @FXML private TableColumn<Transaction2, String> noteColumn;
    @FXML private TableColumn<Transaction2, Button> deleteColumn;
    @FXML private TableColumn<Transaction2, Button> updateColumn;

    @FXML private Button newTransactionButton;

    private UpdateTransactionWindowController updateTransactionWindowController;

    @FXML
    public void initialize(){
        SessionData.setTotalBalance(0);

        transactionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Transaction2> transactionsList = TransactionDAO.getTransactions2(null, null, null);
        SessionData2.setTransactions(transactionsList);
        ObservableList<Transaction2> transactions = FXCollections.observableArrayList(transactionsList);

        initBalanceLabel(transactionsList);

        typeColumn.setCellValueFactory(cellData -> 
            new ReadOnlyStringWrapper(cellData.getValue().getType()));

        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        categoryColumn.setCellValueFactory(cellData -> {
            Category category = cellData.getValue().getCategory();
            String categoryName = (category != null) ? category.getName() : "";
            return new ReadOnlyStringWrapper(categoryName);
        });
         
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
                        Transaction2 t = getTableView().getItems().get(getIndex());
                        updateButtonPressed(t, getIndex());
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

        initFilterOptions();
    }

    // set the options for the filters
    public void initFilterOptions(){
        List<Category> categories = CategoryDAO.getCategories();
        SessionData2.setCategoriesList(categories);
        categoryFilter.setItems(FXCollections.observableList(categories));

        ObservableList<String> types = FXCollections.observableArrayList("income", "expense");
        typeFilter.setItems(types);
    }

    // when a delete button is pressed, delete the transaction associated with the index
    @FXML
    public void deleteButtonPressed(int index){
    }

    // when an update button is pressed, open new window for updating the transaction
    @FXML
    public void updateButtonPressed(Transaction2 transaction, int index){

    }







    // setting the balance label with the balance calculation of all transactions
    public void initBalanceLabel(List<Transaction2> transactionsList){
        double totalBalance = SessionData.getTotalBalance();

        for(Transaction2 t: transactionsList){
            if(t.getType().equals("income")){
                totalBalance += t.getAmount();
            }

            else{
                totalBalance -= t.getAmount();
            }
        }

        balanceLabel.setText("Total balance: " + String.valueOf(totalBalance));
        SessionData.setTotalBalance(totalBalance);
    }

}
