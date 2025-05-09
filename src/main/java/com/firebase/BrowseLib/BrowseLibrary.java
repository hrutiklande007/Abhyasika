
package com.firebase.BrowseLib;

import com.firebase.login_apk.Landing;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BrowseLibrary {
    String username = "";

    public Scene createLibrary(Stage primaryStage) {

        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        
        // Background image
        Image backgroundImage = new Image("back.jpg"); // Update with your background image path
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

        // Logo Image and Label
        Image logoImage = new Image("logo.png"); // Update the path to the logo image
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(85); // Set the width of the logo image
        logoImageView.setFitHeight(85); // Set the height of the logo image
        logoImageView.setPreserveRatio(true);

        Label logoLabel = new Label("Abhyasika.com");
        logoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        logoLabel.setStyle("-fx-text-fill: black;");
        logoLabel.setPadding(new Insets(25, 0, 0, 0));

        Button back = new Button("⇐");
        back.setStyle("-fx-background-color:#4CAF50;-fx-text-fill: white;");
        back.setPrefHeight(30);
        back.setPrefWidth(50);
        back.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,30));
        back.setOnAction(event -> {
            Landing landingPage = new Landing();

            try {
                landingPage.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        // HBox for logo and logo label
        HBox logoBox = new HBox(10, logoImageView, logoLabel); // 10 is the spacing between elements
        logoBox.setAlignment(Pos.TOP_LEFT);

        // VBox for logoBox and Go Back button
        VBox logoAndBackBox = new VBox(10, logoBox, back); // 10 is the spacing between elements
        logoAndBackBox.setAlignment(Pos.TOP_LEFT);
        logoAndBackBox.setPadding(new Insets(0, 0, 0, 10));

        // First HBox with Image and Tagline
        Image image1 = new Image("hb.jpeg"); // Update with your image path
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(1870); // Adjust size as needed
        imageView1.setFitHeight(700);

        Label tagline = new Label("“Quiet Corners, Grand Ideas: Your Personal Portal to the World of Wisdom.”");
        tagline.setStyle(
                "-fx-font-size: 44px; " +
                        "-fx-text-fill: white;" + // Changed color to white
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 10, 0.5, 0.0, 0.0);" +
                        "-fx-font-family: 'Arial'; -fx-font-weight: bold;" +
                        "-fx-background-color: rgba(0, 0, 0, 0.6);" + // Adding a slight background color for better visibility
                        "-fx-padding: 10;" + // Adding padding for better spacing
                        "-fx-border-color: rgba(255, 255, 255, 0.75);" + // Adding a border
                        "-fx-border-width: 2px;" + // Border width
                        "-fx-border-radius: 10px;" // Border radius for rounded corners
        );
        tagline.setPadding(new Insets(20));

        // Adding animation to the tagline
        final double SCALE_FACTOR = 1.1;
        tagline.setOnMouseEntered(event -> {
            tagline.setScaleX(SCALE_FACTOR);
            tagline.setScaleY(SCALE_FACTOR);
        });
        tagline.setOnMouseExited(event -> {
            tagline.setScaleX(1);
            tagline.setScaleY(1);
        });

        StackPane imageWithTagline = new StackPane();
        imageWithTagline.getChildren().addAll(imageView1, tagline);

        HBox hBox1 = new HBox(imageWithTagline);
        hBox1.setAlignment(Pos.CENTER);
        // hBox1.setSpacing(10);

        // Second HBox with Two VBoxes
        // Image slideshow
        List<Image> images = new ArrayList<>();
        images.add(new Image("a.jpg")); // Update with your image paths
        images.add(new Image("2.jpg")); 
        images.add(new Image("3.jpg"));
        images.add(new Image("4.jpeg"));
        images.add(new Image("5.jpeg"));

        ImageView slideShowImageView = new ImageView(images.get(0));
        slideShowImageView.setFitWidth(500); // Adjust size as needed
        slideShowImageView.setFitHeight(300);
        
        // Create a Timeline to change the image every 3 seconds
        Timeline slideshowTimeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
            Image currentImage = slideShowImageView.getImage();
            int currentIndex = images.indexOf(currentImage);
            int nextIndex = (currentIndex + 1) % images.size();
            slideShowImageView.setImage(images.get(nextIndex));
        }));
        slideshowTimeline.setCycleCount(Timeline.INDEFINITE); // Loop indefinitely
        slideshowTimeline.play();

        VBox vBox1 = new VBox(slideShowImageView);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(10);
        vBox1.setPadding(new Insets(10));
        vBox1.setStyle(
                "-fx-border-color: white; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: rgba(255, 255, 255, 0.1);");

        // Label at the top of the infoBox
        Label label = new Label("Shree Abhyasika");
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 45)); // Make the text bold
        label.setTextFill(Color.BLACK);
        label.setPadding(new Insets(10)); // Add some padding
        label.setAlignment(Pos.CENTER); // Center the label

        Text address = new Text("📍: 1st floor, SK Chambers, near Janjira Hotel,\n" +
                "265 Navi Sadashiv Peth, Pune, India, Maharashtra");
        address.setFont(Font.font("Arial", 28)); // Increased font size
        address.setFill(Color.BLACK);

        Text phone = new Text("📞: 077095 99697");
        phone.setFont(Font.font("Arial", 28)); // Increased font size
        phone.setFill(Color.BLACK);

        Text email = new Text("✉️: StudentLibrary@gmail.com");
        email.setFont(Font.font("Arial", 28)); // Increased font size
        email.setFill(Color.BLACK);

        Text price = new Text("Price range: 1200 Per Month");
        price.setFont(Font.font("Arial", 28)); // Increased font size
        price.setFill(Color.BLACK);

        VBox vBox2 = new VBox(label, address, phone, email, price);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(10);
        vBox2.setPadding(new Insets(10));
        vBox2.setStyle(
                "-fx-border-color: white; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: rgba(255, 255, 255, 0.1);");

        HBox hBox2 = new HBox(vBox1, vBox2);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(20);

        // Main layout
        VBox mainLayout = new VBox(logoAndBackBox, hBox1, hBox2);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(20);
        mainLayout.setMaxWidth(1920); // Ensure content does not exceed the window width
        mainLayout.setBackground(new Background(background));
        mainLayout.setPadding(new Insets(20)); // Add padding to ensure content can scroll

        // Add scrolling
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Ensure horizontal scrollbar never appears
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Ensure vertical scrollbar always appears
        scrollPane.setStyle("-fx-background-color: transparent;");

        return new Scene(scrollPane); // Adjusted height for better scrolling
    }
}

