package com.librapp.librapp.models;

import com.librapp.librapp.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Theme extends RealmObject {
    @PrimaryKey
    private long id;
    private String word;

    public Theme() {
        this.id = MyApplication.KeywordID.incrementAndGet();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
