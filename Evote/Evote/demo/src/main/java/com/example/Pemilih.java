package com.example;

public class Pemilih {
    private String NOID;
    private String NAMA;
    private String ALAMAT;
    private String EMAIL;
    private String STATUS;

    public Pemilih() {}

    public Pemilih(String NOID, String NAMA, String ALAMAT, String EMAIL, String STATUS) {
        this.NOID = NOID;
        this.NAMA = NAMA;
        this.ALAMAT = ALAMAT;
        this.EMAIL = EMAIL;
        this.STATUS = STATUS;
    }

    public String getNOID() {
        return NOID;
    }

    public void setNOID(String NOID) {
        this.NOID = NOID;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public String getALAMAT() {
        return ALAMAT;
    }

    public void setALAMAT(String ALAMAT) {
        this.ALAMAT = ALAMAT;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
