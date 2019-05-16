package io.vincentpalazzo.gradledatabase.persistence;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.sql.*;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class DataSource {

    private Connection connection;

    public boolean connectionDatabase(URLClassLoader classLoaderJar, String driverPackage, String url, String username, String password){
        if((url == null || url.isEmpty()) || (username == null || username.isEmpty()) || (password == null || password.isEmpty())){
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Parameter function not valid the value is: url = " + url + " username = " + username + " password =  " + password);
        }

        if (!registerDriver(classLoaderJar, driverPackage)){
            System.out.println("Driver not register");
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            //TODO create a exeption more specific
            e.printStackTrace();
            throw new IllegalArgumentException("Exception generated is: " + e.getLocalizedMessage() + " \nat the url " +
                    url + " username " + username + " password " + password);
        }
    }

    public boolean createDatabese(String nameDatabase){
        if(nameDatabase == null || nameDatabase.isEmpty()){
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Parameter function not valid");
        }

        String query = "CREATE DATABASE " + nameDatabase;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
        }
    }

    public boolean deleteDatabese(String nameDatabase){
        if(nameDatabase == null || nameDatabase.isEmpty()){
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Parameter function not valid");
        }

        String query = "DROP DATABASE " + nameDatabase;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
        }
    }

    public boolean createTableWithFileSql(File fileSql, boolean continueWithError) {
        if(fileSql == null || !fileSql.exists()){
            //TODO improve error
            throw new IllegalArgumentException("Input argument not valid");
        }
        try {
            SqlFile sqlFile = new SqlFile(fileSql);
            sqlFile.setConnection(connection);
            sqlFile.setContinueOnError(continueWithError);
            sqlFile.setAutoClose(true);
            sqlFile.execute();
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
        } catch (SqlToolError sqlToolError) {
            throw new IllegalArgumentException("Exception generate is: " + sqlToolError.getLocalizedMessage());
        } catch (SQLException e) {
            throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
        }
    }

    public boolean insertIntoDatabaseFromFile(File fileSql, boolean continueWithError) {
        return createTableWithFileSql(fileSql, continueWithError);
    }

    public boolean closeConnectionDatabase(){
        try {
            if(!connection.isClosed()){
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
        }
        return false;
    }

    private boolean registerDriver(URLClassLoader classLoaderJar, String driverPackage) {
        if(driverPackage == null || driverPackage.isEmpty()){
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Parameter function not valid the value is driverPackage = " + driverPackage);
        }
        Driver driver;

        try {
            //driver = (Driver) ClassLoader.getSystemClassLoader().loadClass(driverPackage).newInstance();
            driver = (Driver) classLoaderJar.loadClass(driverPackage).newInstance();
            System.out.println("Driver: " + driver);
        } catch (InstantiationException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getMessage());
        } catch (IllegalAccessException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getMessage());
        }

        try {
            DriverManager.registerDriver(driver);
            System.out.println("Driver register");
            return true;
        } catch (SQLException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getLocalizedMessage());
        }
    }
}
