package com.garcesito.restauranteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogginActivity extends AppCompatActivity {
    String correoR, contrasenaR,correo,contrasena,LogOpcion;
    EditText ecorreo,econtrasena;
    GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 5678, RC_Face_sign=4321;
    private int opcionLogueo;//0 no hay 1 google 2 facebook 3 correo
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_loggin);

        ecorreo = (EditText) findViewById(R.id.ecorreo);
        econtrasena = (EditText) findViewById(R.id.econtrasena);
        //........loggin facebook........//

        loginButton = (LoginButton) findViewById(R.id.face_sign_in);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Login Exitoso",Toast.LENGTH_SHORT);
                goMainActivity();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login Cancelado",Toast.LENGTH_SHORT);

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Login Error",Toast.LENGTH_SHORT);
            }
        });
        //------------------------------------//

        //...........loggin google......//
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               // .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "Error en login", Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

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
    private void goMainActivity()
    {
        //prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        //editor = prefs.edit();

        //ditor.putInt("opcionLogueo",opcionLogueo);
        //editor.commit();
        LogOpcion="1";
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Logueo",LogOpcion);
        startActivity(intent);
    }
    private void goMainActivity2() {
        LogOpcion="2";
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Logueo",LogOpcion);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            correoR = data.getExtras().getString("Correo");
            contrasenaR = data.getExtras().getString("Password");
            //Toast.makeText(getApplicationContext(), correoR, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), contrasenaR, Toast.LENGTH_SHORT).show();

        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if(requestCode == RC_Face_sign)
        {

            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("google", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(getApplicationContext(), "Nombre de usuario:"+acct.toString(), Toast.LENGTH_LONG).show();

            Log.d("Nombre de usuario:", acct.getDisplayName());
            goMainActivity2();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(getApplicationContext(), "Error en login", Toast.LENGTH_SHORT).show();
        }
    }



    public void Registrese(View view) {
        Intent intent = new Intent(LogginActivity.this, RegistroActivity.class);
        startActivityForResult(intent, 1234);
    }

    public void iniciar(View view) {

        correo=ecorreo.getText().toString();
        contrasena=econtrasena.getText().toString();
        //Toast.makeText(getApplicationContext(), "este boton funciona bn", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "correoR: "+correoR, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "contraseña: "+contrasena, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "contraseñaR: "+contrasenaR, Toast.LENGTH_SHORT).show();
        if(correo.equals("")||contrasena.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Ingrese datos", Toast.LENGTH_SHORT).show();
        }
        else if( (correoR== null) || (contrasenaR == null))
        {
            Toast.makeText(getApplicationContext(), "No hay registros previos", Toast.LENGTH_SHORT).show();
        }
        else if((correo.equals(correoR)) && (contrasena.equals(contrasenaR)))
        {
            LogOpcion="0";
           // Toast.makeText(getApplicationContext(), "entro aqui: ", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(LogginActivity.this, MainActivity.class);
            intent.putExtra("correo", correoR);
            intent.putExtra("contrasena", contrasenaR);
            intent.putExtra("Logueo",LogOpcion);
            startActivityForResult(intent,1234);
            //finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Cuenta o contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }

    }
}
