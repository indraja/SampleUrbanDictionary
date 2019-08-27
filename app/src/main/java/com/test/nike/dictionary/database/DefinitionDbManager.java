package com.test.nike.dictionary.database;

import android.content.Context;
import android.support.annotation.Nullable;

import com.test.nike.dictionary.network.WordDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class user to do different operation like insert, clear, sort from dao class
 */
public class DefinitionDbManager {
    private static DefinitionDbManager mInstance;
    private static DefinitionDao definitionDao;

    public static DefinitionDbManager getInstance() {
        if (mInstance == null) {
            mInstance = new DefinitionDbManager();
        }
        return mInstance;
    }

    public static void initRoom(Context context) {
        definitionDao = DefinitionDb.getInstance(context).getRepoDao();
    }

    public void clearData() {
        definitionDao.deleteDefinitionTable();
    }

    public void insertDefinitions(@Nullable List<WordDefinition> definitions) {
        if (definitions == null || definitions.isEmpty()) {
            return;
        }

        final List<DefinitionEntity> oldEntries = definitionDao.getAllDefinitions();
        if (oldEntries != null && !oldEntries.isEmpty()) {
            clearData();
        }

        for (int i = 0; i < definitions.size(); i++) {
            WordDefinition definition = definitions.get(i);
            DefinitionEntity definitionEntity = new DefinitionEntity();
            definitionEntity.setDefinition(definition.getDefinition());
            definitionEntity.setId(i);
            definitionEntity.setThumbsUp(definition.getThumbsUp());
            definitionEntity.setThumbsDown(definition.getThumbsDown());

            definitionDao.insertDefinition(definitionEntity);
        }
    }

    public List<DefinitionEntity> getAllDefinitions() {
        List<DefinitionEntity> definitions = definitionDao.getDefinitionByLeastThumbsUpOrder();
        return definitions != null ? definitions : new ArrayList<DefinitionEntity>();
    }

    public List<DefinitionEntity> getDefinitionByMostThumbsUpOrder() {
        List<DefinitionEntity> definitions = definitionDao.getDefinitionByMostThumbsUpOrder();
        return definitions != null ? definitions : new ArrayList<DefinitionEntity>();
    }

    public List<DefinitionEntity> getDefinitionByLeastThumbsUpOrder() {
        List<DefinitionEntity> definitions = definitionDao.getDefinitionByLeastThumbsUpOrder();
        return definitions != null ? definitions : new ArrayList<DefinitionEntity>();
    }
}