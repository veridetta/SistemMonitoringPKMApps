package com.inc.vr.corps.sistemmonitoringpkm.ui.progress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.adapter.LaporanAdapter;
import com.inc.vr.corps.sistemmonitoringpkm.adapter.ProgresAdapter;
import com.inc.vr.corps.sistemmonitoringpkm.api.ApiClient;
import com.inc.vr.corps.sistemmonitoringpkm.model.LaporanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.ProgresResponse;
import com.inc.vr.corps.sistemmonitoringpkm.ui.kegiatan.KegiatanPkm_Activity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressActivity extends AppCompatActivity
        implements ProgresAdapter.ContactsAdapterListener{
    FloatingActionButton btnAdd;
    String idUser;
    private RecyclerView recyclerView;
    private List<ProgresResponse.DataResult> kegiatanList;
    private ProgresAdapter mAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        header();
        initView();
        initPreferences();
        initClick();
        recyclerView();
        getProgres(idUser);
    }
    void header(){
        // toolbar fancy stuff
        getSupportActionBar().setTitle("Prgores");
        //requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
    void recyclerView(){
        recyclerView = findViewById(R.id.rc_data);
        kegiatanList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1, RecyclerView.VERTICAL, false);
        mAdapter = new ProgresAdapter(this, kegiatanList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
    void initView(){
        btnAdd = findViewById(R.id.btn_add);

    }
    void initPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        idUser = sharedPreferences.getString("id","");
    }
    void initClick(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgressActivity.this, KegiatanPkm_Activity.class);
                startActivity(intent);
            }
        });

    }

    void getProgres(String id){
        ApiClient client = new ApiClient();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        client.getServies().getProgres(id,"get").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    ProgresResponse responseModel = (ProgresResponse) response.body();
                    if (responseModel.getStatus().equals("sukses")) {
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        List<ProgresResponse.DataResult> result = responseModel.getData();
                        kegiatanList.clear();
                        kegiatanList.addAll(result);
                        mAdapter.notifyDataSetChanged();
                        sweetAlertDialog.dismissWithAnimation();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(ProgresResponse.DataResult contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getJenis_usaha() + ", "
                + contact.getLaporan(), Toast.LENGTH_LONG).show();
    }
}