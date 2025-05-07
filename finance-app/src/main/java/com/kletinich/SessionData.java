package com.kletinich;

import java.util.List;

import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;

// a class that stores data of the session that can be passed between controllers
public class SessionData {
    private static List<Transaction> transactionsList = null;
    private static List<Category> categoriesList = null;

    private static double totalBalance = 0;

    public static void setTransactions(List<Transaction> transactions){
        transactionsList = transactions;
    }

    public static List<Transaction> getTransactionsList(){
        return transactionsList;
    }

    public static void deleteTransaction(Transaction transaction){
        for(Transaction t: transactionsList){
            if(t == transaction){
                transactionsList.remove(transaction);
                System.out.println(transaction + " deleted");
                break;
            }
        }
    }

    public static void setCategoriesList(List<Category> categories){
        categoriesList = categories;
    }

    public static List<Category> getCategoriesList(){
        return categoriesList;
    }

    public static void setTotalBalance(double newBalance){
        totalBalance = newBalance;
    }

    public static double getTotalBalance(){
        return totalBalance;
    }
}
