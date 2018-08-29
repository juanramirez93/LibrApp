package com.librapp.librapp.models;

import com.librapp.librapp.app.MyApplication;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Book extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private Author author;
    private Editorial editorial;
    private int year;
    private String language;
    private RealmList<Theme> themes;
    private RealmList<Keyword> keywords;
    private Person owner;
    private RealmList<Record> records;
    private RealmList<Loan> loans;
    private Date createdAt;
    private String imagePath;

    public Book() {
        this.id = MyApplication.BookID.incrementAndGet();
        this.themes = new RealmList<>();
        this.keywords = new RealmList<>();
        this.records = new RealmList<>();
        this.loans = new RealmList<>();
        this.createdAt = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public RealmList<Record> getRecords() {
        return records;
    }

    public void setRecords(RealmList<Record> records) {
        this.records = records;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public RealmList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(RealmList<Loan> loans) {
        this.loans = loans;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return title + "//" + author + "//" + imagePath;
    }
}
