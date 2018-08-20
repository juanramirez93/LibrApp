package com.librapp.librapp.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Book extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private RealmList<Author> authors;
    private Editorial editorial;
    private int year;
    private RealmList<Theme> themes;
    private RealmList<Keyword> keywords;
    private Person owner;
    private Person holder;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(RealmList<Author> authors) {
        this.authors = authors;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public RealmList<Theme> getThemes() {
        return themes;
    }

    public void setThemes(RealmList<Theme> themes) {
        this.themes = themes;
    }

    public RealmList<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(RealmList<Keyword> keywords) {
        this.keywords = keywords;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getHolder() {
        return holder;
    }

    public void setHolder(Person holder) {
        this.holder = holder;
    }
}
