package com.inc.vr.corps.sistemmonitoringpkm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse {
    String status;
    String pesan;

    //Getter Methode
    public String getStatus(){return status;}
    public String getPesan(){return pesan;}

    //Setter Methode
    void setStatus(String status){this.status=status;}
    void setPesan(String pesan){this.pesan=pesan;}
}

