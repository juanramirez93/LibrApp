package com.librapp.librapp.models;

import com.librapp.librapp.app.MyApplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Loan extends RealmObject {
    @PrimaryKey
    private long id;
    private Person person;
    private Date loanDate;
    private Date returnDate;

    public Loan() {
        this.id = MyApplication.LoanID.incrementAndGet();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
