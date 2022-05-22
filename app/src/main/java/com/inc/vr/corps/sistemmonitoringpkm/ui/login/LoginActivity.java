package com.inc.vr.corps.sistemmonitoringpkm.ui.login;

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

import androidx.appcompat.app.AppCompatActivity;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.api.ApiClient;
import com.inc.vr.corps.sistemmonitoringpkm.model.LoginModel;
import com.inc.vr.corps.sistemmonitoringpkm.ui.main.MainActivity;
import com.inc.vr.corps.sistemmonitoringpkm.ui.register.RegisterActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        btnClick();
    }

    void initView(){
        etUsername = findViewById(R.id.tx_username);
        etPassword = findViewById(R.id.tx_password);
        btnLogin = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_register);

    }

    void btnClick(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (validateLogin(username, password)){
                    if (cekKoneksi(LoginActivity.this)){
                        dologin(username, password);
                    }
                }
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    boolean validateLogin(String username, String password){
        Boolean status = false;
        if(username.isEmpty() || password.isEmpty()){
            //show error
            Toast.makeText(LoginActivity.this, "Username or Password is empty", Toast.LENGTH_SHORT).show();
            status = false;
        }else{
            //check if username and password is correct
            status = true;
        }
        return status;
    }
    private void dologin(String xusername, String xpassword){
        ApiClient client = new ApiClient();
        client.getServies().login(xusername, xpassword,"android").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    LoginModel responseModel = (LoginModel) response.body();
                    if (responseModel.getStatus().equals("sukses")) {
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        List<LoginModel.LoginRsult> result = responseModel.getData();
                        Log.d("Login", "onResponse: " + result.get(0).getId());
                        //save value to sharedpreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", result.get(0).getId());
                        editor.putString("username", xusername);
                        editor.putString("password", xpassword);
                        editor.putString("nama", result.get(0).getName());
                        editor.putString("role", result.get(0).getRole());
                        //isLogin
                        editor.putBoolean("isLogin", true);
                        editor.apply();
                        //pindah activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("Login", "onResponse: " + response.message());
                    //jika login gagal
                    Toast.makeText(getApplicationContext(), "Login Gagal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Login", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Login Gagal 2", Toast.LENGTH_LONG).show();
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