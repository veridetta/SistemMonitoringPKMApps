package com.inc.vr.corps.sistemmonitoringpkm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaporanResponse {
    String status;
    String pesan;
    @SerializedName("data")
    List<DataResult> data;
    //Getter Methode
    public String getStatus(){return status;}
    public String getPesan(){return pesan;}
    public List<DataResult> getData(){return data;}
    //Setter Methode
    void setStatus(String status){this.status=status;}
    void setPesan(String pesan){this.pesan=pesan;}
    void setData(List<DataResult> data){this.data=data;}


    public class DataResult {
        String id;
        String id_user;
        String kategori;
        String alamat;
        String totalPemasukan;
        String totalPengeluaran;
        String totalSaldo;
        String tanggal;
        String keterangan;
        String tipe;
        String nilai;

        //Getter Methode
        public String getId(){return id;}
        public String getId_user(){return id_user;}
        public String getKategori(){return kategori;}
        public String getAlamat(){return alamat;}
        public String getTotalPemasukan(){return totalPemasukan;}
        public String getTotalPengeluaran(){return totalPengeluaran;}
        public String getTotalSaldo(){return totalSaldo;}
        public String getTanggal(){return tanggal;}
        public String getKeterangan(){return keterangan;}
        public String getTipe(){return tipe;}
        public String getNilai(){return nilai;}


        //Setter Methode
        void setId(String id){this.id=id;}
        void setId_user(String id_user){this.id_user=id_user;}
        void setKategori(String kategori){this.kategori=kategori;}
        void setAlamat(String alamat){this.alamat=alamat;}
        void setTotalPemasukan(String totalPemasukan){this.totalPemasukan=totalPemasukan;}
        void setTotalPengeluaran(String totalPengeluaran){this.totalPengeluaran=totalPengeluaran;}
        void setTotalSaldo(String totalSaldo){this.totalSaldo=totalSaldo;}
        void setTanggal(String tanggal){this.tanggal=tanggal;}
        void setKeterangan(String keterangan){this.keterangan=keterangan;}
        void setTipe(String tipe){this.tipe=tipe;}
        void setNilai(String nilai){this.nilai=nilai;}



    }
}

