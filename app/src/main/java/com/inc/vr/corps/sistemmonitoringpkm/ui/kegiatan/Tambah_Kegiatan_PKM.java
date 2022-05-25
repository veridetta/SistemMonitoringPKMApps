package com.inc.vr.corps.sistemmonitoringpkm.ui.kegiatan;

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

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tambah_Kegiatan_PKM extends AppCompatActivity {

    EditText etNama, etHari, etTanggal, etWaktu;
    Spinner spJenis;
    Button btnSimpan, btnBatal;
    String idUser, namaUser, idKegiatan, iHari, iTanggal, iWaktu, iJenis;
    Boolean ubah;
    String[] kategori = {"Catering","Frozen Food", "Bisnis Kursus Memasak","Bisnis Jahit Menjahit"
            ,"Bisnis Tanaman"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan_pkm);
        initView();
        initSpinner();
        initPreferences();
        btnClick();
        initPreferences();
        initIntent();
    }
    void initView(){
        etNama = findViewById(R.id.tx_nama_keluarga);
        etHari = findViewById(R.id.tx_hari);
        etTanggal = findViewById(R.id.tx_tanggal);
        etWaktu = findViewById(R.id.tx_waktu_pelaksanaan);
        spJenis = findViewById(R.id.sp_jenis_kegiatan);
        btnBatal = findViewById(R.id.btn_cancel);
        btnSimpan = findViewById(R.id.btn_simpan);
    }
    void initSpinner(){
        // catering, frozen food, bisnis kursus memasak, bisnis jahit menjahit, bisnis  tanaman
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenis.setAdapter(adapter);
    }
    void btnClick(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama.getText().toString().isEmpty()){
                    etNama.setError("Nama Kegiatan tidak boleh kosong");
                }else if(etHari.getText().toString().isEmpty()){
                    etHari.setError("Hari tidak boleh kosong");
                }else if(etTanggal.getText().toString().isEmpty()){
                    etTanggal.setError("Tanggal tidak boleh kosong");
                }else if(etWaktu.getText().toString().isEmpty()){
                    etWaktu.setError("Waktu tidak boleh kosong");
                }else{
                    String nama = etNama.getText().toString();
                    String hari = etHari.getText().toString();
                    String tanggal = etTanggal.getText().toString();
                    String waktu = etWaktu.getText().toString();
                    String jenis = spJenis.getSelectedItem().toString();
                    String id = idUser;
                    postKegiatan(nama, hari, tanggal, waktu, jenis);
                }
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
    void initIntent(){
        idKegiatan = getIntent().getStringExtra("id");
        iJenis = getIntent().getStringExtra("jenis");
        iHari = getIntent().getStringExtra("hari");
        iTanggal = getIntent().getStringExtra("tanggal");
        iWaktu = getIntent().getStringExtra("waktu");
        ubah = getIntent().getBooleanExtra("ubah",false);
        if(ubah){
            etHari.setText(iHari);
            //check if Ijenis on string kategori
            for (int i=0;i<kategori.length;i++){
                if (kategori[i].toString().equals(iJenis)){
                    spJenis.setSelection(i);
                }
            }
            etTanggal.setText(iTanggal);
            etWaktu.setText(iWaktu);
        }
    }
    void postKegiatan(String nama, String hari, String tanggal, String waktu, String jenis){
        ApiClient client = new ApiClient();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        client.getServies().postKegiatan(idUser,"buat",nama, jenis,tanggal,hari,waktu,"").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    KegiatanResponse responseModel = (KegiatanResponse) response.body();
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