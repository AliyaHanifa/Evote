package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HasilController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button kandidatButton;

    @FXML
    private Button pemilihanButton;

    @FXML
    private Button kotakSuaraButton;

    @FXML
    private Button jadwalPemilihanButton;

    @FXML
    private Label username;

    @FXML
    private Label lokasiUser;

    public void initialize() {
        loadDataFromCSV("Evote/dataVoting.csv");
    }

    private void loadDataFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(System.getProperty("user.dir"), filename).toString()))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String kandidat = parts[0];
                    int suara = Integer.parseInt(parts[1]);
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.setName(kandidat);
                    series.getData().add(new XYChart.Data<>(kandidat, suara));
                    barChart.getData().add(series);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        switchToScene(event, "melihatJadwal-view.fxml");
    }

    @FXML
    void kandidatButton(ActionEvent event) {
        switchToScene(event, "melihatKandidat-view.fxml");
    }

    @FXML
    void pemilihanButton(ActionEvent event) {
        switchToScene(event, "voting-view.fxml");
    }

}
