package com.kletinich.Controllers;


import java.time.LocalDate;
import java.time.ZoneId;

import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdateTransactionWindowController {
    @FXML private Label idLabel;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> typeFilter;
    @FXML private TextField amountTextBox;
    @FXML private ComboBox<Category> categoryFilter;
    @FXML private TextArea noteTextBox;

    @FXML private Button updateButton;
    @FXML private Button cancelButton;

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
    }
}
