package com.garcesito.restauranteapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogginActivity extends AppCompatActivity {
    private String correoR, contrasenaR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.garcesito.restauranteapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public void iniciar(View view) {

        //se realizan las validaciones, SI SE CUMPLEN:

        Intent intent = new Intent(LogginActivity.this, MainActivity.class);
        intent.putExtra("correo", correoR);
        intent.putExtra("contrasena", contrasenaR);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            correoR = data.getExtras().getString("correo");
            contrasenaR = data.getExtras().getString("contrasena");
            Toast.makeText(this, correoR, Toast.LENGTH_SHORT).show();
            Log.d("correo", correoR); //visualizar en el monitor las variables
            Log.d("contrasena", contrasenaR);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Registrese(View view) {
        Intent intent = new Intent(LogginActivity.this, RegistroActivity.class);
        startActivityForResult(intent, 1234);
    }

}
