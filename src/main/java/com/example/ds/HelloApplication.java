package com.example.ds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HelloApplication extends Application {

    Stage showDataStage;
    Stage downloadDataStage;
    Stage suggestDataStage;
    Stage deleteDataStage;
    Label title;
    Button showData;
    Button downloadData;
    Button suggestData;
    Button deleteSuggest;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create container
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        // Create Element
        title = new Label("Vélo'v");
        showData = new Button("Consulter les données");
        downloadData = new Button("Télécharger le fichier");
        suggestData = new Button("Proposer une station");
        deleteSuggest = new Button("Supprimer une station");

        // => EventListener ShowDataButton
        showData.setOnAction(e -> {
            // Create container
            VBox showDataContainer = new VBox();
            showDataContainer.setAlignment(Pos.CENTER);
            // Create Element
            Label showDataTitle = new Label("Show Data :");
            showDataContainer.getChildren().addAll(showDataTitle);

            Scene showDataScene = new Scene(showDataContainer, 400, 400);
            showDataStage = new Stage();
            showDataStage.setScene(showDataScene);
            showDataStage.show();
        });

        // => EventListener DownloadDataButton
        downloadData.setOnAction(e -> {
            // Create container
            VBox downloadDataContainer = new VBox();
            downloadDataContainer.setAlignment(Pos.CENTER);
            // Create Element
            Label downloadDataTitle = new Label("Download Data :");
            Label data = new Label();

            JSONObject json = null;
            try {
                json = readJsonFromUrl("https://download.data.grandlyon.com/ws/grandlyon/pvo_patrimoine_voirie.pvostationvelov/all.json?maxfeatures=100&start=1");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            JSONArray dataAPI = json.getJSONArray("values");
            downloadDataContainer.getChildren().addAll(downloadDataTitle, readAllData(dataAPI));

            Scene downloadDataScene = new Scene(downloadDataContainer, 400, 400);
            downloadDataStage = new Stage();
            downloadDataStage.setScene(downloadDataScene);
            downloadDataStage.setTitle("Download");
            downloadDataStage.show();
        });

        // => EventListener SuggestDataButton
        suggestData.setOnAction(e -> {
            // Create container
            VBox suggestDataContainer = new VBox();
            suggestDataContainer.setAlignment(Pos.CENTER);
            // Create Element
            Label suggestDataTitle = new Label("Suggest Data :");
            suggestDataContainer.getChildren().addAll(suggestDataTitle);

            Scene suggestDataScene = new Scene(suggestDataContainer, 400, 400);
            suggestDataStage = new Stage();
            suggestDataStage.setScene(suggestDataScene);
            suggestDataStage.setTitle("Suggest");
            suggestDataStage.show();
        });

        // => EventListener DeleteDataButton
        deleteSuggest.setOnAction(e -> {
            // Create container
            VBox deleteDataContainer = new VBox();
            deleteDataContainer.setAlignment(Pos.CENTER);
            // Create Element
            Label deleteDataTitle = new Label("Delete Data :");
            deleteDataContainer.getChildren().addAll(deleteDataTitle);

            Scene deleteDataScene = new Scene(deleteDataContainer, 400, 400);
            deleteDataStage = new Stage();
            deleteDataStage.setScene(deleteDataScene);
            deleteDataStage.setTitle("Suggest");
            deleteDataStage.show();
        });

        // Add element into container
        container.getChildren().addAll(title, showData, downloadData, suggestData, deleteSuggest);

        // Set scene
        Scene mainScene = new Scene(container, 600, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Main windows");
        primaryStage.show();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static VBox readAllData(JSONArray dataAPI) {
        VBox containerDataAPI = new VBox(10);
        VBox containerStation = new VBox(20);

        for (int i = 0 ; i < dataAPI.length()-1; i++) {
            JSONObject obj = dataAPI.getJSONObject(i);
            String adressData = obj.getString("adresse1");
            //String B = String.valueOf(obj.getInt("numdansarrondissement"));
            String nameData = obj.getString("nom");
            // String D = obj.getString("nbbornettes");

            Label newLabel = new Label("=> Adress : " + adressData + " / Nom : =>" + nameData);
            containerStation.getChildren().add(newLabel);
        }
        containerDataAPI.getChildren().add(containerStation);
        return containerDataAPI;
    }

    public static void main(String[] args) {
        launch();
    }
}