package com.cleanup.todoc.repository;


import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class AllRepository {

    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Project>> allProject;

    public AllRepository(ProjectDao projectDao, TaskDao taskDao) {
        this.mTaskDao = taskDao;
        this.mProjectDao = projectDao;
        allTasks = this.mTaskDao.loadAllTasks();
        allProject = this.mProjectDao.loadAllProjects();
    }

    public LiveData<List<Project>> getAllProject() {
        return allProject;
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void addNewTask(long projectId, String name, long creationTimeStamp) {
        Task task = new Task(0, projectId, name, creationTimeStamp);
        new insertAsyncTask(mTaskDao).execute(task);

    }

    public void deleteTask(Task task) {
        new deleteAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsynchTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsynchTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsynchTaskDao.insertTasks(params[0]);
            return null;
        }


    }

    private static class deleteAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.deleteTasks(params[0]);
            return null;
        }


    }
}
