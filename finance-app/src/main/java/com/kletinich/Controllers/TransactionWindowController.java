package com.kletinich.Controllers;

import java.sql.Date;
import java.util.List;

import com.kletinich.SessionData;
import com.kletinich.database.CategoryDAO;
import com.kletinich.database.TransactionDAO;
import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TransactionWindowController {

    @FXML private Label balanceLabel;

    @FXML private ComboBox<String> typeFilter;
    @FXML private ComboBox<Category> categoryFilter;
    @FXML private TextField amountTextField;
    @FXML private Button filterButton;
    @FXML private Button resetFilterButton;

    @FXML private TableView<Transaction> transactionsTable;
    //@FXML private TableColumn<Transaction, Integer> idColumn;
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

        List<Transaction> transactionsList = TransactionDAO.getTransactions(null, null, null);
        SessionData.setTransactions(transactionsList);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionsList);

        initBalanceLabel(transactionsList);

        //idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
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
                        Transaction t = getTableView().getItems().get(getIndex());
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
        SessionData.setCategoriesList(categories);
        categoryFilter.setItems(FXCollections.observableList(categories));

        ObservableList<String> types = FXCollections.observableArrayList("income", "expense");
        typeFilter.setItems(types);
    }

    // when a delete button is pressed, delete the transaction associated with the index
    @FXML
    public void deleteButtonPressed(int index){
        double totalBalance = SessionData.getTotalBalance();

        if(index >= 0){
            Transaction transaction = deleteColumn.getTableView().getItems().get(index);
            System.out.println("Deleting " + transaction);
            TransactionDAO.deleteTransactionByID(transaction.getTransactionID());
            deleteColumn.getTableView().getItems().remove(transaction);

            // update total balance after update
            if(transaction.getType().equals("income")){
                totalBalance -= transaction.getAmount();
            }

            else{
                totalBalance += transaction.getAmount();
            }

            balanceLabel.setText("Total balance: " + String.valueOf(totalBalance));
            SessionData.deleteTransaction(transaction);
            SessionData.setTotalBalance(totalBalance);
        }
    }

    // when an update button is pressed, open new window for updating the transaction
    @FXML
    public void updateButtonPressed(Transaction transaction, int index){
        Double oldAmount;
        Double newAmount;
        String oldType;
        String newType;

        oldAmount = transaction.getAmount();
        oldType = transaction.getType();

        loadUpdateTransactionWindow(transaction);

        Transaction updatedTransaction = updateTransactionWindowController.getUpdatedTransaction();

        newAmount = updatedTransaction.getAmount();
        newType = updatedTransaction.getType();
    
        transactionsTable.getItems().set(index, updatedTransaction);
        transactionsTable.refresh();

        // update the balance label with the updated values of the updated transaction
        updateBalanceLabel(oldAmount, newAmount, oldType, newType);

    }

    // when an insert new transaction button pressed, open new window for inserting the transaction
    @FXML
    public void newTransactionButtonPressed(){
        loadUpdateTransactionWindow(null);

        Transaction newTransaction = updateTransactionWindowController.getUpdatedTransaction();
        if(newTransaction.getTransactionID() != -1){
            transactionsTable.getItems().add(newTransaction);
            transactionsTable.refresh();
        }

        updateBalanceLabel(null, newTransaction.getAmount(), null, newTransaction.getType());
    }

    // load and show the update transaction window for inserting new transaction or updating existing one
    public void loadUpdateTransactionWindow(Transaction transaction){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kletinich/fxml/transactions/UpdateTransactionWindow.fxml"));
            Parent root = loader.load();

            updateTransactionWindowController = loader.getController();
            updateTransactionWindowController.setTransactionData(transaction, typeFilter, categoryFilter);

            Stage updateTransactionStage = new Stage();
            updateTransactionStage.initModality(Modality.APPLICATION_MODAL);
            updateTransactionStage.setTitle("Update Transaction");
            updateTransactionStage.setScene(new Scene(root));
            updateTransactionStage.showAndWait();

        }catch(Exception e){
            System.out.println("Error while staging UpdateTransactionWindow!");
        }
    }

    // filter the table rows with relevant filters
    @FXML
    public void filterButtonPressed(){
        String type = typeFilter.getValue();
        Integer categoryID = null;
        Double amount = null;

        amountTextField.setStyle("");

        Category selectedCategory = categoryFilter.getValue();

        if(selectedCategory != null){
            categoryID = selectedCategory.getID();
        }

        String amountString = amountTextField.getText();
        try{
            if(amountString != null && !amountString.trim().isEmpty()){
                amount = Double.parseDouble(amountString);
            }

        }catch(NumberFormatException e){
            amountTextField.setText("Not a valid value!");
            amountTextField.setStyle("-fx-background-color: red;");
            return;
        }

        List<Transaction> filteredTransactions = TransactionDAO.getTransactions(type, amount, categoryID);
        transactionsTable.setItems(FXCollections.observableArrayList(filteredTransactions));
        
    }

    // reset the filter
    @FXML
    public void resetFilterButtonPressed(){
        List<Transaction> transactions = SessionData.getTransactionsList();
        transactionsTable.setItems(FXCollections.observableArrayList(transactions));
        
        typeFilter.getSelectionModel().clearSelection();
        categoryFilter.getSelectionModel().clearSelection();
        amountTextField.clear();
        
        typeFilter.setPromptText("Transaction Type");
        categoryFilter.setPromptText("Category");
        amountTextField.setPromptText("Amount");
    
            
    }

    // reset the amount box for visual clearity
    public void resetAmountBox(){
        amountTextField.setStyle("");
        amountTextField.setPromptText("Amount");
        amountTextField.setText("");
    }

    // setting the balance label with the balance calculation of all transactions
    public void initBalanceLabel(List<Transaction> transactionsList){
        double totalBalance = SessionData.getTotalBalance();

        for(Transaction t: transactionsList){
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

    // updating the balance label of one transcation
    public void updateBalanceLabel(Double oldValue, Double newValue, String oldType, String newType){
        double totalBalance = SessionData.getTotalBalance();

        if(oldValue != null && oldType != null){
            if(oldType.equals("income")){
                totalBalance -= oldValue;
            }

            else{
                totalBalance += oldValue;
            }
        }

        if(newType.equals("income")){
            totalBalance += newValue;
        }

        else{
            totalBalance -= newValue;
        }

        balanceLabel.setText("Total balance: " + String.valueOf(totalBalance));
        SessionData.setTotalBalance(totalBalance);
    }
 
}
