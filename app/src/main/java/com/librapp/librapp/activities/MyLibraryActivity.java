package com.librapp.librapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.librapp.librapp.R;
import com.librapp.librapp.adapters.BookAdapter;
import com.librapp.librapp.models.Book;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MyLibraryActivity extends AppCompatActivity implements View.OnClickListener, RealmChangeListener, AdapterView.OnItemLongClickListener {

    private FloatingActionButton fabAddBook;
    private Realm realm;
    private BookAdapter adapter;
    private RealmResults<Book> books;
    private ListView listView;


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
        listView = findViewById(R.id.listViewMyLibrary);
        adapter = new BookAdapter(this, books, R.layout.list_view_book_item);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        realm = Realm.getDefaultInstance();
    }

    private void showDialogForCreatingBook(String title, String message) {
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
                    createBook(title);
                } else {
                    Toast.makeText(getApplicationContext(), "Agregue un título", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.create().show();
    }

    private void deleteBook(Book book){
        realm.beginTransaction();
        book.deleteFromRealm();
        realm.commitTransaction();
    }

    private void createBook(String title) {
        realm.beginTransaction();
        Book book = new Book();
        book.setTitle(title);
        realm.copyToRealm(book);
        realm.commitTransaction();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(fabAddBook)) {
            showDialogForCreatingBook("Agregar Libro", "Llena los datos");
        }
    }

    @Override
    public void onChange(@NonNull Object o) {
        if (o.equals(books)) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.listViewMyLibrary:
                Book book = (Book)parent.getItemAtPosition(position);
                showDialogForDeletingBook(book.getTitle(), book);
                return true;

        }
        return false;
    }

    private void showDialogForDeletingBook(String title, final Book book) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_delete_book, null);
        builder.setView(viewInflated);


        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBook(book);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
