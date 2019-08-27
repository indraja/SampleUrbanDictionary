package com.test.nike.dictionary.network;

import java.io.Serializable;

public class WordDefinition implements Serializable {

    private String definition;

    private int thumbs_up;

    private int thumbs_down;

    private int defid;

    public String getDefinition() {
        return definition;
    }

    public int getThumbsUp() {
        return thumbs_up;
    }

    public int getThumbsDown() {
        return thumbs_down;
    }

    public int getDefId() {
        return defid;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
