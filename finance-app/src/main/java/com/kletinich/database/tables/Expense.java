package com.kletinich.database.tables;

import java.sql.Date;

public class Expense extends Transaction{
    // to do - add functions and variables for budgets in future versions.

    public Expense(){
        super();
    }

    public Expense(Integer transactionID, double amount, Category category, Date date, String note){
        super(transactionID, amount, category, date, note);
    }

    @Override
    public String getType() {
        return "expense";
    }

    @Override
    public boolean isPlanning() {
        // to do: when budgets will be added - check if is part of planning
        return false;
    }

    @Override
    public String toString(){
        // to do: when budgets will be added - add those budgets to the printing of the class
        String data = "Expense" + super.toString();
        data = data.substring(0, data.length() - 1);
        data += ", type=expense)";

        return data;
    }
}
