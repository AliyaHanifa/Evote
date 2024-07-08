package com.example;

import java.time.LocalDate;

public class Jadwal {
    private LocalDate tanggal;

    public Jadwal(LocalDate tanggal) {
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
