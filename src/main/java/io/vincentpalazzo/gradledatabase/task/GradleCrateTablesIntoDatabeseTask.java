package io.vincentpalazzo.gradledatabase.task;

import io.vincentpalazzo.gradledatabase.exstension.GradleDatabaseExstension;
import io.vincentpalazzo.gradledatabase.persistence.DataSource;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class GradleCrateTablesIntoDatabeseTask extends DefaultTask {

    private URLClassLoader classLoaderJar;

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
        String nameJar = project.getNameJar();

        DataSource dataSource = new DataSource();

        findDependecyFileJarForDriver(nameJar);
        if (dataSource.connectionDatabase(classLoaderJar, driverClass, url + nameDatabase.toLowerCase(), username, password)) {
            if (dataSource.createTableWithFileSql(fileSql, true)) {
                System.out.println("The tables are created");
                dataSource.closeConnectionDatabase();
            }
            //TODO more information
        }
    }

    private boolean findDependecyFileJarForDriver(String nameJar) {
        if (nameJar == null || nameJar.isEmpty()) {
            throw new IllegalArgumentException("The input parameter is null");
        }

        Set<File> classpath = getProject().getConfigurations().getByName("compileClasspath").getFiles();
        for(File file : classpath){
            if(file.getName().contains(nameJar)){
                System.out.println("Contains dependende " + file.getName());
                try {
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
                    this.classLoaderJar = classLoader;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
