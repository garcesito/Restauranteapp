package com.garcesito.restauranteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {
    private String correoR, contrasenaR,LogOpcion;
    TextView tContrasena,tCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Bundle extras = getIntent().getExtras();
        correoR = extras.getString("correo");
        contrasenaR = extras.getString("contrasena");
        LogOpcion = extras.getString("Logueo");

        tCorreo = (TextView) findViewById(R.id.tContrasena);
        tContrasena = (TextView) findViewById(R.id.tCorreo);

        tContrasena.setText("Contraseña: "+contrasenaR);
        tCorreo.setText("Correo: "+correoR);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.mMain:
                intent =  new Intent(PerfilActivity.this, MainActivity.class);
                intent.putExtra("correo", correoR);
                intent.putExtra("contrasena", contrasenaR);
                intent.putExtra("Logueo", LogOpcion);
                startActivity(intent);
                finish();

                break;
            case R.id.mCerrar:

                //Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                //        new ResultCallback<Status>() {
                //            @Override
                //            public void onResult(Status status) {
                //                // ...
                //            }
                //        });


                Intent intentcerrar = new Intent();
                intentcerrar.putExtra("Correo",correoR);
                intentcerrar.putExtra("Password",contrasenaR);
                setResult(RESULT_OK,intentcerrar);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
