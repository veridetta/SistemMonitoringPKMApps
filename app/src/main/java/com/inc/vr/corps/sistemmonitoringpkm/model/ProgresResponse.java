package com.inc.vr.corps.sistemmonitoringpkm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProgresResponse {
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
        String jenis_usaha;
        String alamat;
        String no_hp;
        String pendapatan;
        String laporan;
        String name;

        //Getter Methode
        public String getId(){return id;}
        public String getId_user(){return id_user;}
        public String getJenis_usaha(){return jenis_usaha;}
        public String getAlamat(){return alamat;}
        public String getNo_hp(){return no_hp;}
        public String getPendapatan(){return pendapatan;}
        public String getLaporan(){return laporan;}
        public String getName(){return name;}


        //Setter Methode
        void setId(String id){this.id=id;}
        void setName(String name){this.name=name;}
        void setId_user(String id_user){this.id_user=id_user;}
        void setJenis_usaha(String jenis_usaha){this.jenis_usaha=jenis_usaha;}
        void setAlamat(String alamat){this.alamat=alamat;}
        void setNo_hp(String no_hp){this.no_hp=no_hp;}
        void setPendapatan(String pendapatan){this.pendapatan=pendapatan;}
        void setLaporan(String laporan){this.laporan=laporan;}

    }
}

