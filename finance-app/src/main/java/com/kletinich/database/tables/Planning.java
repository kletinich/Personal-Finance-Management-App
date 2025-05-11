package com.kletinich.database.tables;

// an abstract root class of Budget and Saving. 
// Budget and Saving share common fields
public abstract class Planning{
    protected Integer planningID;
    protected String name;
    protected Category category;
    protected double amount;
    protected double flow; // how much spent or saved currently
    protected double progress; 
    protected String note;

    public Planning(){
        this(null, null, null, 0, 0, null);
    }

    public Planning(Integer id, String name, Category category, double amount, double flow, String note){
        this.planningID = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.flow = flow;
        this.note = note;

        updateProgress();
    }

    public Integer getPlanningID() { return this.planningID; }
    public void setPlanningID(Integer id) { this.planningID = id; }

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

    public double getFlow() { return flow;}
    public void setFlow(double flow) { this.flow = flow; }

    public double getProgress() { return progress;}
    public void updateProgress() {
        progress = 0;

        if(this.amount > 0){
            this.progress = (flow / amount) * 100;
        }
    }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String toString(){
        return "(ID=" + this.planningID + 
                ", 'name=" + this.name + "'" + 
                ", category='" + this.category + "'" +
                ", amount=" + this.amount +
                ", flow=" + this.flow +
                ", progress=" + this.progress + 
                ", note='" + this.note + "')";
    }

    public abstract String getType();
}