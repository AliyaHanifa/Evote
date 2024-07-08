package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MelihatKandidatController {

    @FXML
    private TableView<Kandidat> tableView;

    @FXML
    private Button dataSuaraButton;

    @FXML
    private HBox header;

    @FXML
    private Button kandidatButton;

    @FXML
    private TableColumn<Kandidat, String> kolomEMAIL;

    @FXML
    private TableColumn<Kandidat, String> kolomID;

    @FXML
    private TableColumn<Kandidat, String> kolomNAMA;

    @FXML
    private TableColumn<Kandidat, String> kolomPARTAI;

    @FXML
    private TextField kolomSearch;

    @FXML
    private TableColumn<Kandidat, String> kolomVISIMISI;

    @FXML
    private Label lokasiUser;

    @FXML
    private VBox sidebar;

    @FXML
    private Label username;

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

        tableView.setItems(kandidatList);
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
        tableView.setItems(kandidatList);
    }

    @FXML
    private void searchData(ActionEvent event) {
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

        tableView.setItems(filteredList);
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
    void pemilihanButton(ActionEvent event) {
        switchToScene(event, "voting-view.fxml");
    }

    @FXML
    void jadwalButton(ActionEvent event) {
        switchToScene(event, "melihatJadwal-view.fxml");
    }

    @FXML
    void kotakSuaraButton(ActionEvent event) {
        switchToScene(event, "hasil-view.fxml");
    }
}
