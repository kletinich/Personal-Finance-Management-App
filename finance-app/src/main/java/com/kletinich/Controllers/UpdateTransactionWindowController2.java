package com.kletinich.Controllers;

import java.time.LocalDate;

import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;
import com.kletinich.database.tables.Transaction2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateTransactionWindowController2 {
    @FXML private Label idLabel;
    @FXML private Label idTitleLabel;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> typeFilter;
    @FXML private TextField amountTextBox;
    @FXML private ComboBox<Category> categoryFilter;
    @FXML private TextArea noteTextBox;

    @FXML private Button updateButton;
    @FXML private Button cancelButton;

    private Transaction2 updatedTransaction;
    private static boolean newTransaction = true;

    // display the data of the current selected transaction
    public void setTransactionData(Transaction2 transaction, ComboBox<String> type, ComboBox<Category> categories){
        typeFilter.setItems(type.getItems());
        categoryFilter.setItems(categories.getItems());
        
        if(transaction == null){
            newTransaction = true;
            idLabel.setVisible(false);
            idTitleLabel.setVisible(false);

        }

        else{
            newTransaction = false;
            typeFilter.setValue(transaction.getType());

            idLabel.setVisible(true);
            idTitleLabel.setVisible(true);

            idLabel.setText(String.valueOf(transaction.getTransactionID()));
            
            if(transaction.getDate() != null){
                LocalDate localDate = transaction.getDate().toLocalDate();
                datePicker.setValue(localDate);
            }

            amountTextBox.setText(String.valueOf(transaction.getAmount()));

            String categoryName = transaction.getCategory().getName();
            for(Category category : categoryFilter.getItems()){
                if(category.getName().equals(categoryName)){
                    categoryFilter.setValue(category);
                    break;
                }
            }

            noteTextBox.setText(transaction.getNote());

            updatedTransaction = transaction;
        }
    }

    // close the window and return to the main transactions view window
    @FXML
    public void closeWindow(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // update the data with the new given info
    @FXML
    public void updateButtonPressed(){
        boolean validData = true;
        String amountString = amountTextBox.getText();
        Double amount;

        if(updatedTransaction == null){
            //updatedTransaction = new Transaction(null, amountString, 1, 1, amountString, null, null, null, "");
        }
    }

    public Transaction2 getUpdatedTransaction(){
        return updatedTransaction;
    }

    public void resetTransaction(){
        updatedTransaction = null;
    }
}
