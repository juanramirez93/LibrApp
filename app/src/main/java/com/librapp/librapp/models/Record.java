package com.librapp.librapp.models;

import com.librapp.librapp.app.MyApplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Record extends RealmObject {

    @PrimaryKey
    private long id;
    private Date start;
    private Date end;
    private int grade;

    public Record() {
        this.id = MyApplication.RecordID.incrementAndGet();
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
