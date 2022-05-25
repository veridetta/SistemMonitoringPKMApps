package com.inc.vr.corps.sistemmonitoringpkm.ui.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.api.ApiClient;
import com.inc.vr.corps.sistemmonitoringpkm.model.KegiatanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.ProgresResponse;
import com.inc.vr.corps.sistemmonitoringpkm.ui.kegiatan.KegiatanPkm_Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProgress extends AppCompatActivity {
    EditText etNama, etJenis,etPendapatan;
    Spinner spStatus;
    Button btnSimpan, btnBatal;
    String idUser, namaUser, id, namaKegiatan;
    Boolean ubah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_progress);
        initView();
        initSpinner();
        initGetIntent();
        initPreferences();
        btnClick();
    }
    void initView(){
        etNama = findViewById(R.id.tx_nama_keluarga);
        etJenis = findViewById(R.id.tx_jenis_usaha);
        etPendapatan = findViewById(R.id.tx_pendapatan);
        spStatus = findViewById(R.id.sp_status);
        btnBatal = findViewById(R.id.btn_cancel);
        btnSimpan = findViewById(R.id.btn_simpan);
    }
    void initSpinner(){
        // catering, frozen food, bisnis kursus memasak, bisnis jahit menjahit, bisnis  tanaman
        String[] kategori = {"Belum","Proses", "Selesai"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapter);
    }
    void btnClick(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = spStatus.getSelectedItem().toString();
                String pendapatan = etPendapatan.getText().toString();
                postKegiatan(status, pendapatan);
            }
        });
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    void initPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        idUser = sharedPreferences.getString("id","");
        namaUser = sharedPreferences.getString("nama","");
        etNama.setText(namaUser);
        etNama.setEnabled(false);
    }

    void initGetIntent(){
        id = getIntent().getStringExtra("id");
        namaKegiatan = getIntent().getStringExtra("namaKegiatan");
        etJenis.setText(namaKegiatan);
    }

    void postKegiatan(String status, String pendapatan){
        ApiClient client = new ApiClient();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        client.getServies().postProgres(idUser,"buat",namaKegiatan,pendapatan,status).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    ProgresResponse responseModel = (ProgresResponse) response.body();
                    if (responseModel.getStatus().equals("sukses")) {
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("Sukses");
                        sweetAlertDialog.setContentText(responseModel.getPesan());
                        sweetAlertDialog.setConfirmText("OK");
                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                Intent intent = new Intent(getApplicationContext(), KegiatanPkm_Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("Gagal");
                        sweetAlertDialog.setContentText(responseModel.getPesan());
                        sweetAlertDialog.setConfirmText("OK");
                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });
                    }
                }else{
                    Log.d("Get Kegiatan", "onResponse: " + response.message());
                    //jika login gagal
                    sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Gagal");
                    sweetAlertDialog.setContentText(response.message());
                    sweetAlertDialog.setConfirmText("OK");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Get Kegiatan Gagal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Get Kegiatan", "onFailure: " + t.getMessage());
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Gagal");
                sweetAlertDialog.setContentText("Tidak ada data");
                sweetAlertDialog.setConfirmText("OK");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                Toast.makeText(getApplicationContext(), "Get Kegiatan Gagal 2", Toast.LENGTH_LONG).show();
            }
        });
    }
}