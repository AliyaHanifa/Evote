package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KandidatController {

    @FXML
    private TableView<Kandidat> Table;

    @FXML
    private TableColumn<Kandidat, String> kolomID;

    @FXML
    private TableColumn<Kandidat, String> kolomNAMA;

    @FXML
    private TableColumn<Kandidat, String> kolomVISIMISI;

    @FXML
    private TableColumn<Kandidat, String> kolomEMAIL;

    @FXML
    private TableColumn<Kandidat, String> kolomPARTAI;

    @FXML
    private TextField kolomIDInput;

    @FXML
    private TextField kolomNAMAInput;

    @FXML
    private TextField kolomVISIMISIInput;

    @FXML
    private TextField kolomEMAILInput;

    @FXML
    private TextField kolomPARTAIInput;

    @FXML
    private TextField kolomSearch;

    private ObservableList<Kandidat> kandidatList;
    private static final String FILE_PATH = "Evote\\demo\\src\\main\\resources\\dataKandidat.csv";

    public void initialize() {
        kolomID.setCellValueFactory(new PropertyValueFactory<>("NOID"));
        kolomNAMA.setCellValueFactory(new PropertyValueFactory<>("NAMA"));
        kolomVISIMISI.setCellValueFactory(new PropertyValueFactory<>("VISIMISI"));
        kolomEMAIL.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
        kolomPARTAI.setCellValueFactory(new PropertyValueFactory<>("PARTAI"));

        kandidatList = FXCollections.observableArrayList();

        loadDataFromCSV(); // Load data from CSV when application starts

        Table.setItems(kandidatList);
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
    void kotakSuaraButton(ActionEvent event) {
        switchToScene(event, "pantauPemilihan-view.fxml");
    }

    @FXML
    private void tambahData() {
        Kandidat newKandidat = new Kandidat();
        newKandidat.setNOID(kolomIDInput.getText());
        newKandidat.setNAMA(kolomNAMAInput.getText());
        newKandidat.setVISIMISI(kolomVISIMISIInput.getText());
        newKandidat.setEMAIL(kolomEMAILInput.getText());
        newKandidat.setPARTAI(kolomPARTAIInput.getText());
        kandidatList.add(newKandidat);
        saveDataToCSV(); // Save data to CSV after adding a new candidate
        clearInputFields();
    }

    @FXML
    private void editData() {
        Kandidat selected = Table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNOID(kolomIDInput.getText());
            selected.setNAMA(kolomNAMAInput.getText());
            selected.setVISIMISI(kolomVISIMISIInput.getText());
            selected.setEMAIL(kolomEMAILInput.getText());
            selected.setPARTAI(kolomPARTAIInput.getText());
            Table.refresh();
            saveDataToCSV(); // Save data to CSV after editing a candidate
            clearInputFields();
        }
    }

    @FXML
    private void deleteData() {
        Kandidat selected = Table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            kandidatList.remove(selected);
            saveDataToCSV(); // Save data to CSV after deleting a candidate
        }
    }

    @FXML
    private void searchData() {
        String searchTerm = kolomSearch.getText().toLowerCase();
        ObservableList<Kandidat> filteredList = FXCollections.observableArrayList();

        for (Kandidat kandidat : kandidatList) {
            if (kandidat.getNOID().toLowerCase().contains(searchTerm) ||
                    kandidat.getNAMA().toLowerCase().contains(searchTerm) ||
                    kandidat.getVISIMISI().toLowerCase().contains(searchTerm) ||
                    kandidat.getEMAIL().toLowerCase().contains(searchTerm) ||
                    kandidat.getPARTAI().toLowerCase().contains(searchTerm)) {
                filteredList.add(kandidat);
            }
        }

        Table.setItems(filteredList);
    }

    private void clearInputFields() {
        kolomIDInput.clear();
        kolomNAMAInput.clear();
        kolomVISIMISIInput.clear();
        kolomEMAILInput.clear();
        kolomPARTAIInput.clear();
    }

    @FXML
    public void saveDataToCSV() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write("NOID,NAMA,VISIMISI,EMAIL,PARTAI\n"); // write header
            for (Kandidat kandidat : kandidatList) {
                writer.write(kandidat.getNOID() + "," + kandidat.getNAMA() + "," + kandidat.getVISIMISI() + "," + kandidat.getEMAIL() + "," + kandidat.getPARTAI() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadDataFromCSV() {
        List<Kandidat> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) { // ensure data is valid
                    Kandidat kandidat = new Kandidat();
                    kandidat.setNOID(data[0]);
                    kandidat.setNAMA(data[1]);
                    kandidat.setVISIMISI(data[2]);
                    kandidat.setEMAIL(data[3]);
                    kandidat.setPARTAI(data[4]);
                    list.add(kandidat);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        kandidatList.clear();
        kandidatList.addAll(list);
        Table.setItems(kandidatList);
    }
}
