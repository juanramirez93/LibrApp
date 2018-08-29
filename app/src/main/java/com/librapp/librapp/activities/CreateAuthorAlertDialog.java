package com.librapp.librapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.librapp.librapp.R;
import com.librapp.librapp.adapters.AuthorAdapterSpinner;
import com.librapp.librapp.models.Author;
import com.librapp.librapp.models.Book;

import io.realm.Realm;

public class CreateAuthorAlertDialog extends AlertDialog.Builder {

    private Context context;
    private Realm realm;
    private View viewInflated;
    private EditText nameField;

    public CreateAuthorAlertDialog(Context context) {
        super(context);
        this.context = context;
        initializeVariables();
        setLayout();
    }

    private void initializeVariables() {
        realm = Realm.getDefaultInstance();
        viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_create_author, null);
        this.setView(viewInflated);
        nameField = viewInflated.findViewById(R.id.AddAuthorName);
        this.setTitle("Crear Autor");
    }

    private void setLayout() {

        this.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createAuthor();
            }
        });

        this.create().show();
    }

    private void createAuthor() {
        String name = nameField.getText().toString().trim();

        if (name.length() > 0) {

            Author author = new Author();
            author.setName(name);
            realm.beginTransaction();
            realm.copyToRealm(author);
            realm.commitTransaction();
        } else {
            Toast.makeText(context.getApplicationContext(), "Agregue un nombre", Toast.LENGTH_LONG).show();
        }
    }
}
