package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.example.PemilihController;

public class PemilihController {

    @FXML
    private TableView<Pemilih> Table;

    @FXML
    private TableColumn<Pemilih, String> kolomID;

    @FXML
    private TableColumn<Pemilih, String> kolomNAMA;

    @FXML
    private TableColumn<Pemilih, String> kolomALAMAT;

    @FXML
    private TableColumn<Pemilih, String> kolomEMAIL;

    @FXML
    private TableColumn<Pemilih, String> kolomSTATUS;

    @FXML
    private TextField kolomIDInput;

    @FXML
    private TextField kolomNAMAInput;

    @FXML
    private TextField kolomALAMATInput;

    @FXML
    private TextField kolomEMAILInput;

    @FXML
    private TextField kolomSTATUSInput;

    @FXML
    private TextField kolomSearch;

    private ObservableList<Pemilih> pemilihList;
    private static final String FILE_PATH = "Evote\\demo\\src\\main\\resources\\dataPemilih.csv";

    public void initialize() {
        kolomID.setCellValueFactory(new PropertyValueFactory<>("NOID"));
        kolomNAMA.setCellValueFactory(new PropertyValueFactory<>("NAMA"));
        kolomALAMAT.setCellValueFactory(new PropertyValueFactory<>("ALAMAT"));
        kolomEMAIL.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
        kolomSTATUS.setCellValueFactory(new PropertyValueFactory<>("STATUS"));

        pemilihList = FXCollections.observableArrayList();

        loadDataFromCSV(); // Load data from CSV when application starts

        Table.setItems(pemilihList);
    }

    @FXML
    private void tambahData() {
        Pemilih newPemilih = new Pemilih(kolomIDInput.getText(), kolomNAMAInput.getText(), kolomALAMATInput.getText(), kolomEMAILInput.getText(), kolomSTATUSInput.getText());
        pemilihList.add(newPemilih);
        saveDataToCSV(); // Save to CSV after adding data
        clearInputFields();
    }

    @FXML
    private void editData() {
        Pemilih selected = Table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNOID(kolomIDInput.getText());
            selected.setNAMA(kolomNAMAInput.getText());
            selected.setALAMAT(kolomALAMATInput.getText());
            selected.setEMAIL(kolomEMAILInput.getText());
            selected.setSTATUS(kolomSTATUSInput.getText());
            Table.refresh();
            saveDataToCSV(); // Save to CSV after editing data
            clearInputFields();
        }
    }

    @FXML
    private void deleteData() {
        Pemilih selected = Table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            pemilihList.remove(selected);
            saveDataToCSV(); // Save to CSV after deleting data
        }
    }

    @FXML
    private void searchData() {
        String searchTerm = kolomSearch.getText().toLowerCase();
        ObservableList<Pemilih> filteredList = FXCollections.observableArrayList();

        for (Pemilih pemilih : pemilihList) {
            if (pemilih.getNOID().toLowerCase().contains(searchTerm) ||
                    pemilih.getNAMA().toLowerCase().contains(searchTerm) ||
                    pemilih.getALAMAT().toLowerCase().contains(searchTerm) ||
                    pemilih.getEMAIL().toLowerCase().contains(searchTerm) ||
                    pemilih.getSTATUS().toLowerCase().contains(searchTerm)) {
                filteredList.add(pemilih);
            }
        }

        Table.setItems(filteredList);
    }

    private void clearInputFields() {
        kolomIDInput.clear();
        kolomNAMAInput.clear();
        kolomALAMATInput.clear();
        kolomEMAILInput.clear();
        kolomSTATUSInput.clear();
    }

    @FXML
    public void saveDataToCSV() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write("NOID,NAMA,ALAMAT,EMAIL\n"); // write header
            for (Pemilih pemilih : pemilihList) {
                writer.write(pemilih.getNOID() + "," + pemilih.getNAMA() + "," + pemilih.getALAMAT() + "," + pemilih.getEMAIL() + "," + pemilih.getSTATUS() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadDataFromCSV() {
        List<Pemilih> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) { // ensure data is valid
                    Pemilih pemilih = new Pemilih(data[0], data[1], data[2], data[3], data[4]);
                    list.add(pemilih);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pemilihList.clear();
        pemilihList.addAll(list);
        Table.setItems(pemilihList);
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
    void petugasButton(ActionEvent event) {
        switchToScene(event, "petugas-view.fxml");
    }

}
