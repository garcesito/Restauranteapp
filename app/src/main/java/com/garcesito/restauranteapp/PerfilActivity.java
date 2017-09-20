package com.garcesito.restauranteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {
    private String correoR, contrasenaR;
    TextView tContrasena,tCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tCorreo = (TextView) findViewById(R.id.tContrasena);
        tContrasena = (TextView) findViewById(R.id.tCorreo);

        //Bundle extras = getIntent().getExtras();
        //correoR = extras.getString("correo");
        //contrasenaR = extras.getString("contrasena");

        tContrasena.setText("aqui va la respuesta de contrasena");
        tCorreo.setText("aqui va la respuesta de correo");
    }



}
