package com.inc.vr.corps.sistemmonitoringpkm.api;

import com.inc.vr.corps.sistemmonitoringpkm.model.KegiatanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.LaporanResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.LoginModel;
import com.inc.vr.corps.sistemmonitoringpkm.model.ProgresResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.RegisterResponse;
import com.inc.vr.corps.sistemmonitoringpkm.model.UsahaResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;

public interface ApiService {
    //public static final String BASE_URL = "http://192.168.43.235/pkm/api/";
    public static final String BASE_URL = "https://pkm.epogame.my.id/api/";
    public static final String LOGIN_URL = BASE_URL + "login.php";
    public static final String REGISTER = BASE_URL + "register.php";
    public static final String GET_KEGIATAN = BASE_URL + "/kegiatan/kegiatan_action.php";
    public static final String GET_USAHA = BASE_URL + "/usaha/usaha_action.php";
    public static final String GET_PROGRES = BASE_URL + "/progres/progres_action.php";
    public static final String GET_LAPORAN = BASE_URL + "/laporan/laporan_action.php";

    //Form Login
    @FormUrlEncoded
    @retrofit2.http.POST(LOGIN_URL)
    Call<LoginModel> login(
            @retrofit2.http.Field("username") String username,
            @retrofit2.http.Field("password") String password,
            @retrofit2.http.Field("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(REGISTER)
    Call<RegisterResponse> register(
            @retrofit2.http.Field("username") String username,
            @retrofit2.http.Field("password") String password,
            @retrofit2.http.Field("email") String email,
            @retrofit2.http.Field("name") String name,
            @retrofit2.http.Field("role") String role,
            @retrofit2.http.Field("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(GET_KEGIATAN)
    Call<KegiatanResponse> postKegiatan(
            @retrofit2.http.Field("id_user") String id_user,
            @retrofit2.http.Field("apps") String apps,
            @retrofit2.http.Field("jenis") String jenis,
            @retrofit2.http.Field("nama") String nama,
            @retrofit2.http.Field("tanggal") String tanggal,
            @retrofit2.http.Field("hari") String hari,
            @retrofit2.http.Field("waktu") String waktu,
            @retrofit2.http.Field("id") String id
    );
    @FormUrlEncoded
    @retrofit2.http.POST(GET_KEGIATAN)
    Call<KegiatanResponse> getKegiatan(
            @retrofit2.http.Field("id_user") String id_user,
            @retrofit2.http.Field("bulan") String bulan,
            @retrofit2.http.Field("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(GET_USAHA)
    Call<UsahaResponse> getUsaha(
            @retrofit2.http.Field("id_user") String id_user,
            @retrofit2.http.Field("bulan") String bulan,
            @retrofit2.http.Field("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(GET_PROGRES)
    Call<ProgresResponse> getProgres(
            @retrofit2.http.Field("id_user") String id_user,
            @retrofit2.http.Field("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(GET_PROGRES)
    Call<ProgresResponse> postProgres(
            @retrofit2.http.Field("id_user") String id_user,
            @retrofit2.http.Field("apps") String apps,
            @retrofit2.http.Field("jenis_usaha") String jenis_usaha,
            @retrofit2.http.Field("pendapatan") String pendapatan,
            @retrofit2.http.Field("laporan") String laporan
    );
    @FormUrlEncoded
    @retrofit2.http.POST(GET_LAPORAN)
    Call<LaporanResponse> getLaporan(
            @retrofit2.http.Field("id_user") String id_user,
            @retrofit2.http.Field("groupBy") String groupby,
            @retrofit2.http.Field("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(GET_LAPORAN)
    Call<LaporanResponse> postLaporan(
            @retrofit2.http.Field("id_user") String id_user,
            @retrofit2.http.Field("apps") String apps,
            @retrofit2.http.Field("tanggal") String tanggal,
            @retrofit2.http.Field("kategori") String kategori,
            @retrofit2.http.Field("keterangan") String keterangan,
            @retrofit2.http.Field("pemasukan") String pemasukan,
            @retrofit2.http.Field("pengeluaran") String pengeluaran,
            @retrofit2.http.Field("saldo") String saldo,
            @retrofit2.http.Field("alamat") String alamat,
            @retrofit2.http.Field("id") String id
    );
}
