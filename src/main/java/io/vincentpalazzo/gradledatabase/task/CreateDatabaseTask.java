package io.vincentpalazzo.gradledatabase.task;

import io.vincentpalazzo.gradledatabase.exstension.GradleDatabaseExstension;
import io.vincentpalazzo.gradledatabase.persistence.DataSource;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.TaskAction;

import java.util.Iterator;
import java.io.File;
import java.util.Set;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class CreateDatabaseTask extends DefaultTask {

    @TaskAction
    public void createAction() {

        GradleDatabaseExstension project = getProject().getExtensions().findByType(GradleDatabaseExstension.class);

        String url = project.getUrl();
        String driverClass = project.getDriver(); //The drive name database is different
        String username = project.getUsername();
        String password = project.getPassword();
        String nameDatabase = project.getNameDatabase();
        String nameJar = project.getNameJar();

        if (findDependecyFileJarForDriver(nameJar)) {
            System.out.println("Jar findend");
        } else {
            System.out.println("Jar not found");
        }

        DataSource dataSource = new DataSource();
        if (dataSource.connectionDatabase(driverClass, url, username, password)) {
            if (dataSource.createDatabese(nameDatabase)) {
                System.out.println("Database " + nameDatabase + " created");
            }
        }
    }

    private boolean findDependecyFileJarForDriver(String nameJar) {
        if (nameJar == null || nameJar.isEmpty()) {
            throw new IllegalArgumentException("The input parameter is null");
        }

        Iterator<Configuration> iterable = getProject().getConfigurations().iterator();
        boolean finded = false;

        while ((!finded) || (iterable.hasNext())) {
            Configuration configuration = iterable.next();
            Set<File> filesSet = configuration.resolve();
            for (File file : filesSet) {
                String nameFile = file.getName();
                if (nameFile.contains(nameJar)) {
                    //Now?;
                    finded = true;
                }
            }
        }
        return finded;
    }
}
