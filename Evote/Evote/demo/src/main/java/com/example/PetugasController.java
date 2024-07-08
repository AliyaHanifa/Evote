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

public class PetugasController {

    @FXML
    private TableView<Petugas> Table;

    @FXML
    private TableColumn<Petugas, String> kolomID;

    @FXML
    private TableColumn<Petugas, String> kolomNAMA;

    @FXML
    private TableColumn<Petugas, String> kolomALAMAT;

    @FXML
    private TableColumn<Petugas, String> kolomEMAIL;

    @FXML
    private TableColumn<Petugas, String> kolomJENISKELAMIN;

    @FXML
    private TextField kolomIDInput;

    @FXML
    private TextField kolomNAMAInput;

    @FXML
    private TextField kolomALAMATInput;

    @FXML
    private TextField kolomEMAILInput;

    @FXML
    private TextField kolomJENISKELAMINInput;

    @FXML
    private TextField kolomSearch;

    private ObservableList<Petugas> petugasList;
    private static final String FILE_PATH = "Evote\\demo\\src\\main\\resources\\dataPetugas.csv";

    public void initialize() {
        kolomID.setCellValueFactory(new PropertyValueFactory<>("NOID"));
        kolomNAMA.setCellValueFactory(new PropertyValueFactory<>("NAMA"));
        kolomALAMAT.setCellValueFactory(new PropertyValueFactory<>("ALAMAT"));
        kolomEMAIL.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
        kolomJENISKELAMIN.setCellValueFactory(new PropertyValueFactory<>("JENISKELAMIN"));

        petugasList = FXCollections.observableArrayList();

        loadDataFromCSV(); // Load data from CSV when application starts

        Table.setItems(petugasList);
    }

    @FXML
    private void tambahData() {
        Petugas newPetugas = new Petugas(kolomIDInput.getText(), kolomNAMAInput.getText(), kolomALAMATInput.getText(), kolomEMAILInput.getText(), kolomJENISKELAMINInput.getText());
        petugasList.add(newPetugas);
        saveDataToCSV(); // Save to CSV after adding data
        clearInputFields();
    }

    @FXML
    private void editData() {
        Petugas selected = Table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNOID(kolomIDInput.getText());
            selected.setNAMA(kolomNAMAInput.getText());
            selected.setALAMAT(kolomALAMATInput.getText());
            selected.setEMAIL(kolomEMAILInput.getText());
            selected.setJENISKELAMIN(kolomJENISKELAMINInput.getText());
            Table.refresh();
            saveDataToCSV(); // Save to CSV after editing data
            clearInputFields();
        }
    }

    @FXML
    private void deleteData() {
        Petugas selected = Table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            petugasList.remove(selected);
            saveDataToCSV(); // Save to CSV after deleting data
        }
    }

    @FXML
    private void searchData() {
        String searchTerm = kolomSearch.getText().toLowerCase();
        ObservableList<Petugas> filteredList = FXCollections.observableArrayList();

        for (Petugas petugas : petugasList) {
            if (petugas.getNOID().toLowerCase().contains(searchTerm) ||
                    petugas.getNAMA().toLowerCase().contains(searchTerm) ||
                    petugas.getALAMAT().toLowerCase().contains(searchTerm) ||
                    petugas.getEMAIL().toLowerCase().contains(searchTerm) ||
                    petugas.getJENISKELAMIN().toLowerCase().contains(searchTerm)) {
                filteredList.add(petugas);
            }
        }

        Table.setItems(filteredList);
    }

    private void clearInputFields() {
        kolomIDInput.clear();
        kolomNAMAInput.clear();
        kolomALAMATInput.clear();
        kolomEMAILInput.clear();
        kolomJENISKELAMINInput.clear();
    }

    @FXML
    public void saveDataToCSV() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write("NOID,NAMA,ALAMAT,EMAIL,JENISKELAMIN\n"); // write header
            for (Petugas petugas : petugasList) {
                writer.write(petugas.getNOID() + "," + petugas.getNAMA() + "," + petugas.getALAMAT() + "," + petugas.getEMAIL() + "," + petugas.getJENISKELAMIN() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadDataFromCSV() {
        List<Petugas> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) { // ensure data is valid
                    Petugas petugas = new Petugas(data[0], data[1], data[2], data[3], data[4]);
                    list.add(petugas);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        petugasList.clear();
        petugasList.addAll(list);
        Table.setItems(petugasList);
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
    void pemilihButton(ActionEvent event) {
        switchToScene(event, "pemilih-view.fxml");
    }
}
