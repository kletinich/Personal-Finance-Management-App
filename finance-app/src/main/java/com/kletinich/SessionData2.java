package com.kletinich;

import java.util.List;

import com.kletinich.database.tables.Category;
import com.kletinich.database.tables.Transaction;
import com.kletinich.database.tables.Transaction2;

// a class that stores data of the session that can be passed between controllers
public class SessionData2 {
    private static List<Transaction2> transactionsList = null;
    private static List<Category> categoriesList = null;

    private static double totalBalance = 0;

    public static void setTransactions(List<Transaction2> transactions){
        transactionsList = transactions;
    }

    public static List<Transaction2> getTransactionsList(){
        return transactionsList;
    }

    public static void deleteTransaction(Transaction2 transaction){
        for(Transaction2 t: transactionsList){
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
