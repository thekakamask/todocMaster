package com.cleanup.todoc.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.AllRepository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private AllRepository mAllRepository;
    private LiveData<List<Project>> mProjectList;
    private LiveData<List<Task>> mTasksList;


    public ListViewModel(@NonNull Application application) {
        super(application);
        mAllRepository = DI.getTaskRepository(application);
        mTasksList=mAllRepository.getAllTasks();
        mProjectList=mAllRepository.getAllProject();
    }

    public LiveData<List<Task>> getTasksList() {
         return mTasksList;
    }

    public LiveData<List<Project>> getProjectList() {
        return mProjectList;
    }

    public void deleteTask(Task task) {
        mAllRepository.deleteTask(task);
    }

    public void addNewTask(long projectId, String name, long creationTimestamp) {
        mAllRepository.addNewTask(projectId, name, creationTimestamp);
    }
}
