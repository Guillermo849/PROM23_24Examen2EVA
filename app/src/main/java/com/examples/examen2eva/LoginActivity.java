package com.examples.examen2eva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static String NOMPASS = "admin";
    private TextView txtVErrorMessage;
    private ImageView imgError;
    private EditText edTxtNombre;
    private EditText edTxtContrasena;
    private Button btnLogin;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtVErrorMessage = findViewById(R.id.txtVErrorMessage);
        imgError = findViewById(R.id.imgError);
        edTxtNombre = findViewById(R.id.edTxtNombre);
        edTxtContrasena = findViewById(R.id.edTxtContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(c -> login());
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult(intent, 1111);
                finish();
            }
        });
    }

    private void login() {
        if (edTxtNombre.getText().toString().matches(NOMPASS) && edTxtContrasena.getText().toString().matches(NOMPASS)) {
            Intent intent = new Intent(LoginActivity.this, AdministrationActivity.class);
            finish();
            startActivityForResult(intent, 2222);

        } else {
            imgError.setVisibility(View.VISIBLE);
            txtVErrorMessage.setVisibility(View.VISIBLE);
            edTxtContrasena.setText("");
            edTxtNombre.setText("");
        }
    }
}
