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
public class GradleDeleteDatabaseTask extends DefaultTask {

    private URLClassLoader classLoaderJar;

    @TaskAction
    public void createAction() {

        GradleDatabaseExstension project = getProject().getExtensions().findByType(GradleDatabaseExstension.class);

        String url = project.getUrl();
        String driverClass = project.getDriver(); //The drive name database is different
        String username = project.getUsername();
        String password = project.getPassword();
        String nameDatabase = project.getNameDatabase();
        String nameJar = project.getNameJar();

        findDependecyFileJarForDriver(nameJar);

        DataSource dataSource = new DataSource();
        if (dataSource.connectionDatabase(classLoaderJar, driverClass, url, username, password)) {
            if (dataSource.deleteDatabese(nameDatabase.toLowerCase())) {
                System.out.println("Database " + nameDatabase.toLowerCase() + " deleted");
                dataSource.closeConnectionDatabase();
            }
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
