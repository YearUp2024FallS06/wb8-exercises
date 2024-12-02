package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args){

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.wb8demo2 <username> <password>");
            System.exit(1);
        }

        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];


        try {
            doDatabaseStuff(username, password);
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public static void doDatabaseStuff(String username, String password) throws SQLException {

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet results = null;

        try{

            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. open a connection to the database
            // use the database URL to point to the correct database


            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", username, password);

            int country_id = 103;

            pStatement = connection.prepareStatement("SELECT * FROM sakila.city WHERE country_id = ?;");
            pStatement.setInt(1, country_id);


            results = pStatement.executeQuery();
            // process the results
            while (results.next()) {
                int cityId = results.getInt("city_id");
                String city = results.getString("city");
                System.out.println(cityId);
                System.out.println(city);

            }


        } catch (ClassNotFoundException e) {
            System.out.println("There was an issue finding a class:");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println("There was an SQL issue:");
            e.printStackTrace();
        }
        finally {

            if(results != null) { results.close();}
            if(pStatement != null) {pStatement.close();}
            if(connection != null) { connection.close();}


        }


    }

}