package com.librapp.librapp.util;

import com.librapp.librapp.models.Book;

public class Puente {

    Book actual;

    public Puente() {
    }




    public void setDetalleBroli(Book broli){
        this.actual = broli;
    }

    public Book getBroli(){
        return this.actual;
    }
}
