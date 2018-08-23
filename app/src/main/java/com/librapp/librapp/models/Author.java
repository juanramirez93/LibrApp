package com.librapp.librapp.models;

import com.librapp.librapp.app.MyApplication;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Author extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private int age;
    @LinkingObjects("author")
    private final RealmResults<Book> books = null;

    public Author(){
        this.id = MyApplication.AuthorID.incrementAndGet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RealmResults<Book> getBooks() {
        return books;
    }
}
