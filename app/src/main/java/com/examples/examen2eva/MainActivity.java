package com.examples.examen2eva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnConsultar;
    private Button btnQuimico;
    private Button btnSalir;

    private TextView numConsultas;
    private NotificationManager notificationManager;
    static final String CANAL_ID ="mi_canal";
    static final int NOTIFICACION_ID =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, 1111);
                finish();
            }
        });

        btnQuimico = findViewById(R.id.btnQuimico);
        btnQuimico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1111);
                finish();
            }
        });

        btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificacion();
                finish();
                System.exit(0);
            }
        });

        numConsultas = findViewById(R.id.txtVNumConsultas);
    }

    public void notificacion(){
        //Creamos notificacion
        notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        //Creamos el canal. SOLO puede hacerse en dispositivos con ver. 8 o más.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripción del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificacion =
                new NotificationCompat.Builder(MainActivity.this,CANAL_ID)
                        .setSmallIcon(R.drawable.ic_chats)
                        .setContentTitle("Despedida")
                        .setContentText("Adios");
        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
    }
}