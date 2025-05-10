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
    @FXML private TableColumn<Transaction2, String> noteColumn;
    @FXML private TableColumn<Transaction2, Button> deleteColumn;
    @FXML private TableColumn<Transaction2, Button> updateColumn;

    @FXML private Button newTransactionButton;

    private UpdateTransactionWindowController2 updateTransactionWindowController;

    @FXML
    public void initialize(){
        SessionData.setTotalBalance(0);

        transactionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<Transaction2> transactionsList = TransactionDAO.getTransactions(null, null, null);
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
        double totalBalance = SessionData2.getTotalBalance();

        if(index >= 0){
            Transaction2 transaction = deleteColumn.getTableView().getItems().get(index);
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
            SessionData2.deleteTransaction(transaction);
            SessionData2.setTotalBalance(totalBalance);
        }
    }

    // when an update button is pressed, open new window for updating the transaction
    @FXML
    public void updateButtonPressed(Transaction2 transaction, int index){
        Double oldAmount;
        Double newAmount;
        String oldType;
        String newType;

        oldAmount = transaction.getAmount();
        oldType = transaction.getType();

        loadUpdateTransactionWindow(transaction);

        Transaction2 updatedTransaction = updateTransactionWindowController.getUpdatedTransaction();

        newAmount = updatedTransaction.getAmount();
        newType = updatedTransaction.getType();
    
        transactionsTable.getItems().set(index, updatedTransaction);
        transactionsTable.refresh();

        // update the balance label with the updated values of the updated transaction
        updateBalanceLabel(oldAmount, newAmount, oldType, newType);
    }


    @FXML
    public void newTransactionButtonPressed(){
        loadUpdateTransactionWindow(null);

        Transaction2 newTransaction = updateTransactionWindowController.getUpdatedTransaction();

        if(newTransaction != null && newTransaction.getTransactionID() != -1){
            transactionsTable.getItems().add(newTransaction);
            transactionsTable.refresh();

            updateBalanceLabel(null, newTransaction.getAmount(), null, newTransaction.getType());
        }
    }

    public void loadUpdateTransactionWindow(Transaction2 transaction){
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

    @FXML
    public void filterButtonPressed(){
        System.out.println("filter");
    }

    @FXML
    public void resetFilterButtonPressed(){
        System.out.println("reset filter");
    }

    // reset the amount box for visual clearity
    public void resetAmountBox(){
        amountTextField.setStyle("");
        amountTextField.setPromptText("Amount");
        amountTextField.setText("");
    }

    // setting the balance label with the balance calculation of all transactions
    public void initBalanceLabel(List<Transaction2> transactionsList){
        double totalBalance = SessionData2.getTotalBalance();

        for(Transaction2 t: transactionsList){
            if(t.getType().equals("income")){
                totalBalance += t.getAmount();
            }

            else{
                totalBalance -= t.getAmount();
            }
        }

        balanceLabel.setText("Total balance: " + String.valueOf(totalBalance));
        SessionData2.setTotalBalance(totalBalance);
    }

    // updating the balance label of one transcation
    public void updateBalanceLabel(Double oldValue, Double newValue, String oldType, String newType){
        double totalBalance = SessionData2.getTotalBalance();

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
        SessionData2.setTotalBalance(totalBalance);
    }

}
