package com.kletinich.Controllers;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.kletinich.database.CategoryDAO;
import com.kletinich.database.TransactionDAO;
import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateTransactionWindowController {
    @FXML private Label idLabel;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> typeFilter;
    @FXML private TextField amountTextBox;
    @FXML private ComboBox<Category> categoryFilter;
    @FXML private TextArea noteTextBox;

    @FXML private Button updateButton;
    @FXML private Button cancelButton;

    private Transaction transaction;

    // display the data of the current selected transaction
    public void setTransactionData(Transaction transaction, ComboBox<String> type, ComboBox<Category> categories){
        idLabel.setText(String.valueOf(transaction.getTransactionID()));
        
        if(transaction.getDate() != null){
            LocalDate localDate = transaction.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePicker.setValue(localDate);
        }

        typeFilter.setItems(type.getItems());
        typeFilter.setValue(transaction.getType());

        amountTextBox.setText(String.valueOf(transaction.getAmount()));

        categoryFilter.setItems(categories.getItems());
        String categoryName = transaction.getCategoryName();
        for(Category category : categoryFilter.getItems()){
            if(category.getName().equals(categoryName)){
                categoryFilter.setValue(category);
                break;
            }
        }

        // to do: add budget/saving view to comboBox

        noteTextBox.setText(transaction.getNote());

        this.transaction = transaction;
    }

    public void cancelButtonPressed(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void updateButtonPressed(){
        if(datePicker.getValue() != null){
            LocalDate localDate = datePicker.getValue();

            LocalDateTime localDateTime = localDate.atStartOfDay();
            transaction.setDate(Timestamp.valueOf(localDateTime));
        }

        transaction.setType(typeFilter.getValue());
        String amountString = amountTextBox.getText();
        Double amount;

        try{
            if(amountString != null && !amountString.trim().isEmpty()){
                amount = Double.parseDouble(amountString);
                transaction.setAmount(amount);
                amountTextBox.setStyle("");
            }

        }catch(NumberFormatException e){
            amountTextBox.setText("Not a valid value!");
            amountTextBox.setStyle("-fx-background-color: red;");
        }


        transaction.setCategoryName(categoryFilter.getValue().toString());
        transaction.setCategoryID(CategoryDAO.getCategoryIDByName(transaction.getCategoryName()));
        transaction.setNote(noteTextBox.getText());

        // to do: add budget/saving

        TransactionDAO.updateTransaction(transaction);
    }
}
