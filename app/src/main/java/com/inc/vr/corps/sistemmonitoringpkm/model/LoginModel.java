package com.inc.vr.corps.sistemmonitoringpkm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginModel {
    String status;
    String pesan;
    @SerializedName("data")
    List<LoginRsult> data;
    //Getter Methode
    public String getStatus(){return status;}
    public String getPesan(){return pesan;}
    public List<LoginRsult> getData(){return data;}
    //Setter Methode
    void setStatus(String status){this.status=status;}
    void setPesan(String pesan){this.pesan=pesan;}
    void setData(List<LoginRsult> data){this.data=data;}


    public class LoginRsult {
        String id;
        String username;
        String email;
        String name;
        String role;

        //Getter Methode
        public String getId(){return id;}
        public String getUsername(){return username;}
        public String getEmail(){return email;}
        public String getName(){return name;}
        public String getRole(){return role;}

        //Setter Methode
        void setId(String id){this.id=id;}
        void setUsername(String username){this.username=username;}
        void setEmail(String email){this.email=email;}
        void setName(String name){this.name=name;}
        void setRole(String role){this.role=role;}

    }
}

