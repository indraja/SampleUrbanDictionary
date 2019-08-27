package com.test.nike.dictionary.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity class for definition table
 */
@Entity(tableName = "definitionEntity")
public class DefinitionEntity {
    @PrimaryKey
    @ColumnInfo(name = "definitionId")
    private int id;

    @ColumnInfo(name = "thumbsUp")
    private int thumbsUp;

    @ColumnInfo(name = "thumbsDown")
    private int thumbsDown;

    @ColumnInfo(name = "definition")
    private String definition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}