package com.inc.vr.corps.sistemmonitoringpkm.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.inc.vr.corps.sistemmonitoringpkm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
    }
}