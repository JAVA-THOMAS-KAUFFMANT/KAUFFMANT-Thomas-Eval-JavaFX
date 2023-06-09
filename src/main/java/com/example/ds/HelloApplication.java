package com.example.ds;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

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

    Label addSuggestError = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create container
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        // Create Element in main scene
        title = new Label("Vélo'v");
        showData = new Button("Consulter les données");
        downloadData = new Button("Télécharger le fichier");
        suggestData = new Button("Proposer une station");
        deleteSuggest = new Button("Supprimer une station");
        deleteSuggest.setDisable(true);

        // => EventListener ShowDataButton
        showData.setOnAction(e -> {
            // Create container
            VBox showDataContainer = new VBox(20);
            VBox filterContainer = new VBox(4);
            showDataContainer.setAlignment(Pos.CENTER);
            filterContainer.setAlignment(Pos.CENTER);
            // Create Element
            Label showDataTitle = new Label("Show Data :");
            // Create Filter
            TextField arrondissementInput = new TextField();
            arrondissementInput.setPromptText("Arondissement");
            Button sendFilterButtonArrondissement = new Button("Filter");
            Button allButtonArrondissement = new Button("Display all");

            filterContainer.getChildren().addAll(arrondissementInput, sendFilterButtonArrondissement, allButtonArrondissement);

            // Get All data from API
            JSONObject json = null;
            try {
                json = readJsonFromUrl("https://download.data.grandlyon.com/ws/grandlyon/pvo_patrimoine_voirie.pvostationvelov/all.json?maxfeatures=100&start=1");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // Get all values of API
            JSONArray dataAPI = json.getJSONArray("values");

            // Event button to send filter arrondissement
            sendFilterButtonArrondissement.setOnAction(f -> {
                if(arrondissementInput.getText().length() > 0 && arrondissementInput.getText() != null){
                    showDataContainer.getChildren().remove(2);
                    showDataContainer.getChildren().add(readAllDataFilterByArrondissment(dataAPI, arrondissementInput.getText()));
                }
            });

            // Event button to reset all filter and display all arrondissement
            allButtonArrondissement.setOnAction(f -> {
                showDataContainer.getChildren().remove(2);
                showDataContainer.getChildren().add(readAllData(dataAPI));
            });

            // Default display all station
            showDataContainer.getChildren().addAll(showDataTitle, filterContainer, readAllData(dataAPI));

            // Add all data in a ScrollPane
            ScrollPane sp = new ScrollPane();
            sp.setContent(showDataContainer);

            Scene showDataScene = new Scene(sp, 400, 400);
            showDataStage = new Stage();
            showDataStage.setScene(showDataScene);
            showDataStage.show();
        });

        // => EventListener DownloadDataButton
        downloadData.setOnAction(e -> {
            try {
                // Call function downloadFile
                downloadFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // => EventListener SuggestDataButton
        suggestData.setOnAction(e -> {
            // Create container
            VBox suggestDataContainer = new VBox(4);
            suggestDataContainer.setAlignment(Pos.CENTER);
            suggestDataContainer.setPadding(new Insets(4));
            // Create Element
            Label suggestDataTitle = new Label("Suggest Data :");
            // Create all input required
            TextField firstNameInput = new TextField();
            firstNameInput.setPromptText("First name");

            TextField lastNameInput = new TextField();
            lastNameInput.setPromptText("Last name");

            TextField adressStationInput = new TextField();
            adressStationInput.setPromptText("Adress");

            TextField nameStationInput = new TextField();
            nameStationInput.setPromptText("Name station");

            TextField postalCodeInput = new TextField();
            postalCodeInput.setPromptText("Postal code");

            TextField communeInput = new TextField();
            communeInput.setPromptText("Commune");

            TextField nbBornetteInput = new TextField();
            nbBornetteInput.setPromptText("Nb bornette");

            Button sendInput = new Button("Send");

            // Button to send all input
            sendInput.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    checkInput();
                }

                public void checkInput() {
                    // All input are fill
                    if(firstNameInput.getText().length() > 0 && lastNameInput.getText().length() > 0 && adressStationInput.getText().length() > 0 && nameStationInput.getText().length() > 0 && postalCodeInput.getText().length() > 0 && communeInput.getText().length() > 0 && nbBornetteInput.getText().length() > 0) {
                        // Creating a JSONObject object
                        JSONObject jsonObject = new JSONObject();
                        // Put all data in JSONObject
                        jsonObject.put("firstname", firstNameInput.getText());
                        jsonObject.put("lastname", lastNameInput.getText());
                        jsonObject.put("adress", adressStationInput.getText());
                        jsonObject.put("stationName", adressStationInput.getText());
                        jsonObject.put("postalCode", postalCodeInput.getText());
                        jsonObject.put("commune", communeInput.getText());
                        jsonObject.put("nbBordnette", nbBornetteInput.getText());
                        try {
                            // Add JSONObject
                            FileWriter file = new FileWriter("./data-velov.json");
                            file.write(jsonObject.toString());
                            file.close();

                            addSuggestError.setText("Send !");
                        } catch (IOException e) {
                            // Send error message
                            e.printStackTrace();
                            addSuggestError.setText("Error !");
                        }

                    } else {
                        // All input are not fill => display error message
                        addSuggestError.setText("Error, all input are not fill...");
                    }
                }
            });

            suggestDataContainer.getChildren().addAll(suggestDataTitle, firstNameInput, lastNameInput, adressStationInput, nameStationInput, postalCodeInput, communeInput, nbBornetteInput, addSuggestError, sendInput);

            // Set scene
            Scene suggestDataScene = new Scene(suggestDataContainer, 400, 400);
            suggestDataStage = new Stage();
            suggestDataStage.setScene(suggestDataScene);
            suggestDataStage.setTitle("Suggest");
            suggestDataStage.show();
        });

        // => EventListener DeleteDataButton (not available)
        deleteSuggest.setOnAction(e -> {
            // Create container
            VBox deleteDataContainer = new VBox();
            deleteDataContainer.setAlignment(Pos.CENTER);
            // Create Element
            Label deleteDataTitle = new Label("Delete Data :");
            deleteDataContainer.getChildren().addAll(deleteDataTitle);

            // Set scene
            Scene deleteDataScene = new Scene(deleteDataContainer, 400, 400);
            deleteDataStage = new Stage();
            deleteDataStage.setScene(deleteDataScene);
            deleteDataStage.setTitle("Suggest");
            deleteDataStage.show();
        });

        // => Add element into container
        container.getChildren().addAll(title, showData, downloadData, suggestData, deleteSuggest);

        // => Set scene
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
        VBox containerDataAPI = new VBox();
        VBox containerStation = new VBox(20);

        for (int i = 0 ; i < dataAPI.length()-1; i++) {
            JSONObject obj = dataAPI.getJSONObject(i);
            String adressData = obj.getString("adresse1");
            String nameData = obj.getString("nom");

            Label newLabel = new Label("=> Adresse : " + adressData + "\n==> Nom : " + nameData + "\n==> Arrondissment : " + obj.get("numdansarrondissement") + "\n==> Adresse : " + obj.get("adresse1") + "\n==> Nombre Bornettes : " + obj.get("nbbornettes"));
            containerStation.getChildren().add(newLabel);
        }
        containerDataAPI.getChildren().add(containerStation);
        return containerDataAPI;
    }

    public static VBox readAllDataFilterByArrondissment(JSONArray dataAPI, String arrondissement) {
        VBox containerDataAPI = new VBox();
        VBox containerStation = new VBox(20);

        // Loop into API Data
        for (int i = 0 ; i < dataAPI.length()-1; i++) {
            JSONObject obj = dataAPI.getJSONObject(i);
            if (Objects.equals(arrondissement, obj.get("numdansarrondissement").toString())) {
                // Get adress && nom
                String adressData = obj.getString("adresse1");
                String nameData = obj.getString("nom");

                Label newLabel = new Label("=> Adresse : " + adressData + "\n==> Nom : " + nameData + "\n==> Arrondissment : " + obj.get("numdansarrondissement") + "\n==> Adresse : " + obj.get("adresse1") + "\n==> Nombre Bornettes : " + obj.get("nbbornettes"));
                containerStation.getChildren().add(newLabel);
            }
        }

        containerDataAPI.getChildren().add(containerStation);
        return containerDataAPI;
    }

    public void downloadFile() throws IOException {
        //Redirect
        java.awt.Desktop.getDesktop().browse(URI.create("https://download.data.grandlyon.com/ws/grandlyon/pvo_patrimoine_voirie.pvostationvelov/all.json?maxfeatures=100&start=1"));
    }

    public static void main(String[] args) {
        launch();
    }
}