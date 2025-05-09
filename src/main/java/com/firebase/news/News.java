package com.firebase.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.firebase.login_apk.logintrial;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class News {
    private final String primaryColor = "#1e1e1e";
    private final String secondaryColor = "#2e2e2e";
    private final String accentColor = "#ff6600";
    private final String hoverColor = "#3e3e3e";
    private final String textColor = "#ffffff";
    private final String titleColor = "#87CEFA"; // LightSkyBlue hex code

    private final String hindiApiUrl = "https://newsdata.io/api/1/latest?country=in&category=top&language=hi&apikey=pub_4827854b40e0f58abd233177d051ebcec3246";
    private final String englishApiUrl = "https://newsdata.io/api/1/latest?country=in&category=top&language=en&apikey=pub_4827854b40e0f58abd233177d051ebcec3246";

    public void createScene(Stage primaryStage) {

        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        // Create a VBox to hold the news content
        VBox newsBox = new VBox(10);
        //newsBox.setAlignment(Pos.TOP_CENTER);
        newsBox.setPadding(new Insets(20));
        newsBox.setStyle("-fx-background-color: " + primaryColor
                + "; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");

                Button back = new Button("⇐");
                back.setStyle("-fx-background-color:#4CAF50;-fx-text-fill: white;");
                back.setPrefHeight(30);
                back.setPrefWidth(50);
                back.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,30));
                back.setOnAction(event -> {
                    logintrial loginPage = new logintrial();
                    try {
                        loginPage.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        Label newsTitle = new Label("Latest News / ताज़ा समाचार");
        newsTitle.setPadding(new Insets(0,150,0,0));
        newsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        newsTitle.setTextFill(Color.web(textColor));
        newsTitle.setStyle("-fx-background-color: " + accentColor + "; -fx-padding: 10px; -fx-border-radius: 10;");
        newsBox.getChildren().addAll(back,newsTitle);


        // Fetch news from the API in a separate thread
        new Thread(() -> {
            try {
                String hindiJsonResponse = fetchApiData(hindiApiUrl);
                String englishJsonResponse = fetchApiData(englishApiUrl);

                // Parse the JSON response and create news items
                JSONArray hindiArticles = new JSONObject(hindiJsonResponse).getJSONArray("results");
                JSONArray englishArticles = new JSONObject(englishJsonResponse).getJSONArray("results");

                Platform.runLater(() -> {
                    try {
                        addArticlesToNewsBox(newsBox, hindiArticles);
                        addArticlesToNewsBox(newsBox, englishArticles);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    
                     ScrollPane scrollPane = new ScrollPane(newsBox);
                     scrollPane.setFitToHeight(true);
                     scrollPane.setFitToWidth(true);

                    Scene scene = new Scene(scrollPane);
                    primaryStage.setScene(scene);
                    primaryStage.show();

                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
        
    }

    // Method to fetch data from the API
    private String fetchApiData(String apiUrl) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

    // Method to add articles to the news box
    private void addArticlesToNewsBox(VBox newsBox, JSONArray articles) throws JSONException {
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);

            String title = article.getString("title");
            String description = article.getString("description");

            VBox newsItemBox = createNewsItemBox(title, description);
            newsBox.getChildren().add(newsItemBox);
        }
    }

    // Method to create a news item box
    private VBox createNewsItemBox(String title, String description) {
        VBox newsItemBox = new VBox(5);
        newsItemBox.setAlignment(Pos.CENTER_LEFT);
        newsItemBox.setPadding(new Insets(15));
        newsItemBox.setStyle("-fx-background-color: " + secondaryColor + "; -fx-background-radius: 10;");
        newsItemBox.setOnMouseEntered(
                event -> newsItemBox.setStyle("-fx-background-color: " + hoverColor + "; -fx-background-radius: 10;"));
        newsItemBox.setOnMouseExited(event -> newsItemBox
                .setStyle("-fx-background-color: " + secondaryColor + "; -fx-background-radius: 10;"));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web(titleColor));

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        descriptionLabel.setTextFill(Color.web(textColor));

        newsItemBox.getChildren().addAll(titleLabel, descriptionLabel);
        return newsItemBox;
    }
}
