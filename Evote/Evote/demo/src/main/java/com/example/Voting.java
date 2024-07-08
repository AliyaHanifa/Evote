package com.example;

import java.util.HashMap;
import java.util.Map;

public class Voting {
    private Map<String, Integer> suara;

    public Voting() {
        this.suara = new HashMap<>();
    }

    public void tambahSuara(String namaKandidat) {
        suara.put(namaKandidat, suara.getOrDefault(namaKandidat, 0) + 1);
    }

    public int getSuara(String namaKandidat) {
        return suara.getOrDefault(namaKandidat, 0);
    }

    public Map<String, Integer> getSemuaSuara() {
        return suara;
    }
}
