package com.kletinich.database.tables;

import java.sql.Date;

public abstract class Transaction2 {
    protected Integer transactionID;
    protected double amount;
    protected Category category;
    protected Date date;
    protected String note;

    public Transaction2(){
        this.transactionID = null;
        this.amount = 0;
        this.date = null;
        this.note = null;
    }

    public Transaction2(Integer transactionID, double amount, Category category, Date date, String note){
        this.transactionID = transactionID;
        this.amount = amount;
        this.date = date;
        this.note = note;

        this.category = new Category(category.getID(), category.getName());
    }

    public Integer getTransactionID() { return transactionID; }
    public void setTransactionID(Integer transactionID) { this.transactionID = transactionID; }

    public double getAmount() { return amount;}
    public void setAmount(double amount) { this.amount = amount; }

    public Category getCategory() { return this.category; }
    public void setCategory(Category category){
        if(this.category == null){
            this.category = new Category(category.getID(), category.getName());
        }

        else{
            this.category.setID(category.getID());
            this.category.setName(category.getName());
        }
    }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String toString(){
        return "(ID=" + this.transactionID + 
                ", amount=" + this.amount +
                ", category='" + this.category + "'" +
                ", date='" + this.date + "'" +
                ", note='" + this.note + "')";
    }

    // get the type from the derived classes - income or expense
    public abstract String getType();

    // is a planning (income can be part of saving, expense can be part of budget) - to be done in future versions
    public abstract boolean isPlanning();

}
