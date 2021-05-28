package com.cleanup.todoc.database;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;


@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class CleanUpDatabase extends RoomDatabase {

    public abstract ProjectDao mProjectDao();
    public abstract TaskDao mTaskDao();

    public static volatile CleanUpDatabase INSTANCE;

    public static CleanUpDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CleanUpDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CleanUpDatabase.class, "task_database")
                            .addCallback(databaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static CleanUpDatabase getNewDatabaseInMem(final Context context) {
        INSTANCE =Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                CleanUpDatabase.class)
                .allowMainThreadQueries()
                .addCallback(databaseCallback)
                .build();
        return INSTANCE;
    }

    private static CleanUpDatabase.Callback databaseCallback =
            new CleanUpDatabase.Callback(){

                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ProjectDao mDao;

        PopulateDbAsync(CleanUpDatabase db) {
            mDao = db.mProjectDao();}

            @Override
        protected Void doInBackground(final Void... params) {
            Log.d ("Test", "doInBackground");
            mDao.deleteAll();
            Project[] projects = Project.getAllProjects();
            for(Project project : projects) {
                mDao.insert(project);
            }
            return null;
            }



    }




}
