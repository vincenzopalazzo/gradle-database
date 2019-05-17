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

import com.lingocoder.jar.JarHelper;
import io.vincentpalazzo.gradledatabase.Constant;
import io.vincentpalazzo.gradledatabase.exstension.GradleDatabaseExstension;
import org.gradle.api.DefaultTask;

import java.io.File;
import java.net.URLClassLoader;
import java.util.Optional;

/**
 * @author https://github.com/vincenzopalazzo
 */
public abstract class AbstractTaskGradleDatabase extends DefaultTask {

    protected URLClassLoader classLoaderJar;
    protected String pathFile;
    protected File fileSql; //The file with sql createTabe
    protected String url;
    protected String driverClass; //The drive name database is different
    protected String username;
    protected String password;
    protected String nameDatabase;
    protected String nameJar;
    protected String levelLog;
    protected JarHelper jarHelper;
    protected Optional<File> jar;

    protected void init(){
        GradleDatabaseExstension project = getProject().getExtensions().findByType(GradleDatabaseExstension.class);
        pathFile = project.getPathFile();
        fileSql = new File(pathFile); //The file with sql createTabe
        url = project.getUrl();
        driverClass = project.getDriver(); //The drive name database is different
        username = project.getUsername();
        password = project.getPassword();
        nameDatabase = project.getNameDatabase();
        nameJar = project.getNameJar();
        levelLog = project.getLevelLog();
        jarHelper = new JarHelper(getProject());
        jar = jarHelper.fetch(nameJar);
    }

    private void messageLog(String tag, String message){
        if(tag == null || message == null){
            throw new IllegalArgumentException("Argument function null");
        }
        if(levelLog.equalsIgnoreCase(Constant.DEBUG_TAG)){
            getProject().getLogger().debug(tag + ": " + message);
        }
        if(levelLog.equalsIgnoreCase(Constant.ERROR_TAG)){
            getProject().getLogger().error(tag + ": " + message);
        }
        if(levelLog.equalsIgnoreCase(Constant.INFO)){
            getProject().getLogger().info(tag + ": " + message);
        }
    }

    protected void messageDebug(String tag, String message){
        messageLog(tag, message);
    }

    protected void messageInfo(String tag, String message){
        messageLog(tag, message);
    }

    protected void messageError(String tag, String message){
        messageLog(tag, message);
    }
}
