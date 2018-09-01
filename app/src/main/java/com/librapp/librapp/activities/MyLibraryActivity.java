package com.librapp.librapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.librapp.librapp.R;
import com.librapp.librapp.adapters.BookAdapterRecyclerView;
import com.librapp.librapp.models.Book;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MyLibraryActivity extends AppCompatActivity implements View.OnClickListener, RealmChangeListener {

    private FloatingActionButton fabAddBook;
    private Realm realm;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rVAdapter;
    private RecyclerView.LayoutManager layoutManager;
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
        recyclerView = findViewById(R.id.rVMyLibrary);
        layoutManager = new GridLayoutManager(this, 2);
        rVAdapter = new BookAdapterRecyclerView(books, R.layout.recycler_view_book_item, new BookAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(Book book, int position) {
                Intent intent = new Intent(MyLibraryActivity.this, CreateBookActivity.class);
                intent.putExtra("title",book.getTitle());
               //t intent.putExtra("autor",book.getAuthor().getName());
                startActivity(intent);

                //Toast.makeText(MyLibraryActivity.this, book.getTitle().toString(), Toast.LENGTH_LONG).show();
            }
        }, new BookAdapterRecyclerView.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Book book, int position) {
                showDialogForDeletingBook(book);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rVAdapter);

    }

    private void deleteBook(Book book) {
        realm.beginTransaction();
        book.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(fabAddBook)) {
            Intent intent = new Intent(MyLibraryActivity.this, CreateBookActivity.class);
            intent.putExtra("title","");
            startActivity(intent);
        }
    }

    @Override
    public void onChange(@NonNull Object o) {
        if (o.equals(books)) {
            rVAdapter.notifyDataSetChanged();
        }
    }

    private void showDialogForDeletingBook(final Book book) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (book.getTitle() != null) builder.setTitle(book.getTitle());

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
