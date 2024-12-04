package com.pluralsight;

import com.pluralsight.models.*;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        String lastName = Console.PromptForString("What is the last name of an actor you like? ");

        try(BasicDataSource dataSource = getConfiguredDataSource(args);){

            SakilaDataManager dm = new SakilaDataManager(dataSource);

            List<Actor> actors = dm.actorsByLastName(lastName);
            if(!actors.isEmpty()){
                for(Actor a : actors){
                    System.out.println(a);
                }

                int actorId = Console.PromptForInt("Please enter an actor ID: ");

                List<Film> films = dm.filmsByActorId(actorId);

                for(Film f : films){
                    System.out.println(f);
                }

            }
            else
            {
                System.out.println("No actors found.");
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static BasicDataSource getConfiguredDataSource(String[] args){
        String username = args[0];
        String password = args[1];
        String sqlServerUrl = args[2];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(sqlServerUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;


    }
}