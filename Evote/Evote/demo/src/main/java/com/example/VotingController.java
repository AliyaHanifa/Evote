package com.example;

import javafx.event.ActionEvent;

// import javafx.fxml.FXML;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.image.ImageView;

// public class VotingController {
//     @FXML
//     private Button jadwalPemilihanButton;

//     @FXML
//     private Button kandidatButton;

//     @FXML
//     private Button kotakSuaraButton;

//     @FXML
//     private Button kotakSuaraButton1;
//     @FXML
//     private Button kotakSuaraButton11;
//     @FXML
//     private Button kotakSuaraButton111;

//     private Voting voting;

//     public void initialize() {
//         voting = new Voting();

//         kotakSuaraButton1.setOnAction(event -> pilihKandidat("Ayam Kampus"));
//         kotakSuaraButton11.setOnAction(event -> pilihKandidat("Tono"));
//         kotakSuaraButton111.setOnAction(event -> pilihKandidat("Emily White"));
//     }

//     private void pilihKandidat(String namaKandidat) {
//         voting.tambahSuara(namaKandidat);
//         System.out.println("Kandidat " + namaKandidat + " mendapat 1 suara. Total suara: " + voting.getSuara(namaKandidat));
//     }

//     public Voting getVoting() {
//         return voting;
//     }
// }

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class VotingController {
    @FXML
    private Button jadwalPemilihanButton;

    @FXML
    private Button kandidatButton;

    @FXML
    private Button kotakSuaraButton;

    @FXML
    private Button kotakSuaraButton1;
    @FXML
    private Button kotakSuaraButton11;
    @FXML
    private Button kotakSuaraButton111;

    private Voting voting;
    private CSVHandler csvHandler;
    private List<String[]> dataVoting;

    public void initialize() {
        voting = new Voting();

        kotakSuaraButton1.setOnAction(event -> {
            pilihKandidat("Ayam Kampus");
            saveToCSV("Evote/dataVoting.csv");
        });
        kotakSuaraButton11.setOnAction(event -> {
            pilihKandidat("Tono");
            saveToCSV("Evote/dataVoting.csv");
        });
        kotakSuaraButton111.setOnAction(event -> {
            pilihKandidat("Emily White");
            saveToCSV("Evote/dataVoting.csv");
        });
    }

    private void pilihKandidat(String namaKandidat) {
        voting.tambahSuara(namaKandidat);
        System.out.println("Kandidat " + namaKandidat + " mendapat 1 suara. Total suara: " + voting.getSuara(namaKandidat));
    }

    private void saveToCSV(String filename) {
        try {
            // Menyimpan file CSV
            try (FileWriter writer = new FileWriter(Paths.get(System.getProperty("user.dir"), filename).toString())) {
                writer.append("Kandidat,Suara\n");
                for (Map.Entry<String, Integer> entry : voting.getSemuaSuara().entrySet()) {
                    writer.append(entry.getKey()).append(",").append(entry.getValue().toString()).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Voting getVoting() {
        return voting;
    }

    @FXML
    private void showChart() {
        Stage stage = new Stage();
        stage.setTitle("Voting Results");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        xAxis.setLabel("Kandidat");
        yAxis.setLabel("Suara");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Voting Results");

        for (Map.Entry<String, Integer> entry : voting.getSemuaSuara().entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
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
    void handlerJadwal(ActionEvent event) {
        switchToScene(event, "melihatJadwal-view.fxml");
    }

    @FXML
    void handlerKandidat(ActionEvent event) {
        switchToScene(event, "melihatKandidat-view.fxml");
    }

    @FXML
    void handlerKotakSuara(ActionEvent event) {
        switchToScene(event, "hasil-view.fxml");
    }
}

