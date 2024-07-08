package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VerifPemilihController {

    @FXML
    private TableView<VerifPemilih> Table;

    @FXML
    private TableColumn<VerifPemilih, String> kolomID;

    @FXML
    private TableColumn<VerifPemilih, String> kolomNAMA;

    @FXML
    private TableColumn<VerifPemilih, String> kolomALAMAT;

    @FXML
    private TableColumn<VerifPemilih, String> kolomEMAIL;

    @FXML
    private TableColumn<VerifPemilih, String> kolomSTATUS;

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

    @FXML
    private Button searchButton;

    private ObservableList<VerifPemilih> verifPemilihList;
    private static final String FILE_PATH = "Evote\\demo\\src\\main\\resources\\dataPemilih.csv";

    public void initialize() {
        kolomID.setCellValueFactory(new PropertyValueFactory<>("NOID"));
        kolomNAMA.setCellValueFactory(new PropertyValueFactory<>("NAMA"));
        kolomALAMAT.setCellValueFactory(new PropertyValueFactory<>("ALAMAT"));
        kolomEMAIL.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
        kolomSTATUS.setCellValueFactory(new PropertyValueFactory<>("STATUS"));

        verifPemilihList = FXCollections.observableArrayList();
        loadDataFromCSV(); // Load data from CSV when application starts
        Table.setItems(verifPemilihList);
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
    void jadwalButton(ActionEvent event) {
        switchToScene(event, "jadwal-view.fxml");
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
        VerifPemilih newVerifPemilih = new VerifPemilih(
            kolomIDInput.getText(),
            kolomNAMAInput.getText(),
            kolomALAMATInput.getText(),
            kolomEMAILInput.getText(),
            kolomSTATUSInput.getText()
        );
        verifPemilihList.add(newVerifPemilih);
        saveDataToCSV(); // Save to CSV after adding data
        clearInputFields();
    }

    @FXML
    private void editData() {
        VerifPemilih selected = Table.getSelectionModel().getSelectedItem();
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
        VerifPemilih selected = Table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            verifPemilihList.remove(selected);
            saveDataToCSV(); // Save to CSV after deleting data
        }
    }

    @FXML
    private void searchData() {
        String searchTerm = kolomSearch.getText().toLowerCase();
        ObservableList<VerifPemilih> filteredList = FXCollections.observableArrayList();

        for (VerifPemilih verifPemilih : verifPemilihList) {
            if (verifPemilih.getNOID().toLowerCase().contains(searchTerm) ||
                verifPemilih.getNAMA().toLowerCase().contains(searchTerm) ||
                verifPemilih.getALAMAT().toLowerCase().contains(searchTerm) ||
                verifPemilih.getEMAIL().toLowerCase().contains(searchTerm) ||
                verifPemilih.getSTATUS().toLowerCase().contains(searchTerm)) {
                filteredList.add(verifPemilih);
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
            writer.write("NOID,NAMA,ALAMAT,EMAIL,STATUS\n"); // write header
            for (VerifPemilih verifPemilih : verifPemilihList) {
                writer.write(
                    verifPemilih.getNOID() + "," +
                    verifPemilih.getNAMA() + "," +
                    verifPemilih.getALAMAT() + "," +
                    verifPemilih.getEMAIL() + "," +
                    verifPemilih.getSTATUS() + "\n"
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadDataFromCSV() {
        List<VerifPemilih> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) { // ensure data is valid
                    VerifPemilih verifPemilih = new VerifPemilih(data[0], data[1], data[2], data[3], data[4]);
                    list.add(verifPemilih);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        verifPemilihList.clear();
        verifPemilihList.addAll(list);
        Table.setItems(verifPemilihList);
    }
}
