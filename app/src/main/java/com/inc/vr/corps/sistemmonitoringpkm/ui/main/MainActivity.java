package com.inc.vr.corps.sistemmonitoringpkm.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.ui.data.DataUsaha;
import com.inc.vr.corps.sistemmonitoringpkm.ui.kegiatan.KegiatanPkm_Activity;
import com.inc.vr.corps.sistemmonitoringpkm.ui.laporan.LaporanKeuangan;
import com.inc.vr.corps.sistemmonitoringpkm.ui.progress.ProgressActivity;

public class MainActivity extends AppCompatActivity {
    CardView btnKegiatan, btnDataUsaha,btnProgres,btnLaporan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView();
        BtnClick();
    }
    void initView(){
        btnKegiatan = findViewById(R.id.home_kegiatan_pkm);
        btnDataUsaha = findViewById(R.id.home_data_usaha);
        btnProgres = findViewById(R.id.home_progress);
        btnLaporan = findViewById(R.id.home_laporan_keuangan);
    }

    void BtnClick(){

        btnKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KegiatanPkm_Activity.class);
                startActivity(intent);
            }
        });
        btnDataUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataUsaha.class);
                startActivity(intent);
            }
        });
        btnProgres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
                startActivity(intent);
            }
        });
        btnLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LaporanKeuangan.class);
                startActivity(intent);
            }
        });
    }
}