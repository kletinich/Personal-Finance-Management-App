package com.kletinich.database.tables;

import java.sql.Date;

public class Income extends Transaction{
    // to do - add functions and variables for savings in future versions.

    public Income(){
        super();
    }

    public Income(Integer transactionID, double amount, Category category, Date date, String note){
        super(transactionID, amount, category, date, note);
    }

    @Override
    public String getType() {
        return "income";
    }

    @Override
    public boolean isPlanning() {
        // to do: when savings will be added - check if is part of planning
        return false;
    }

    @Override
    public String toString(){
        // to do: when savings will be added - add those savings to the printing of the class
        String data = "Income" + super.toString();
        data = data.substring(0, data.length() - 1);
        data += ", type=income)";

        return data;
    }

}
