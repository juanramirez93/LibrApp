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
import android.widget.ArrayAdapter;
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
import com.librapp.librapp.util.Puente;

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
    public Puente pte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        setTheme(R.style.LibrappTheme);
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("title");
        //Toast.makeText(this, nombre,Toast.LENGTH_SHORT).show();
        initializeVariables();
       if(!nombre.equals("")){
            this.setTitle("Editar Libro");
            this.setValoredEdit(intent);
        }else{
            this.setTitle("Crear Libro");
        }

    }

    public void setValoredEdit(Intent intent){
        String nombre = intent.getStringExtra("title");
        String autor = intent.getStringExtra("autor");
        Toast.makeText(this, autor,Toast.LENGTH_SHORT).show();
        int pos;
        this.titleField.setText(nombre.toString());
        pos = getIndex(authorField, autor);
        this.authorField.setSelection(pos);
    }

    private int getIndex(Spinner spinner, String myString){
        String valor = "";
        int valor1 = 0, valor2 = 0;
        for (int i=0;i<spinner.getCount();i++){
            valor=spinner.getItemAtPosition(i).toString();
            valor1 =  valor.indexOf("name:") + 5;
            valor2 =  valor.indexOf("}]");
            valor=valor.substring(valor1, valor2 );
            if (valor.equals(myString)){
                return i;
            }
        }
        return 0;
    }

    private void agregarAutor(Intent intent){
        String compareValue = intent.getStringExtra("autor");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yellows, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        authorField.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            authorField.setSelection(spinnerPosition);
        }
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

    public Book puente(Intent intent){
        return (Book)intent.getSerializableExtra("libros");
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
