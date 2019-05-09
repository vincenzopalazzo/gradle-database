package io.vincentpalazzo.gradledatabase.task;

import io.vincentpalazzo.gradledatabase.exstension.GradleDatabaseExstension;
import io.vincentpalazzo.gradledatabase.persistence.DataSource;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class GradleCrateTablesIntoDatabeseTask extends DefaultTask {

    @TaskAction
    public void createAction() {

        GradleDatabaseExstension project = getProject().getExtensions().findByType(GradleDatabaseExstension.class);
        String pathFile = project.getPathFile();

        File fileSql = new File(pathFile); //The file with sql createTabe
        String url = project.getUrl();
        String driverClass = project.getDriver(); //The drive name database is different
        String username = project.getUsername();
        String password = project.getPassword();
        String nameDatabase = project.getNameDatabase();

        DataSource dataSource = new DataSource();

        if (dataSource.connectionDatabase(driverClass, url + nameDatabase, username, password)) {
            if (dataSource.createTableWithFileSql(fileSql, true)) {
                System.out.println("The tables are created");
            }
            //TODO more information
        }
    }
}
