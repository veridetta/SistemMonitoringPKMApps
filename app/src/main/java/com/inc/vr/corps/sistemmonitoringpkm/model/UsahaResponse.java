package com.inc.vr.corps.sistemmonitoringpkm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsahaResponse {
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
        String minat;
        String no_hp;
        String tanggal;
        String name;

        //Getter Methode
        public String getId(){return id;}
        public String getMinat(){return minat;}
        public String getNo_hp(){return no_hp;}
        public String getTanggal(){return tanggal;}
        public String getId_user(){return id_user;}
        public String getName(){return name;}


        //Setter Methode
        void setId(String id){this.id=id;}
        void setName(String name){this.name=name;}
        void setNo_hp(String no_hp){this.no_hp=no_hp;}
        void setMinat(String minat){this.minat=minat;}
        void setTanggal(String tanggal){this.tanggal=tanggal;}
        void setId_user(String id_user){this.id_user=id_user;}

    }
}

