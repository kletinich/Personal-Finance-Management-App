package com.kletinich.database.tables;

import java.sql.Date;


// Transaction table in the database
public class Transaction {
    private Integer transactionID;
    private String type;
    private double amount;
    private Integer categoryID;
    private String categoryName;
    private Date date;
    private Integer budgetID;
    private Integer savingID;
    private String note;

    public Transaction(Integer transactionID, String type, double amount, Integer categoryID, String categoryName, 
        Date date, Integer budgetID, Integer savingID, String note) throws IllegalArgumentException{
    
        if(categoryID == null){
            throw new IllegalArgumentException("catagoryID not initialized");
        }

        if(budgetID != null && savingID != null){
            throw new IllegalArgumentException("Both budget ID and saving ID cannot be initialized, pick one!");
        }

        this.transactionID = transactionID;
        this.type = type;
        this.amount = amount;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.date = date;
        this.budgetID = budgetID;
        this.savingID = savingID;
        this.note = note;
    }

    public int getTransactionID() { return transactionID; }
    public void setTransactionID(int transactionID) { this.transactionID = transactionID; } // do I need it?

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) throws IllegalArgumentException{ 
        if(amount <= 0){
            throw new IllegalArgumentException("Amount should be positive");
        }
        
        this.amount = amount; 
    }

    public Integer getCategoryID() { return categoryID; }
    public void setCategoryID(Integer categoryID) { this.categoryID = categoryID; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName;}

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

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

    public String toString(){
        return "Transaction(" +
            this.transactionID + ", " +
            this.type + ", " +
            this.amount + ", " +
            this.categoryName + ", " +
            this.date + ", " +
            this.budgetID + ", " +
            this.savingID + ", " +
            this.note  + ")";
    }
}
