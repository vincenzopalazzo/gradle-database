package persistence;

import io.vincentpalazzo.gradledatabase.persistence.DataSurce;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class TestDataSurce {

    DataSurce dataSurce;
    String username = "postgres";
    String password = "vincenzo";
    String url = "jdbc:postgresql://192.168.1.80/";

    @Before
    public void initData(){
        dataSurce = new DataSurce();
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
        result =  dataSurce.createDatabese("testPlugin");
        TestCase.assertTrue(result);
    }

    @Test
    public void testDeleteDatabase(){
        String driverPackage = "org.postgresql.Driver";

        boolean result = dataSurce.connectionDatabase(driverPackage, url, username, password);
        TestCase.assertTrue(result);
        result =  dataSurce.deleteDatabese("testPlugin");
        TestCase.assertTrue(result);
    }
}
