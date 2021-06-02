package com.cleanup.todoc;


import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnitRunner;

import com.cleanup.todoc.database.CleanUpDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;


import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DatabaseInstrumentedTest {

    private static final String TAG = "MyActivity";
    private ProjectDao mProjectDao;
    private TaskDao mTaskDao;
    private CleanUpDatabase db;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = CleanUpDatabase.getNewDatabaseInMem(context);
        mTaskDao = db.mTaskDao();
        mProjectDao = db.mProjectDao();

    }


    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        Task expectedTask = new Task(1,1L,"test", 0);
        mTaskDao.insertTasks(expectedTask);

        List<Project> allProjects = LiveDataTestUtil.getValue(mProjectDao.loadAllProjects());
        assertNotNull(allProjects);

        List<Task> allTasks = LiveDataTestUtil.getValue(mTaskDao.loadAllTasks());
        assertNotNull(allTasks);
        //Log.v(TAG, "tasks insert =" + allTasks.get(0).toString());
        //assertTrue(allTasks.contains(expectedTask));
        assertEquals(expectedTask.getName(),allTasks.get(0).getName());

        mTaskDao.deleteTasks(expectedTask);
        List<Task> emptyAllTasks = LiveDataTestUtil.getValue(mTaskDao.loadAllTasks());
        assertThat(emptyAllTasks, Matchers.empty());

    }

}
