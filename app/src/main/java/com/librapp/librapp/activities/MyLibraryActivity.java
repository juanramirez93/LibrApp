package com.librapp.librapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.librapp.librapp.R;
import com.librapp.librapp.adapters.AuthorAdapterSpinner;
import com.librapp.librapp.adapters.BookAdapterListView;
import com.librapp.librapp.models.Author;
import com.librapp.librapp.models.Book;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MyLibraryActivity extends AppCompatActivity implements View.OnClickListener, RealmChangeListener, AdapterView.OnItemLongClickListener {

    private FloatingActionButton fabAddBook;
    private Realm realm;
    private BookAdapterListView adapter;
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
        adapter = new BookAdapterListView(this, books, R.layout.list_view_book_item);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
    }

    private void deleteBook(Book book){
        realm.beginTransaction();
        book.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(fabAddBook)) {
            Intent intent = new Intent(MyLibraryActivity.this, CreateBookActivity.class);
            startActivity(intent);
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
