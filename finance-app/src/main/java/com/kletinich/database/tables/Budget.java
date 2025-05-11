package com.kletinich.database.tables;

public class Budget extends Planning{

    public Budget(){
        super();
    }

    public Budget(Integer id, String name, Category category, double amount, double spent, String note){
        super(id, name, category, amount, spent, note);
    }

    @Override
    public String getType() {
        return "budget";
    }

    @Override
    public String toString(){
        return "Budget" + super.toString();
    }

}
