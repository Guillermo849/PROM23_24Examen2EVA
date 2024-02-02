package com.examples.examen2eva;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ElementosSQLiteHelper extends SQLiteOpenHelper {
    String sqlCreate =
            "CREATE TABLE Elementos (id INTEGER PRIMARY KEY," +
                    " nombre TEXT, simbolo TEXT, num_atomico INTEGER, estado TEXT)";
    public ElementosSQLiteHelper(Context contexto, String nombre,
                                 CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO elementos VALUES(1,'HELIO','He',2,'GAS')");
        db.execSQL("INSERT INTO elementos VALUES(2,'HIERRO','Fe',26,'SOLIDO')");
        db.execSQL("INSERT INTO elementos VALUES(3,'MERCURIO','Hg',80,'LIQUIDO')");
        Log.i("Creada", "Base de datos creada sin borrar");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Contactos");
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO elementos VALUES(1,'HELIO','He',2,'GAS')");
        db.execSQL("INSERT INTO elementos VALUES(2,'HIERRO','Fe',26,'SOLIDO')");
        db.execSQL("INSERT INTO elementos VALUES(3,'MERCURIO','Hg',80,'LIQUIDO')");
        Log.i("Creada", "Base de datos creada");
    }
}
