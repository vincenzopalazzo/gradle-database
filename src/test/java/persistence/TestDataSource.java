package persistence;

import io.vincentpalazzo.gradledatabase.persistence.DataSource;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class TestDataSource {

    DataSource dataSource;
    String username = "postgres";
    String password = "vincenzo";
    String url = "jdbc:postgresql://192.168.1.80/";
    String nameDatabase = "testgradleplugin";
    URLClassLoader classLoader;
    String pathJarDriver;

    @Before
    public void initData(){
        dataSource = new DataSource();
        pathJarDriver = System.getProperty("user.dir");
        System.out.println(pathJarDriver);
        File file = new File(pathJarDriver + "fileTest/postgresql-42.2.5.jre6.jar");
        try {
            classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectionWithDriver(){
        String driverPackage = "org.postgresql.Driver";

        boolean result = dataSource.connectionDatabase(classLoader, driverPackage, url, username, password);
        dataSource.closeConnectionDatabase();
        TestCase.assertTrue(result);
    }

    @Test
    public void testCreateDatabase(){
        String driverPackage = "org.postgresql.Driver";

        boolean result = dataSource.connectionDatabase(classLoader, driverPackage, url, username, password);
        TestCase.assertTrue(result);
        //Test
        result =  dataSource.createDatabese(nameDatabase);
        //delete database
        dataSource.deleteDatabese(nameDatabase);
        //close db
        dataSource.closeConnectionDatabase();
        TestCase.assertTrue(result);
    }

    @Test
    public void testDeleteDatabase(){
        String driverPackage = "org.postgresql.Driver";

        boolean result = dataSource.connectionDatabase(classLoader, driverPackage, url, username, password);
        TestCase.assertTrue(result);
        dataSource.createDatabese(nameDatabase);
        //Test
        result =  dataSource.deleteDatabese(nameDatabase);
        //Close db
        dataSource.closeConnectionDatabase();
        TestCase.assertTrue(result);
    }

    @Test
    public void testCACreateIntoDatabaseWithFileSqlOne(){
        String driverPackage = "org.postgresql.Driver";

        //Create db
        dataSource.connectionDatabase(classLoader, driverPackage, url, username, password);
        dataSource.createDatabese(nameDatabase);
        dataSource.closeConnectionDatabase();

        String urlPostDbCreated;
        urlPostDbCreated = String.copyValueOf(url.toCharArray());
        urlPostDbCreated = urlPostDbCreated + nameDatabase;
        System.out.println("url: " + urlPostDbCreated);
        boolean result = dataSource.connectionDatabase(classLoader, driverPackage, urlPostDbCreated, username, password);
        TestCase.assertTrue(result);

        //Test
        result = dataSource.createTableWithFileSql(new File("/home/vincenzo/Github/gradle-database/fileTest/schema.sql"), true);
        dataSource.closeConnectionDatabase();

        dataSource.connectionDatabase(classLoader, driverPackage, url, username, password);
        dataSource.deleteDatabese(nameDatabase);
        dataSource.closeConnectionDatabase();
        TestCase.assertTrue(result);
    }
    @Test
    public void testCAInsertIntoDatabaseWithFileSqlOne(){
        String driverPackage = "org.postgresql.Driver";

        //Create db
        dataSource.connectionDatabase(classLoader, driverPackage, url, username, password);
        dataSource.createDatabese(nameDatabase);
        dataSource.closeConnectionDatabase();

        String urlPostDbCreated;
        urlPostDbCreated = String.copyValueOf(url.toCharArray());
        urlPostDbCreated = urlPostDbCreated + nameDatabase;
        System.out.println("url: " + urlPostDbCreated);
        boolean result = dataSource.connectionDatabase(classLoader, driverPackage, urlPostDbCreated, username, password);
        TestCase.assertTrue(result);
        //Create Table
        dataSource.createTableWithFileSql(new File("/home/vincenzo/Github/gradle-database/fileTest/schema.sql"), true);
        //Test
        result = dataSource.insertIntoDatabaseFromFile(new File("/home/vincenzo/Github/gradle-database/fileTest/insert.sql"), false);
        dataSource.closeConnectionDatabase();

        dataSource.connectionDatabase(classLoader, driverPackage, url, username, password);
        dataSource.deleteDatabese(nameDatabase);
        dataSource.closeConnectionDatabase();

        TestCase.assertTrue(result);
    }
}
