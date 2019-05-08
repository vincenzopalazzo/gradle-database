package io.vincentpalazzo.gradledatabase.task;

import io.vincentpalazzo.gradledatabase.exstension.GradleDatabaseExstension;
import io.vincentpalazzo.gradledatabase.persistence.DataSurce;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.tasks.TaskAction;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class CreateDatabaseTask extends DefaultTask{

    @TaskAction
    public void createAction(){

        GradleDatabaseExstension project = getProject().getExtensions().findByType(GradleDatabaseExstension.class);

        String url = project.getUrl();
        String driverClass = project.getDriver(); //The drive name database is different
        String username = project.getUsername();
        String password = project.getPassword();
        String nameDatabase = project.getNameDatabase();
        String nameJar = project.getNameJar();

        ConfigurableFileCollection classpath = getProject().files();

        //TODO The name for jar for load a driver
        getProject().getDependencies().getArtifactTypes().getByName(nameJar);

        DataSurce dataSource = new DataSurce();
        if(dataSource.connectionDatabase(driverClass, url, username, password)){
            if (dataSource.createDatabese(nameDatabase)){
                System.out.println("Database " + nameDatabase + " created");
            }
        }
    }
}
