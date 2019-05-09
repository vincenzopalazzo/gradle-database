package io.vincentpalazzo.gradledatabase.plugin;

import io.vincentpalazzo.gradledatabase.exstension.GradleDatabaseExstension;
import io.vincentpalazzo.gradledatabase.task.GradleCreateDatabaseTask;

import io.vincentpalazzo.gradledatabase.task.GradleCrateTablesIntoDatabeseTask;
import io.vincentpalazzo.gradledatabase.task.GradleDeleteDatabaseTask;
import io.vincentpalazzo.gradledatabase.task.GradleInsertValueIntoDatabaseTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class GradleDatabasePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().add("settingDatabase", GradleDatabaseExstension.class);

        //Task
        project.getTasks().create("createDatabase", GradleCreateDatabaseTask.class);
        project.getTasks().create("createTable", GradleCrateTablesIntoDatabeseTask.class);
        project.getTasks().create("insertValue", GradleInsertValueIntoDatabaseTask.class);
        project.getTasks().create("deleteDatabase", GradleDeleteDatabaseTask.class);

    }
}
