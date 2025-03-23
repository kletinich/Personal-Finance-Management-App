package com.kletinich.database;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kletinich.database.tables.Transaction;

// Data Access Object class for accessing and using queries on transactions table
public abstract class TransactionDAO {
    private static Connection connection = null;

    // get a transaction by a given id
    public static Transaction getTransactionByID(int transactionID){
        connection = DatabaseConnector.connect();
        Transaction transaction = null;

        // connected successfully
        if(connection != null){
            String query = "SELECT * FROM transactions " +
                "WHERE transaction_id = ?";

            try{
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, transactionID);

                ResultSet result = statement.executeQuery();

                if(result.next()){
                    Integer budgetID = result.getInt("budget_id");
                    if(result.wasNull()){
                        budgetID = null;
                    }

                    Integer savingID = result.getInt("saving_id");
                    if(result.wasNull()){
                        savingID = null;
                    }

                    transaction = new Transaction(transactionID, 
                        result.getString("type"), 
                        result.getDouble("amount"), 
                        result.getInt("category_id"), 
                        result.getTimestamp("date"), 
                        budgetID, 
                        savingID, 
                        result.getString("note"));
                }

                else{
                    System.out.println("No transaction with id " + transactionID + " was found in transactions!");
                }

                DatabaseConnector.Disconnect();

            }catch(SQLException e){
                System.err.println("Error while executing GET from transactions!");
            }
        }

        return transaction;
    }

    // get all transaction that satisfy a given parameters in transaction object
    // get the transactions by tpye, amount and category id
    public static List<Transaction> getTransactions(String type, Double amount, Integer categoryID){
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = DatabaseConnector.connect();

        // connected successfully
        if(connection != null){
            String query = "SELECT * FROM transactions ";

            int count = 0;

            // add optional where/and keywords to query
            if(type != null || amount != null || categoryID != null){
                query += "WHERE ";

                if(type != null){
                    query += "type = ? ";
                    count++;
                }

                if(amount != null){
                    if(count > 0){
                        query += "AND ";
                    }

                    query += "amount = ? "; // to do: add option to <>, not just =
                    count++;
                }

                if(categoryID != null){
                    if(count > 0){
                        query += "AND ";
                    }

                    query += "category_id = ?"; 
                }
            }

            try{
                PreparedStatement statement = connection.prepareStatement(query);
                int index = 1;

                if(type != null){
                    statement.setString(index, type);
                    index++;
                }

                if(amount != null){
                    statement.setDouble(index, amount);
                    index++;
                }

                if(categoryID != null){
                    statement.setInt(index, categoryID);
                }

                ResultSet result = statement.executeQuery();

                while(result.next()){
                    Integer budgetID = result.getInt("budget_id");
                    if(result.wasNull()){
                        budgetID = null;
                    }
    
                    Integer savingID = result.getInt("saving_id");
                    if(result.wasNull()){
                        savingID = null;
                    }
    
                    Transaction transaction = new Transaction(result.getInt("transaction_id"), 
                        result.getString("type"), 
                        result.getDouble("amount"), 
                        result.getInt("category_id"), 
                        result.getTimestamp("date"), 
                        budgetID, 
                        savingID, 
                        result.getString("note"));

                    transactions.add(transaction);
                }   

                DatabaseConnector.Disconnect();
                
                }catch(SQLException e){
                    System.err.println("Error while executing GET from transactions!");
                
            }
        }

        return transactions;
        
    }

    // insert a new transaction
    public static void insertTransaction(Transaction transaction){
        connection = DatabaseConnector.connect();

        // connected successfully
        if(connection != null){
            String query = "INSERT INTO transactions " +
                "(type, amount, category_id, date, budget_id, saving_id, note)" +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, transaction.getType());
                statement.setDouble(2, transaction.getAmount());
                statement.setInt(3, transaction.getCategoryID());
                statement.setTimestamp(4, transaction.getDate());
                statement.setString(7, transaction.getNote());

                if(transaction.getBudgetID() == null){
                    statement.setNull(5, java.sql.Types.INTEGER);
                }

                else{
                    statement.setInt(5, transaction.getBudgetID()); 
                }

                if(transaction.getSavingID() == null){
                    statement.setNull(6, java.sql.Types.INTEGER);
                }

                else{
                    statement.setInt(6, transaction.getSavingID());
                }

                int affectedRows = statement.executeUpdate();

                if(affectedRows > 0){
                    System.out.println("Transaction inserted successfully!");
                }

                else{
                    System.out.println("Transaction couldn't be inserted!");
                }

            } catch (SQLException e) {
                System.err.println("Error while executing INSERT into transactions!");
            }

            DatabaseConnector.Disconnect();
        }
    }

    // delete a transaction by given transaction id
    public static void deleteTransactionByID(int transactionID){
        connection = DatabaseConnector.connect();

        // connected successfully
        if(connection != null){
            String query = "DELETE FROM transactions WHERE " +
                "transaction_id = ?";

            try{
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, transactionID);

                int affectedRows = statement.executeUpdate();

                if(affectedRows > 0){
                    System.out.println("Transaction deleted successfully!");
                }

                else{
                    System.err.println("Transaction couldn't be deleted!"); 
                }

            }catch (SQLException e) {
                System.err.println("Error while executing DELETE from transactions!");
            }

            DatabaseConnector.Disconnect();
        }
    }

    // update a transaction by a given updated transaction
    public static void  updateTransaction(Transaction transaction){
        Connection connection = DatabaseConnector.connect();

        if(connection != null){
            String query = "UPDATE transactions SET " +
                "type = ?, amount = ?, category_id = ?, date = ?, budget_id = ?, saving_id = ?, note = ?" +
                " WHERE transaction_id = ?";

            try{
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, transaction.getType());
                statement.setDouble(2, transaction.getAmount());
                statement.setInt(3, transaction.getCategoryID());
                statement.setTimestamp(4, transaction.getDate());
                statement.setString(7, transaction.getNote());

                if(transaction.getBudgetID() == null){
                    statement.setNull(5, java.sql.Types.INTEGER);
                }
    
                else{
                    statement.setInt(5, transaction.getBudgetID()); 
                }
    
                if(transaction.getSavingID() == null){
                    statement.setNull(6, java.sql.Types.INTEGER);
                }
    
                else{
                    statement.setInt(6, transaction.getSavingID());
                }

                statement.setInt(8, transaction.getTransactionID());

                int affectedRows = statement.executeUpdate();

                if(affectedRows > 0){
                    System.out.println("Transaction updated successfully!");
                }
    
                else{
                    System.err.println("Transaction couldn't be updated!"); 
                }
                    
            }catch(SQLException e){
                    System.err.println("Error while executing UPDATE in transactions!");
            }

            DatabaseConnector.Disconnect();
        }
    }
}
