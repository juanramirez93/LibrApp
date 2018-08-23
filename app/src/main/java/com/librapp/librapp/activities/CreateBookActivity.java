package com.librapp.librapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.librapp.librapp.R;
import com.librapp.librapp.adapters.AuthorAdapterSpinner;
import com.librapp.librapp.models.Author;
import com.librapp.librapp.models.Book;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateBookActivity extends AppCompatActivity implements View.OnClickListener {
    private Realm realm;
    private View viewInflated;
    private EditText titleField;
    private Spinner authorField;
    private Button addBook;
    private RealmResults<Author> authors;
    private Button addAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        initializeVariables();
    }


    private void initializeVariables() {
        realm = Realm.getDefaultInstance();
        authors = realm.where(Author.class).findAll();
        titleField = findViewById(R.id.AddBookTitle);
        authorField = findViewById(R.id.spinnerAuthor);
        AuthorAdapterSpinner authorAdapterSpinner = new AuthorAdapterSpinner(this, authors, R.layout.spinner_author_item);
        authorField.setAdapter(authorAdapterSpinner);
        this.setTitle("Crear Libro");
        addAuthor = findViewById(R.id.buttonAddAuthor);
        addAuthor.setOnClickListener(this);
        addBook = findViewById(R.id.addBookSave);
        addBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(addAuthor)){
            new CreateAuthorAlertDialog(this);
        }else if(v.equals(addBook)){
            createBook();
        }
    }

    private void createBook() {

        String title = titleField.getText().toString().trim();
        Author author = (Author) authorField.getSelectedItem();
        if (title.length() > 0) {
            realm.beginTransaction();
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            realm.copyToRealm(book);
            realm.commitTransaction();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Agregue un título", Toast.LENGTH_LONG).show();
        }

    }
}
