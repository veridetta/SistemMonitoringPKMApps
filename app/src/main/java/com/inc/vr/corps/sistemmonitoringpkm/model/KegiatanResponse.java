package com.inc.vr.corps.sistemmonitoringpkm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KegiatanResponse {
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
        String jenis;
        String hari;
        String tanggal;
        String waktu;
        String name;

        //Getter Methode
        public String getId(){return id;}
        public String getJenis(){return jenis;}
        public String getHari(){return hari;}
        public String getTanggal(){return tanggal;}
        public String getWaktu(){return waktu;}
        public String getId_user(){return id_user;}


        //Setter Methode
        void setId(String id){this.id=id;}
        void setName(String name){this.name=name;}
        void setJenis(String jenis){this.jenis=jenis;}
        void setHari(String hari){this.hari=hari;}
        void setTanggal(String tanggal){this.tanggal=tanggal;}
        void setWaktu(String waktu){this.waktu=waktu;}
        void setId_user(String id_user){this.id_user=id_user;}

    }
}

