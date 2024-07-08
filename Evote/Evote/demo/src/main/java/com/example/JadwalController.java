package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class JadwalController {
    
    @FXML
    private Button editDataButton;

    @FXML
    private Button hapusDataButton;

    @FXML
    private DatePicker kolomDatePicker;

    @FXML
    private TableView<Jadwal> tableView;

    @FXML
    private TableColumn<Jadwal, String> kolomTanggal;

    @FXML
    private Button tambahDataButton;

    private ObservableList<Jadwal> jadwalList;

    private static final String FILE_PATH = "Evote\\demo\\src\\main\\resources\\dataJadwal.csv";
    
    public void initialize() {
        jadwalList = FXCollections.observableArrayList();

        kolomTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggal().toString()));

        tableView.setItems(jadwalList);
        
        tambahDataButton.setOnAction(event -> tambahData());
        hapusDataButton.setOnAction(event -> hapusData());
        editDataButton.setOnAction(event -> edit());

        loadDataFromCSV(); // Load data from CSV when application starts
    }

    private void switchToScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dataPemilihButton(ActionEvent event) {
        switchToScene(event, "verifPemilih-view.fxml");
    }

    @FXML
    void kandidatButton(ActionEvent event) {
        switchToScene(event, "kandidat-view.fxml");
    }

    @FXML
    void kotakSuaraButton(ActionEvent event) {
        switchToScene(event, "pantauPemilihan-view.fxml");
    }

    @FXML
    private void tambahData() {
        LocalDate tanggal = kolomDatePicker.getValue();
        if (tanggal != null && !jadwalList.contains(new Jadwal(tanggal))) {
            jadwalList.add(new Jadwal(tanggal));
            kolomDatePicker.setValue(null);
            saveDataToCSV(); // Save data to CSV after adding new entry
        }
    }

    @FXML
    private void hapusData() {
        Jadwal selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            jadwalList.remove(selectedItem);
            saveDataToCSV(); // Save data to CSV after removing entry
        }
    }

    @FXML
    private void edit() {
        Jadwal selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            LocalDate newDate = kolomDatePicker.getValue();
            if (newDate != null && !jadwalList.contains(new Jadwal(newDate))) {
                selectedItem.setTanggal(newDate);
                tableView.refresh();
                saveDataToCSV(); // Save data to CSV after editing entry
                kolomDatePicker.setValue(null);
            }
        }
    }

    private void saveDataToCSV() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Jadwal jadwal : jadwalList) {
                writer.write(jadwal.getTanggal().toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromCSV() {
        List<Jadwal> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // If file does not exist, return
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocalDate tanggal = LocalDate.parse(line);
                list.add(new Jadwal(tanggal));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jadwalList.clear();
        jadwalList.addAll(list);
        tableView.setItems(jadwalList);
    }
}
