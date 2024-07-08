package com.example;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PantauPemilihanController {

    @FXML
    private TextField kolomSearch;

    @FXML
    private Label lokasiUser;

    @FXML
    private VBox sidebar;

    @FXML
    private Label username;

    @FXML
    private TableView<PantauPemilihan> tableView;

    @FXML
    private HBox header;

    @FXML
    private TableColumn<PantauPemilihan, String> kolomNomorID;

    @FXML
    private TableColumn<PantauPemilihan, String> kolomNama;

    @FXML
    private TableColumn<PantauPemilihan, String> kolomSesi;

    @FXML
    private TableColumn<PantauPemilihan, String> kolomStatus;

    @FXML
    private Label belumMemilihLabel;

    @FXML
    private Label sudahMemilihLabel;

    private ObservableList<PantauPemilihan> pantauList = FXCollections.observableArrayList();

    public void initialize() {
        kolomNomorID.setCellValueFactory(new PropertyValueFactory<>("nomorIdentitas"));
        kolomNama.setCellValueFactory(new PropertyValueFactory<>("namaLengkap"));
        kolomSesi.setCellValueFactory(new PropertyValueFactory<>("waktuPemilihan"));
        kolomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadCSVData("Evote\\demo\\src\\main\\resources\\dataPantauPemilihan.csv"); // Load data from CSV when application starts

        tableView.setItems(pantauList);

        // Update labels initially
        updateLabels();
    }

    private void loadCSVData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    PantauPemilihan pantau = new PantauPemilihan(values[0], values[1], values[2], values[3]);
                    pantauList.add(pantau);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLabels() {
        long sudahMemilih = pantauList.stream().filter(pantau -> "Sudah".equalsIgnoreCase(pantau.getStatus())).count();
        long belumMemilih = pantauList.size() - sudahMemilih;

        sudahMemilihLabel.setText("Sudah Memilih: " + sudahMemilih);
        belumMemilihLabel.setText("Belum Memilih: " + belumMemilih);
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
    void jadwalButton(ActionEvent event) {
        switchToScene(event, "jadwal-view.fxml");
    }

    @FXML
    void kandidatButton(ActionEvent event) {
        switchToScene(event, "kandidat-view.fxml");
    }

    @FXML
    void searchData(ActionEvent event) {
        String searchTerm = kolomSearch.getText().toLowerCase();
        ObservableList<PantauPemilihan> filteredList = FXCollections.observableArrayList();

        for (PantauPemilihan pantau : pantauList) {
            if (pantau.getNomorIdentitas().toLowerCase().contains(searchTerm) ||
                pantau.getNamaLengkap().toLowerCase().contains(searchTerm) ||
                pantau.getWaktuPemilihan().toLowerCase().contains(searchTerm) ||
                pantau.getStatus().toLowerCase().contains(searchTerm)) {
                filteredList.add(pantau);
            }
        }

        tableView.setItems(filteredList);
    }
}
