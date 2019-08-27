package com.test.nike.dictionary;

import android.app.Application;

import com.test.nike.dictionary.database.DefinitionDbManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize roomDB
        DefinitionDbManager.initRoom(this);
    }
}
