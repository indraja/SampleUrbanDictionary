package com.test.nike.dictionary.network;

import java.io.Serializable;
import java.util.List;

/**
 * Pojo class for word search api response.
 */
public class Dictionary implements Serializable {
    private List<WordDefinition> list;

    public List<WordDefinition> getWordDefinitions() {
        return list;
    }
}
