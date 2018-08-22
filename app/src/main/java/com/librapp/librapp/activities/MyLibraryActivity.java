package com.librapp.librapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.librapp.librapp.R;
import com.librapp.librapp.adapters.BookAdapter;
import com.librapp.librapp.models.Book;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MyLibraryActivity extends AppCompatActivity implements View.OnClickListener, RealmChangeListener {

    private FloatingActionButton fabAddBook;
    private Realm realm;
    private BookAdapter adapter;
    private RealmResults<Book> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);
        initializeVariables();
    }

    private void initializeVariables() {
        fabAddBook = findViewById(R.id.fabAddBook);
        fabAddBook = findViewById(R.id.fabAddBook);
        fabAddBook.setOnClickListener(this);
        realm = Realm.getDefaultInstance();
        books = realm.where(Book.class).findAll();
        books.addChangeListener(this);
        ListView listView = findViewById(R.id.listViewMyLibrary);
        adapter = new BookAdapter(this, books, R.layout.list_view_book_item);
        listView.setAdapter(adapter);

        realm = Realm.getDefaultInstance();
    }

    private void showAlertForCreatingBook(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_book, null);
        builder.setView(viewInflated);

        final EditText nameField = viewInflated.findViewById(R.id.AddBookTitle);

        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = nameField.getText().toString().trim();
                if (title.length() > 0) {
                    createNewBook(title);
                } else {
                    Toast.makeText(getApplicationContext(), "Agregue un nombre", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.create().show();
    }

    private void createNewBook(String title) {
        realm.beginTransaction();
        Book book = new Book();
        book.setTitle(title);
        realm.copyToRealm(book);
        realm.commitTransaction();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(fabAddBook)) {
            showAlertForCreatingBook("Agregar Libro", "Llena los datos");
        }
    }

    @Override
    public void onChange(@NonNull Object o) {
        if (o.equals(books)) {
            adapter.notifyDataSetChanged();
        }
    }
}
