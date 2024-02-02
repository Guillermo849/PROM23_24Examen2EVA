package com.examples.examen2eva;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdministrationActivity extends AppCompatActivity {


    private EditText edTxtCodigo;
    private EditText edTxtNombre;
    private EditText edTxtSimbolo;
    private EditText edTxtNumAtomico;
    private EditText edTxtEstado;
    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnVovler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        edTxtCodigo = findViewById(R.id.edTxtCodigo);
        edTxtNombre = findViewById(R.id.edTxtNombre);
        edTxtSimbolo = findViewById(R.id.edTxtSimbolo);
        edTxtNumAtomico = findViewById(R.id.edTxtNumAtomico);
        edTxtEstado = findViewById(R.id.edTxtEstado);

        btnInsertar = findViewById(R.id.btnInsertar);
        btnInsertar.setOnClickListener(c -> insertar());
        btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(c -> actualizar());
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(c -> eliminar());
        btnVovler = findViewById(R.id.btnVolver);
        btnVovler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministrationActivity.this, MainActivity.class);
                startActivityForResult(intent, 1111);
                finish();
            }
        });
    }

    private void insertar() {
        if (!edTxtCodigo.getText().toString().equals("") && !edTxtNombre.getText().toString().equals("")) {
            ElementosSQLiteHelper usdbh =
                    new ElementosSQLiteHelper(this, "BDDQuimica", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            int codigo = Integer.parseInt(edTxtCodigo.getText().toString());
            String nombre = edTxtNombre.getText().toString();
            String simbolo = edTxtSimbolo.getText().toString();
            int numAtomico = Integer.parseInt(edTxtNumAtomico.getText().toString());
            String estado = edTxtEstado.getText().toString();
            SQLiteDatabase dbRead = usdbh.getReadableDatabase();
            if (dbRead != null) {
                String[] args = new String[] {edTxtNombre.getText().toString()};
                Cursor c = dbRead.rawQuery("SELECT * FROM elementos WHERE nombre LIKE ?", args);
                if (c.moveToFirst()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("El elemento ya existe")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int wich) {}
                            }).show();
                } else {
                    String insertQuery = "INSERT INTO elementos (id,nombre,simbolo,num_atomico,estado) VALUES("+codigo+", '"+nombre+"', '"+simbolo+"', "+numAtomico+", '"+estado+"')";
                    db.execSQL(insertQuery);
                }
            }

            dbRead.close();
            db.close();
            edTxtCodigo.setText("");
            edTxtNombre.setText("");
            edTxtSimbolo.setText("");
            edTxtNumAtomico.setText("");
            edTxtEstado.setText("");
        }
    }

    private void actualizar() {
        if (!edTxtCodigo.getText().toString().equals("")) {
            ElementosSQLiteHelper usdbh =
                    new ElementosSQLiteHelper(this, "BDDQuimica", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();

            int codigo = Integer.parseInt(edTxtCodigo.getText().toString());
            String nombre = edTxtNombre.getText().toString();
            String simbolo = edTxtSimbolo.getText().toString();
            int numAtomico = Integer.parseInt(edTxtNumAtomico.getText().toString());
            String estado = edTxtEstado.getText().toString();

            SQLiteDatabase dbRead = usdbh.getReadableDatabase();
            if (dbRead != null) {
                String[] args = new String[] {edTxtNombre.getText().toString()};
                Cursor c = dbRead.rawQuery("SELECT * FROM elementos WHERE nombre LIKE ?", args);
                if (c.moveToFirst()) {
                    db.execSQL("UPDATE elementos SET id="+codigo+", nombre='"+nombre+"', simbolo='"+simbolo+"', num_atomico="+numAtomico+", estado='"+estado+"' WHERE nombre LIKE '"+edTxtNombre.getText().toString()+"';");
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("El elemento se ha modificado")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int wich) {}
                            }).show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("El elemento no existe")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int wich) {}
                            }).show();
                }
            }
            dbRead.close();
            db.close();
            edTxtCodigo.setText("");
            edTxtNombre.setText("");
            edTxtSimbolo.setText("");
            edTxtNumAtomico.setText("");
            edTxtEstado.setText("");
        }
    }

    private void eliminar() {
        if (!edTxtCodigo.getText().toString().equals("")) {
            ElementosSQLiteHelper usdbh =
                    new ElementosSQLiteHelper(this, "BDDQuimica", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            SQLiteDatabase dbRead = usdbh.getReadableDatabase();
            if (dbRead != null) {
                String[] args = new String[]{edTxtNombre.getText().toString()};
                Cursor c = dbRead.rawQuery("SELECT * FROM elementos WHERE nombre LIKE ?", args);
                if (c.moveToFirst()) {
                    db.execSQL("DELETE FROM elementos WHERE nombre LIKE '"+edTxtNombre.getText().toString()+"';");
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("El elemento se ha eliminado correctamente")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int wich) {}
                            }).show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("El elemento no existe")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int wich) {}
                            }).show();
                }
            }
            dbRead.close();
            db.close();

            edTxtCodigo.setText("");
            edTxtNombre.setText("");
            edTxtSimbolo.setText("");
            edTxtNumAtomico.setText("");
            edTxtEstado.setText("");
        }
    }
}
