package com.example;

import java.time.LocalDate;

public class MelihatJadwal {
    private LocalDate tanggal;

    public MelihatJadwal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return tanggal.toString();
    }
}
