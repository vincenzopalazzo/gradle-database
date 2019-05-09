package io.vincentpalazzo.gradledatabase.persistence;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class DataSource {

    private Connection connection;

    public boolean connectionDatabase(String driverPackage, String url, String username, String password){
        if((url == null || url.isEmpty()) ||
                (username == null || username.isEmpty()) ||
                (password == null || password.isEmpty())){
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Parameter function not valid");
        }

        registerDriver(driverPackage);

        try {
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getLocalizedMessage());
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
            connection.close();
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
            connection.close();
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
            sqlFile.execute();
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
        } catch (SqlToolError sqlToolError) {
            throw new IllegalArgumentException("Exception generate is: " + sqlToolError.getLocalizedMessage());
        } catch (SQLException e) {
            throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new IllegalArgumentException("Exception generate is: " + e.getLocalizedMessage());
            }
        }
    }

    public boolean insertIntoDatabaseFromFile(File fileSql, boolean continueWithError) {
        return createTableWithFileSql(fileSql, continueWithError);
    }

    private boolean registerDriver(String driverPackage) {
        if(driverPackage == null || driverPackage.isEmpty()){
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Parameter function not valid");
        }
        Driver driver = null;

        try {
            driver = (Driver) ClassLoader.getSystemClassLoader().loadClass(driverPackage).newInstance();
        } catch (InstantiationException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getLocalizedMessage());
        }

        try {
            DriverManager.registerDriver(driver);
            return true;
        } catch (SQLException e) {
            //TODO create a exeption more specific
            throw new IllegalArgumentException("Exception generated is: " + e.getLocalizedMessage());
        }
    }
}
