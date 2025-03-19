package com.kletinich.database.tables;

import java.sql.Timestamp;

// Transaction table in the database
public class Transaction {
    private int transactionID;
    private String type;
    private double amount;
    private Integer categoryID;
    private Timestamp date;
    private Integer budgetID;
    private Integer savingID;
    private String note;

    public Transaction(int transactionID, String type, double amount, Integer categoryID, 
        Timestamp date, Integer budgetID, Integer savingID, String note) throws IllegalArgumentException{
    
        if(categoryID == null){
            throw new IllegalArgumentException("catagoryID not initialized");
        }

        if(budgetID != null && savingID != null){
            throw new IllegalArgumentException("Both budgetID and savingID cannot be initialized");
        }

        this.transactionID = transactionID;
        this.type = type;
        this.amount = amount;
        this.categoryID = categoryID;
        this.date = date;
        this.budgetID = budgetID;
        this.savingID = savingID;
        this.note = note;
    }

    public int getTransactionID() { return transactionID; }
    public void setTransactionID(int transactionID) { this.transactionID = transactionID; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Integer getCategoryID() { return categoryID; }
    public void setCategoryID(Integer catagoryID) { this.categoryID = catagoryID; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }

    public Integer getBudgetID() { return budgetID; }
    public void setBudgetID(Integer budgetID) throws IllegalArgumentException{
        if(this.savingID != null){
            throw new IllegalArgumentException("Transaction is a saving!");
        }

        this.budgetID = budgetID; 
    }

    public Integer getSavingID() { return savingID; }
    public void setSavingID(Integer savingID) { 
        if(this.budgetID != null){
            throw new IllegalArgumentException("Transaction is a budget!");
        }

        this.savingID = savingID; 
    }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public boolean isBudget() { return this.budgetID != null; }
    public boolean isSaving(){ return this.savingID != null; }


}
