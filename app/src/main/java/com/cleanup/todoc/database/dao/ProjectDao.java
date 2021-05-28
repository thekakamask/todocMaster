package com.cleanup.todoc.database.dao;




import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> loadAllProjects();

    @Query("DELETE FROM project_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);


}
