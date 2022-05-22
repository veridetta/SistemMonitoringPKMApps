package com.inc.vr.corps.sistemmonitoringpkm.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.api.ApiClient;
import com.inc.vr.corps.sistemmonitoringpkm.model.LoginModel;
import com.inc.vr.corps.sistemmonitoringpkm.model.RegisterResponse;
import com.inc.vr.corps.sistemmonitoringpkm.ui.login.LoginActivity;
import com.inc.vr.corps.sistemmonitoringpkm.ui.main.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etPassword, etEmail, etName;
    TextView btnLogin;
    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        initView();
        btnClick();
    }

    void initView(){
        etUsername = findViewById(R.id.tx_username);
        etPassword = findViewById(R.id.tx_password);
        etEmail = findViewById(R.id.tx_email);
        etName = findViewById(R.id.tx_nama);
        btnLogin = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_register);
    }
    void btnClick(){
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                String name = etName.getText().toString();
                if(validateForm(username, password, email, name)){
                    if(cekKoneksi(RegisterActivity.this)){
                        register(username, name, password, email);
                    }
                }
            }
        });
    }
    boolean validateForm(String username, String name, String password, String email){
        Boolean valid = false;
        //check empty
        if (username.isEmpty()||name.isEmpty()||password.isEmpty()||email.isEmpty()){
            Toast.makeText(this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
            valid = false;
        }else{
            valid = true;
        }
        return valid;
    }
    private void register(String username, String name, String password, String email){
        ApiClient client = new ApiClient();
        client.getServies().register(username, password, email, name,"user","android").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    RegisterResponse responseModel = (RegisterResponse) response.body();
                    if (responseModel.getStatus().equals("sukses")) {
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        //pindah activity
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("Register", "onResponse: " + response.message());
                    //jika login gagal
                    Toast.makeText(getApplicationContext(), "Register Gagal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Login", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Register Gagal 2", Toast.LENGTH_LONG).show();
            }
        });
    }
    //fungsi cek koneksi
    public boolean cekKoneksi(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //for api 29 and above
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if(capabilities != null){
                if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    return true;
                }
                else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    return true;
                }
                else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }else{
            //for api level below 29
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                return true;
            }else{
                return false;
            }
        }
    }
}