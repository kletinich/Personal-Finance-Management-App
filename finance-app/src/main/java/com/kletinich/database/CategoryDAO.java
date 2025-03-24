package com.kletinich.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kletinich.database.tables.Category;

// Data Access Object class for accessing and using queries on categories table
public abstract class CategoryDAO {
    private static Connection connection = null;

    // get a category by a given id
    public static Category getCategoryByID(int categoryID){
        connection = DatabaseConnector.connect();
        Category category = null;

        //connected successfully
        if(connection != null){
            String query = "SELECT * FROM categories WHERE category_id = ?";

            try{
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, categoryID);

                ResultSet result = statement.executeQuery();

                if(result.next()){
                    category = new Category(categoryID, result.getString("name"));
                }

                else{
                    System.out.println("No category with id " + categoryID + " was found in catefories!");
                }

                DatabaseConnector.Disconnect();

            }catch(SQLException e){
                System.err.println("Error while executing GET from categories!");
            }
        }

        return category;
    }

    // get all the categories
    public static List<Category> getCategories(){
        connection = DatabaseConnector.connect();
        List<Category> categories = new ArrayList<>();

        // connected successfully
        if(connection != null){
            String query = "SELECT * FROM categories";

            try{
                PreparedStatement statement = connection.prepareStatement(query);

                ResultSet result = statement.executeQuery();

                while(result.next()){
                    Category category = new Category(result.getInt("category_id"),
                        result.getString("name"));

                    categories.add(category);
                }

                DatabaseConnector.Disconnect();

            }catch(SQLException e){
                System.err.println("Error while executing GET from categories!");
            }
        }

        return categories;
    }

}
