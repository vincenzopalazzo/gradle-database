package io.vincentpalazzo.gradledatabase.plugin;

import io.vincentpalazzo.gradledatabase.exstension.GradleDatabaseExstension;
import io.vincentpalazzo.gradledatabase.task.CreateDatabaseTask;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class GradleDatabasePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("createDatabase", CreateDatabaseTask.class);
        project.getExtensions().add("settingDatabase", GradleDatabaseExstension.class);
    }
}
