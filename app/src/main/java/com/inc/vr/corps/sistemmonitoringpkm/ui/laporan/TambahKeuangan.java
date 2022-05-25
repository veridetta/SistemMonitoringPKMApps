package com.inc.vr.corps.sistemmonitoringpkm.ui.laporan;

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
import com.inc.vr.corps.sistemmonitoringpkm.model.LaporanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.ui.kegiatan.KegiatanPkm_Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKeuangan extends AppCompatActivity {
    EditText etJumlah, etKet, etTanggal;
    Spinner spJenis, spKategori;
    Button btnSimpan, btnBatal;
    String idUser, namaUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_keuangan);
        initView();
        initSpinner();
        initPreferences();
        btnClick();
    }
    void initView(){
        etJumlah = findViewById(R.id.tx_jumlah);
        etKet = findViewById(R.id.tx_keterangan);
        etTanggal = findViewById(R.id.tx_tanggal);
        spJenis = findViewById(R.id.sp_jenis_laporan);
        spKategori = findViewById(R.id.sp_kategori);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnBatal = findViewById(R.id.btn_cancel);
    }
    void initSpinner(){
        // catering, frozen food, bisnis kursus memasak, bisnis jahit menjahit, bisnis  tanaman
        String[] kategori = {"Catering","Frozen Food", "Bisnis Kursus Memasak","Bisnis Jahit Menjahit"
                ,"Bisnis Tanaman"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategori.setAdapter(adapter);
        String[] jenis = {"Pendapatan","Pengeluaran"};
        ArrayAdapter<String> jAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jenis);
        jAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenis.setAdapter(jAdapter);
    }
    void btnClick(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etJumlah.getText().toString().isEmpty()){
                    etJumlah.setError("Jumlah tidak boleh kosong");
                }else if (etKet.getText().toString().isEmpty()){
                    etKet.setError("Keterangan tidak boleh kosong");
                }else if (etTanggal.getText().toString().isEmpty()){
                    etTanggal.setError("Tanggal tidak boleh kosong");
                }else {
                    String tanggal = etTanggal.getText().toString();
                    String jenis = spJenis.getSelectedItem().toString();
                    String kategori = spKategori.getSelectedItem().toString();
                    String keterangan = etKet.getText().toString();
                    String jumlah = etJumlah.getText().toString();
                    postLaporan(tanggal, kategori,keterangan,jenis);
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
    }
    void postLaporan(String tanggal, String kategori, String keterangan, String jenis){
        String pemasukan = "0";
        String pengeluaran = "0";
        if (jenis.equals("Pendapatan")){
            pemasukan = etJumlah.getText().toString();
        }else{
            pengeluaran = etJumlah.getText().toString();
        }
        ApiClient client = new ApiClient();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        client.getServies().postLaporan(idUser,"buat",tanggal,kategori,keterangan,pemasukan,pengeluaran,
                "0","","").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    LaporanResponse responseModel = (LaporanResponse) response.body();
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
                                Intent intent = new Intent(getApplicationContext(), LaporanKeuangan.class);
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