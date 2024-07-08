package com.example;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

public class CSVJadwalUtil {

    public static List<Jadwal> readCSV(String filePath) {
        List<Jadwal> jadwalList = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return jadwalList; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocalDate tanggal = LocalDate.parse(line);
                jadwalList.add(new Jadwal(tanggal));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jadwalList;
    }

    public static void writeCSV(String filePath, ObservableList<Jadwal> jadwalList) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Jadwal jadwal : jadwalList) {
                writer.write(jadwal.getTanggal().toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
