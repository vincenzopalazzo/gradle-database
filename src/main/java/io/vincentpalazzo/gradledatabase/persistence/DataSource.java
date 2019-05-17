package io.vincentpalazzo.gradledatabase.persistence;
/**
 * MIT License
 *
 * Copyright (c) 2019 Vincent Palazzo vincenzopalazzodev@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import io.vincentpalazzo.gradledatabase.exception.DataSurceException;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.sql.*;

/**
 * This class have need for the refactoring
 * @author https://github.com/vincenzopalazzo
 */
public class DataSource {

    private Connection connection;

    public boolean connectionDatabase(URLClassLoader classLoaderJar, String driverPackage, String url, String username, String password) throws DataSurceException {
        if((url == null || url.isEmpty()) || (username == null || username.isEmpty()) || (password == null || password.isEmpty())){
            //TODO create a exeption more specific
            throw new DataSurceException("Parameter function not valid the value is: url = " + url + " username = " + username + " password =  " + password);
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
            throw new DataSurceException("Exception generated is: " + e.getLocalizedMessage() + " \nat the url " +
                    url + " username " + username + " password " + password);
        }
    }

    public boolean createDatabese(String nameDatabase) throws DataSurceException {
        if(nameDatabase == null || nameDatabase.isEmpty()){
            //TODO create a exeption more specific
            throw new DataSurceException("Parameter function not valid");
        }

        String query = "CREATE DATABASE " + nameDatabase;

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataSurceException("Exception generate is: " + e.getLocalizedMessage());
        }
    }

    public boolean deleteDatabese(String nameDatabase) throws DataSurceException {
        if(nameDatabase == null || nameDatabase.isEmpty()){
            //TODO create a exeption more specific
            throw new DataSurceException("Parameter function not valid");
        }

        String query = "DROP DATABASE " + nameDatabase;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataSurceException("Exception generate is: " + e.getLocalizedMessage());
        }
    }

    public boolean createTableWithFileSql(File fileSql, boolean continueWithError) throws DataSurceException {
        if(fileSql == null || !fileSql.exists()){
            //TODO improve error
            throw new DataSurceException("Input argument not valid");
        }
        try {
            SqlFile sqlFile = new SqlFile(fileSql);
            sqlFile.setConnection(connection);
            sqlFile.setContinueOnError(continueWithError);
            sqlFile.setAutoClose(true);
            sqlFile.execute();
            return true;
        } catch (IOException | SqlToolError | SQLException e) {
            throw new DataSurceException("Exception generate is: " + e.getLocalizedMessage());
        }
    }

    public boolean insertIntoDatabaseFromFile(File fileSql, boolean continueWithError) throws DataSurceException {
        return createTableWithFileSql(fileSql, continueWithError);
    }

    public boolean closeConnectionDatabase() throws DataSurceException {
        try {
            if(!connection.isClosed()){
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            throw new DataSurceException("Exception generate is: " + e.getLocalizedMessage());
        }
        return false;
    }

    private boolean registerDriver(URLClassLoader classLoaderJar, String driverPackage) throws DataSurceException {
        if(driverPackage == null || driverPackage.isEmpty()){
            //TODO create a exeption more specific
            throw new DataSurceException("Parameter function not valid the value is driverPackage = " + driverPackage);
        }

        try {
            Driver driver = (Driver) Class.forName(driverPackage, true, classLoaderJar).newInstance();
            DriverManager.registerDriver(new DriverGradle(driver));
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            //TODO create a exeption more specific
            throw new DataSurceException("Exception generated is: " + e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new DataSurceException("Exception generated is: " + e.getLocalizedMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new DataSurceException("Exception generated is: " + e.getLocalizedMessage());
        }
    }
}
