package com.kletinich.database.tables;

// an abstract root class of Budget and Saving. 
// Budget and Saving share common fields
public abstract class Planning{
    private Integer id;
    private String name;
    private double amount;
    private Double progress; 

    public Planning(Integer id, String name, double amount, double progress){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.progress = progress;
    }

    public Planning(Integer id, String name, double amount){
        this(id, name, amount, 0);
    }

    public abstract String getType();


}