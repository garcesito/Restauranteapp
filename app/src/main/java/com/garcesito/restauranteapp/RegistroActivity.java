package com.garcesito.restauranteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {
    private String correo, contrasena,repconrasena;
    EditText eCorreo,eContrasena,eRepContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        eRepContrasena = (EditText) findViewById(R.id.eRepContrasena);
    }

    public void registrar(View view) {
        correo= eCorreo.getText().toString();
        contrasena=eContrasena.getText().toString();
        repconrasena=eRepContrasena.getText().toString();
        if ((repconrasena.equals(contrasena)) && (repconrasena!= null))
        {
            if (correo.contains("@"))
            {
                Intent intent = new Intent();
                intent.putExtra("Correo",correo);
                intent.putExtra("Password",contrasena);
                setResult(RESULT_OK,intent);
                finish();
            }else
            {
                Toast.makeText(getApplicationContext(), "Correo no valido", Toast.LENGTH_SHORT).show();
            }

        }else
        {
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }


    }
}
