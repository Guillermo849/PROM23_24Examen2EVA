package com.examples.examen2eva;

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

public class SearchActivity extends AppCompatActivity {
    private TextView txtVListElementos;
    private EditText edTxtNombre;
    private Button btnCosultar;
    private Button btnLimpiar;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtVListElementos = findViewById(R.id.txtVListUsuarios);

        edTxtNombre = findViewById(R.id.edTxtNombre);
        btnCosultar = findViewById(R.id.btnCosultar);
        btnCosultar.setOnClickListener(c -> consultar());
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(c -> limpiar());
        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivityForResult(intent, 1111);
                finish();
            }
        });

        ElementosSQLiteHelper usdbh =
                new ElementosSQLiteHelper(this, "BDDQuimica", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM elementos", null);
            if (c.moveToFirst()) {
                do {
                    txtVListElementos.setText(txtVListElementos.getText()+"\n"+c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2)+" - "+c.getString(3)+" - "+c.getString(4));
                } while (c.moveToNext());
            }
        }
        db.close();
    }

    private void limpiar() {
        edTxtNombre.setText("");
        txtVListElementos.setText("");
        btnLimpiar.setVisibility(View.INVISIBLE);
        btnCosultar.setVisibility(View.VISIBLE);
    }

    private void consultar() {
        ElementosSQLiteHelper usdbh =
                new ElementosSQLiteHelper(this, "BDDQuimica", null, 1);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        txtVListElementos.setText("");
        if (db != null) {
            if (!edTxtNombre.getText().toString().matches("")) {
                String[] args = new String[] {edTxtNombre.getText()+"%"};
                Cursor c = db.rawQuery("SELECT * FROM elementos WHERE nombre LIKE ?", args);
                //Log.i("Consulta", c.getString(0));
                if(c.moveToFirst()) {
                    do {
                        txtVListElementos.setText(txtVListElementos.getText()+"\n"+c.getInt(0)+" - "+c.getString(1)+" - "+c.getString(2));
                    } while (c.moveToNext());
                }
                btnLimpiar.setVisibility(View.VISIBLE);
                btnCosultar.setVisibility(View.INVISIBLE);
            } else {
                txtVListElementos.setText("No hay elementos con ese nombre");
            }
        }
        db.close();
    }
}
