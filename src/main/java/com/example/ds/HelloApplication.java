package com.example.ds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
            downloadDataContainer.getChildren().addAll(downloadDataTitle);

            Scene downloadDataScene = new Scene(downloadDataContainer, 400, 400);
            deleteDataStage = new Stage();
            deleteDataStage.setScene(downloadDataScene);
            deleteDataStage.show();
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
            deleteDataStage.show();
        });

        // Add element into container
        container.getChildren().addAll(title, showData, downloadData, suggestData, deleteSuggest);

        // Set scene
        Scene mainScene = new Scene(container, 400, 400);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}