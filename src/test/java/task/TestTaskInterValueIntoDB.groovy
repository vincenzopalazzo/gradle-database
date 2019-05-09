package task

import io.vincentpalazzo.gradledatabase.task.GradleInsertValueIntoDatabaseTask
import junit.framework.TestCase
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * @author https://github.com/vincenzopalazzo
 */
class TestTaskInterValueIntoDB {

    @Test
    void testTaskCreateDbFirst(){
        Project project = ProjectBuilder.builder().build()
        project.getPlugins().apply'io.vincentpalazzo.gradledatabase'

        TestCase.assertTrue(project.tasks.insertValue instanceof GradleInsertValueIntoDatabaseTask)
    }
}
