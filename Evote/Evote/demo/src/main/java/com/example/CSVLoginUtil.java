package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoginUtil {
    private static final String CSV_FILE = "Evote\\demo\\src\\main\\resources\\dataLogin.csv";

    public static List<Login> readUsersFromCSV() {
        List<Login> logins = new ArrayList<>();
        String line;
        
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String username = values[0].trim();
                    String password = values[1].trim();
                    String role = values[2].trim();
                    logins.add(new Login(username, password, role));
                    System.out.println("Read user: " + logins);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return logins;
    }
}
