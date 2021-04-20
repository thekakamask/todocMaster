package com.cleanup.todoc.database.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * from task_table")
    public LiveData<List<Task>> loadAllTasks();

    @Query("SELECT * FROM task_table WHERE id = :id LIMIT 1")
    public LiveData<Task> loadTaskById(long id);

    @Update
    public void updateTasks(Task... tasks);

    @Delete
    public void deleteTasks(Task... tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTasks(Task... tasks);



}
