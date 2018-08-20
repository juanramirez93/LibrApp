package com.librapp.librapp.models;

import com.librapp.librapp.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Editorial extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;

    public Editorial() {
        this.id = MyApplication.EditorialID.incrementAndGet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
