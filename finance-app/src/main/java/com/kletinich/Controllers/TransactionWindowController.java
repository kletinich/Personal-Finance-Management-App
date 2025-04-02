package com.kletinich.Controllers;

import java.sql.Timestamp;
import java.util.List;

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

    private double totalBalance;

    @FXML private Label balanceLabel;

    @FXML private ComboBox<String> typeFilter;
    @FXML private ComboBox<Category> categoryFilter;
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
            if(t.getType().equals("income")){
                totalBalance += t.getAmount();
            }

            else{
                totalBalance -= t.getAmount();
            }
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
                        Transaction t = getTableView().getItems().get(getIndex());
                        updateButtonPressed(t);
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
        categoryFilter.setItems(FXCollections.observableList(categories));

        ObservableList<String> types = FXCollections.observableArrayList("income", "expense");
        typeFilter.setItems(types);
    }

    // when a delete button is pressed, delete the transaction associated with the index
    public void deleteButtonPressed(int index){
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
        }
    }

    // when an update button is pressed, open new window for updating the transaction
    public void updateButtonPressed(Transaction transaction){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kletinich/fxml/transactions/UpdateTransactionWindow.fxml"));
            Parent root = loader.load();

            UpdateTransactionWindowController controller = loader.getController();
            controller.setTransactionData(transaction, typeFilter, categoryFilter);

            Stage updateTransactionStage = new Stage();
            updateTransactionStage.initModality(Modality.APPLICATION_MODAL);
            updateTransactionStage.setTitle("Update Transaction");
            updateTransactionStage.setScene(new Scene(root));
            updateTransactionStage.showAndWait();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

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

        List<Transaction> transactions = TransactionDAO.getTransactions(type, amount, categoryID);
        transactionsTable.setItems(FXCollections.observableArrayList(transactions));
        
    }

    public void resetAmountBox(){
        amountTextField.setStyle("");
        amountTextField.setPromptText("Amount");
        amountTextField.setText("");
    }


    
}
