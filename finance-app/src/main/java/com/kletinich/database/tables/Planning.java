package com.kletinich.database.tables;

// an abstract root class of Budget and Saving. 
// Budget and Saving share common fields
public abstract class Planning{
    private Integer budgetID;
    private String name;
    private Category category;
    private double amount;
    private double spent;
    private Double progress; 
    private String note;

    public Planning(){
        this(null, "", null, 0, 0, "");
    }

    public Planning(Integer id, String name, Category category, double amount, double spent, String note){
        this.budgetID = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.spent = spent;
        this.note = note;

        updateProgress();
    }

    public Integer getBudgetID() { return this.budgetID; }
    public void setBudgetID(Integer id) { this.budgetID = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name;}

    public Category getCategory() { return this.category; }
    public void setCategory() {
        if(this.category == null){
            this.category = new Category(category.getID(), category.getName());
        }

        else{
            this.category.setID(category.getID());
            this.category.setName(category.getName());
        }
    }

    public double getAmount() { return amount;}
    public void setAmount(double amount) { this.amount = amount; }

    public double getSpent() { return spent;}
    public void setspent(double spent) { this.spent = spent; }

    public double getProgress() { return progress;}
    public void updateProgress() {
        if(this.amount > 0){
            this.progress = (spent / amount) * 100;
        }
    }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String toString(){
        return "(ID=" + this.budgetID + 
                ", name=" + this.name + 
                ", category='" + this.category + "'" +
                ", amount=" + this.amount +
                ", spent=" + this.spent +
                ", progress=" + this.progress + 
                ", note='" + this.note + "')";
    }

    public abstract String getType();
}