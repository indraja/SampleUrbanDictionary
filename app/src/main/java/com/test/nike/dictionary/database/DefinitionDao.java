package com.test.nike.dictionary.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * DefinitionDao contains the methods used for accessing the database
 */
@Dao
public interface DefinitionDao {

    @Query("SELECT * FROM definitionEntity")
    List<DefinitionEntity> getAllDefinitions();

    @Query("SELECT * FROM DefinitionEntity ORDER BY thumbsUp DESC")
    List<DefinitionEntity> getDefinitionByMostThumbsUpOrder();

    @Query("SELECT * FROM DefinitionEntity ORDER BY thumbsUp ASC")
    List<DefinitionEntity> getDefinitionByLeastThumbsUpOrder();

    @Query("SELECT * FROM DefinitionEntity WHERE definitionId LIKE :id LIMIT 1")
    DefinitionEntity getDefinitionById(int id);

    @Insert
    void insertDefinition(DefinitionEntity definition);

    @Query("DELETE  FROM  DefinitionEntity")
    void deleteDefinitionTable();

    @Update
    void updateDefinitionTable(DefinitionEntity definitionTable);
}