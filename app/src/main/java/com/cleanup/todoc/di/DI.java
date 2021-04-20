package com.cleanup.todoc.di;

import android.app.Application;


import com.cleanup.todoc.database.CleanUpDatabase;
import com.cleanup.todoc.repository.AllRepository;

public class DI {
    private static boolean instantiateDbInMem = false;

    private static AllRepository mRepository = null;

    public static AllRepository getTaskRepository(Application application) {
        if(mRepository == null) {
            CleanUpDatabase db;
            if (instantiateDbInMem) {
                db= CleanUpDatabase.getNewDatabaseInMem(application);
            } else {
                db = CleanUpDatabase.getDatabase(application);
            }
            mRepository = new AllRepository(db.mProjectDao(), db.mTaskDao());

        }
        return mRepository;
    }

    public static void setInstantiateDbInMemory(boolean val) {
        instantiateDbInMem = val;
    }
}
