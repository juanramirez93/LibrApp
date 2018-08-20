package com.librapp.librapp.models;

import com.librapp.librapp.app.MyApplication;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private long cellphone;
    private String email;
    @LinkingObjects("person")
    private final RealmResults<Loan> loans = null;

    public Person(){
        this.id = MyApplication.PersonID.incrementAndGet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCellphone() {
        return cellphone;
    }

    public void setCellphone(long cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealmResults<Loan> getLoans() {
        return loans;
    }
}
