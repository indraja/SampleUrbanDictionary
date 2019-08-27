package com.test.nike.dictionary.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * DefinitionDb provides an abstraction layer over RoomDatabase to access all the definition related tables
 */
@Database(entities = {DefinitionEntity.class}, version = RoomDBConstants.ROOM_DB_VERSION)
@TypeConverters({NikeTypeConverter.class})
public abstract class DefinitionDb extends RoomDatabase {

    private static final String DB_NAME = RoomDBConstants.ROOM_DB_NAME;
    private static volatile DefinitionDb instance;

    static synchronized DefinitionDb getInstance(Context context) {
        // get an instance of the created database using the following code
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public static void closeDB() {
        try {
            if (instance != null && instance.isOpen()) {
                instance.close();
                instance = null;
            }
        } catch (Exception e) {
            instance = null;
        }
    }

    private static DefinitionDb create(final Context context) {
        return Room.databaseBuilder(context, DefinitionDb.class, DB_NAME)
                .allowMainThreadQueries().
                        build();
    }

    public abstract DefinitionDao getRepoDao();
}