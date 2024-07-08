package com.example;

public class PantauPemilihan {
    private String nomorIdentitas;
    private String namaLengkap;
    private String waktuPemilihan;
    private String status;

    public PantauPemilihan(String nomorIdentitas, String namaLengkap, String waktuPemilihan, String status) {
        this.nomorIdentitas = nomorIdentitas;
        this.namaLengkap = namaLengkap;
        this.waktuPemilihan = waktuPemilihan;
        this.status = status;
    }

    public String getNomorIdentitas() {
        return nomorIdentitas;
    }

    public void setNomorIdentitas(String nomorIdentitas) {
        this.nomorIdentitas = nomorIdentitas;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getWaktuPemilihan() {
        return waktuPemilihan;
    }

    public void setWaktuPemilihan(String waktuPemilihan) {
        this.waktuPemilihan = waktuPemilihan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
