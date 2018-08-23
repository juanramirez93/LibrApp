package com.librapp.librapp.app;

import android.app.Application;

import com.librapp.librapp.models.Author;
import com.librapp.librapp.models.Book;
import com.librapp.librapp.models.Editorial;
import com.librapp.librapp.models.Keyword;
import com.librapp.librapp.models.Loan;
import com.librapp.librapp.models.Person;
import com.librapp.librapp.models.Record;
import com.librapp.librapp.models.Theme;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApplication extends Application {
    public static AtomicInteger BookID;
    public static AtomicInteger AuthorID;
    public static AtomicInteger EditorialID;
    public static AtomicInteger KeywordID;
    public static AtomicInteger PersonID;
    public static AtomicInteger RecordID;
    public static AtomicInteger ThemeID;
    public static AtomicInteger LoanID;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        BookID = getIdByTable(realm, Book.class);
        AuthorID = getIdByTable(realm, Author.class);
        EditorialID = getIdByTable(realm, Editorial.class);
        KeywordID = getIdByTable(realm, Keyword.class);
        PersonID = getIdByTable(realm, Person.class);
        RecordID = getIdByTable(realm, Record.class);
        ThemeID = getIdByTable(realm, Theme.class);
        LoanID = getIdByTable(realm, Loan.class);
        realm.close();
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }
}


