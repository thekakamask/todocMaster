package com.cleanup.todoc;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.AllRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class AllRepositoryUnitTest {

    private AllRepository mAllRepository;

    private TaskDao mTaskDao = mock(TaskDao.class);
    private ProjectDao mProjectDao = mock(ProjectDao.class);

    private List<Project> expectedProjects = Arrays.asList(Project.getAllProjects());

    private List<Task> expectedTasks= Arrays.asList(
            new Task(0, 0, "nom", 0),
            new Task(1,1,"nom2", 1)

    );

    LiveData<List<Project>> mLiveDataProjectList = new MutableLiveData<>(expectedProjects);

    LiveData<List<Task>> mLiveDataTaskList = new MutableLiveData<>(expectedTasks);

    @Before
    public void setup() {

        when(mProjectDao.loadAllProjects()).thenReturn(mLiveDataProjectList);
        when(mTaskDao.loadAllTasks()).thenReturn(mLiveDataTaskList);

        mAllRepository = new AllRepository(mProjectDao, mTaskDao);

    }

    @Test
    public void getAllProjectsReturnList() {
        assertEquals(mLiveDataProjectList, mAllRepository.getAllProject());
    }

    @Test
    public void getAllTasksReturnList() {
        assertEquals(mLiveDataTaskList, mAllRepository.getAllProject());
    }


}
