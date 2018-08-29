package com.librapp.librapp.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.librapp.librapp.R;
import com.librapp.librapp.adapters.AuthorAdapterSpinner;
import com.librapp.librapp.models.Author;
import com.librapp.librapp.models.Book;
import com.squareup.picasso.Picasso;

import gun0912.tedbottompicker.TedBottomPicker;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class CreateBookActivity extends AppCompatActivity implements View.OnClickListener {
    private Realm realm;
    private ImageView imageView;
    private EditText titleField;
    private Spinner authorField;
    private LinearLayout linearLayout;
    private Button addBook;
    private RealmResults<Author> authors;
    private Button addAuthor;
    private final int MY_PERMISSIONS = 100;
    private String path;
    private Uri selectedUri;
    public RequestManager mGlideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        setTheme(R.style.LibrappTheme);
        initializeVariables();
    }


    private void initializeVariables() {
        realm = Realm.getDefaultInstance();
        mGlideRequestManager = Glide.with(this);
        authors = realm.where(Author.class).findAll();
        linearLayout = findViewById(R.id.AddBookLinearLayout);
        imageView = findViewById(R.id.AddBookImage);
        imageView.setOnClickListener(this);
        mGlideRequestManager.load(R.mipmap.ic_book_default).into(imageView);
        titleField = findViewById(R.id.AddBookTitle);
        authorField = findViewById(R.id.spinnerAuthor);
        AuthorAdapterSpinner authorAdapterSpinner = new AuthorAdapterSpinner(this, authors, R.layout.spinner_author_item);
        authorField.setAdapter(authorAdapterSpinner);
        this.setTitle("Crear Libro");
        addAuthor = findViewById(R.id.buttonAddAuthor);
        addAuthor.setOnClickListener(this);
        addBook = findViewById(R.id.addBookSave);
        addBook.setOnClickListener(this);
        mayRequestStoragePermission();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(addAuthor)) {
            new CreateAuthorAlertDialog(this);
        } else if (v.equals(addBook)) {
            createBook();
        } else if (v.equals(imageView)) {
            uploadImage();

        }
    }

    private void uploadImage() {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(final Uri uri) {
                        selectedUri = uri;
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                mGlideRequestManager
                                        .load(uri)
                                        .into(imageView);
                            }
                        });
                    }
                })
                .setSelectedUri(selectedUri)
                .setPeekHeight(1200)
                .create();

        tedBottomPicker.show(getSupportFragmentManager());
    }

    private void createBook() {

        String title = titleField.getText().toString().trim();
        Author author = (Author) authorField.getSelectedItem();
        if (title.length() > 0) {
            realm.beginTransaction();
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            if (selectedUri != null) {
                book.setImagePath(selectedUri.toString());
            }
            realm.copyToRealm(book);
            realm.commitTransaction();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Agregue un título", Toast.LENGTH_LONG).show();
        }

    }

    private boolean mayRequestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }


        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(linearLayout, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            }).show();
        } else {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(CreateBookActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                imageView.setEnabled(true);
            }
        } else {
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateBookActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    public void setPath(String path) {
        this.path = path;
    }
}
