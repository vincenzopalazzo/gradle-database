package task

import junit.framework.TestCase
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * @author https://github.com/vincenzopalazzo
 */
class TestTaskCreateDB {

    @Test
    void testTaskCreateDbFirst(){
        Project project = ProjectBuilder.builder().build()
        project.getPlugins().apply'io.vincentpalazzo.gradledatabase'

       // assertTrue(project.tasks.createDatabase instanceof CreateDatabaseTask)
        TestCase.assertTrue(false)
    }
}
