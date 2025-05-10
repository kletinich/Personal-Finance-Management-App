package com.kletinich.Controllers;

import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
}
