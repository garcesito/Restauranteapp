package com.garcesito.restauranteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {

    private String correoR, contrasenaR;
    GoogleApiClient mGoogleApiClient;
    //SharedPreferences prefs;
    //SharedPreferences.Editor editor;
    String LogOpcion;//0 para correo,1 para facebook,2 para google
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        LogOpcion =extras.getString("Logueo");
        if(LogOpcion.equals("0"))
        {
            correoR = extras.getString("correo");
            contrasenaR = extras.getString("contrasena");
        }else if(LogOpcion.equals("1"))
        {
            if(AccessToken.getCurrentAccessToken()== null)
            {

                goLogginActivity();
            }
        }






    }

    private void goLogginActivity() {
        Intent intent = new Intent(getApplicationContext(),LogginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.mPerfil:
                intent =  new Intent(MainActivity.this, PerfilActivity.class);
                intent.putExtra("correo", correoR);
                intent.putExtra("contrasena", contrasenaR);
                startActivity(intent);
                finish();

                break;
            case R.id.mCerrar:

                if (LogOpcion.equals("0"))
                {
                    Intent intentcerrar = new Intent();
                    intentcerrar.putExtra("Correo",correoR);
                    intentcerrar.putExtra("Password",contrasenaR);
                    setResult(RESULT_OK,intentcerrar);
                    finish();
                }else if(LogOpcion.equals("1"))
                {
                    LoginManager.getInstance().logOut();
                    goLogginActivity();
                }else if(LogOpcion.equals("2"))
                {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            // ...
                                        }
                                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
