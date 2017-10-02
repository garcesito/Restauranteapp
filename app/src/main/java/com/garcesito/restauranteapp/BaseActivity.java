package com.garcesito.restauranteapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

         fm =getSupportFragmentManager();
         ft = fm.beginTransaction();

        CartaFragment fragment = new CartaFragment();
        ft.add(R.id.frame, fragment).commit();

    }

    public void cambiar(View view) {
        ft = fm.beginTransaction();
        PedidosFragment fragment = new PedidosFragment();
        ft.replace(R.id.frame, fragment).commit();
    }
}
