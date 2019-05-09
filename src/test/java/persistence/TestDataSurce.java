package persistence;

import io.vincentpalazzo.gradledatabase.persistence.DataSource;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class TestDataSurce {

    DataSource dataSurce;
    String username = "postgres";
    String password = "vincenzo";
    String url = "jdbc:postgresql://192.168.1.80/";
    String nameDatabase = "testgradleplugin";

    @Before
    public void initData(){
        dataSurce = new DataSource();
    }

    @Test
    public void testConnectionWithDriver(){
        String driverPackage = "org.postgresql.Driver";

        boolean result = dataSurce.connectionDatabase(driverPackage, url, username, password);
        TestCase.assertTrue(result);
    }

    @Test
    public void testCreateDatabase(){
        String driverPackage = "org.postgresql.Driver";

        boolean result = dataSurce.connectionDatabase(driverPackage, url, username, password);
        TestCase.assertTrue(result);
        result =  dataSurce.createDatabese(nameDatabase);
        TestCase.assertTrue(result);
    }

    @Test
    public void testDeleteDatabase(){
        String driverPackage = "org.postgresql.Driver";

        boolean result = dataSurce.connectionDatabase(driverPackage, url, username, password);
        TestCase.assertTrue(result);
        result =  dataSurce.deleteDatabese(nameDatabase);
        TestCase.assertTrue(result);
    }

    @Test
    public void testCACreateIntoDatabaseWithFileSqlOne(){
        String driverPackage = "org.postgresql.Driver";

        String urlPostDbCreated;
        urlPostDbCreated = String.copyValueOf(url.toCharArray());
        urlPostDbCreated = urlPostDbCreated + nameDatabase;
        System.out.println("url: " + urlPostDbCreated);
        boolean result = dataSurce.connectionDatabase(driverPackage, urlPostDbCreated, username, password);
        TestCase.assertTrue(result);
        result = dataSurce.createTableWithFileSql(new File("/home/vincenzo/Github/gradle-database/fileTest/schema.sql"), true);
        TestCase.assertTrue(result);
    }
    @Test
    public void testCAInsertIntoDatabaseWithFileSqlOne(){
        String driverPackage = "org.postgresql.Driver";

        String urlPostDbCreated;
        urlPostDbCreated = String.copyValueOf(url.toCharArray());
        urlPostDbCreated = urlPostDbCreated + nameDatabase;
        System.out.println("url: " + urlPostDbCreated);
        boolean result = dataSurce.connectionDatabase(driverPackage, urlPostDbCreated, username, password);
        TestCase.assertTrue(result);
        result = dataSurce.createTableWithFileSql(new File("/home/vincenzo/Github/gradle-database/fileTest/insert.sql"), false);
        TestCase.assertTrue(result);
    }
}
