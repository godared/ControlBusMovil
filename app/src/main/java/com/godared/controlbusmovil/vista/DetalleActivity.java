package com.godared.controlbusmovil.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.godared.controlbusmovil.R;

public class DetalleActivity extends AppCompatActivity {
    private Toolbar tbToolBar;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        tbToolBar=(Toolbar)findViewById(R.id.miBarra);
        setSupportActionBar(tbToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
