package com.inc.vr.corps.sistemmonitoringpkm.model;

public class UserModel {
    private String id;
    private String nip;
    private String nama;
    private String pangkat;
    private String id_golongan;
    private String jabatan;
    private String unitkerja;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public String getPangkat() {
        return pangkat;
    }

    public String getId_golongan() {
        return id_golongan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getUnitkerja() {
        return unitkerja;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setPangkat(String pangkat) {
        this.pangkat = pangkat;
    }

    public void setId_golongan(String id_golongan) {
        this.id_golongan = id_golongan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public void setUnitkerja(String unitkerja) {
        this.unitkerja = unitkerja;
    }
}

