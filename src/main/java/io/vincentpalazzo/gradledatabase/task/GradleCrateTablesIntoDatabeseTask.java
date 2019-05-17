/**
 * MIT License
 *
 * Copyright (c) 2019 Vincent Palazzo vincenzopalazzodev@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.vincentpalazzo.gradledatabase.task;

import io.vincentpalazzo.gradledatabase.Constant;
import io.vincentpalazzo.gradledatabase.exception.DataSurceException;
import io.vincentpalazzo.gradledatabase.persistence.DataSource;
import org.gradle.api.tasks.TaskAction;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class GradleCrateTablesIntoDatabeseTask extends AbstractTaskGradleDatabase {

    private final String loggerTag = this.getClass().getCanonicalName();

    public GradleCrateTablesIntoDatabeseTask() {
        super();
    }

    @TaskAction
    public void createAction() {
        init();

        DataSource dataSource = new DataSource();

        if(jar.isPresent()){
            messageInfo(loggerTag, "Jar findend");
            try {
                classLoaderJar = new URLClassLoader(new URL[]{jar.get().toURI().toURL()});

                if(levelLog.equalsIgnoreCase(Constant.DEBUG_TAG)){ messageDebug(loggerTag, "Classloader string: " + classLoaderJar.toString());}

                if (dataSource.connectionDatabase(classLoaderJar, driverClass.trim(), url.trim() + nameDatabase.toLowerCase(), username, password)) {

                    if(levelLog.equalsIgnoreCase(Constant.DEBUG_TAG)){ messageDebug(loggerTag, "Connection with database is ok");}

                    if (dataSource.createTableWithFileSql(fileSql, true)) {
                        if(levelLog.equalsIgnoreCase(Constant.DEBUG_TAG)){messageDebug(loggerTag, " create a table with file sql: ");}
                        getProject().getLogger().info("The tables are created");
                        dataSource.closeConnectionDatabase();
                        return;
                    }
                    //TODO more information
                    messageInfo(getName(), "Error founded");
                }
            } catch (MalformedURLException e) {
                if(levelLog.equalsIgnoreCase(Constant.DEBUG_TAG)){messageDebug(loggerTag, " Error verificate is: " + e.getMessage());}
                getProject().getLogger().info("Jar not found");
            } catch (DataSurceException e) {
                if(levelLog.equalsIgnoreCase(Constant.DEBUG_TAG)){ messageDebug(loggerTag, " Error verificate is: " + e.getMessage());}
                messageDebug(getName(), "Is verifiched an exeption into DataSurce, the exception is: " + e.getLocalizedMessage());
            }
        }else{
            messageInfo(getName(), "Jar not found");
        }
    }
}
