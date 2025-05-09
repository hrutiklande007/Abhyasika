package com.firebase.Login_page;

import com.firebase.login_apk.Landing;
import com.firebase.login_apk.logintrial;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AboutUs {

    public Scene createAboutScene(Stage primaryStage) {
        primaryStage.setTitle("About Us - Library Management System");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);

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
            Landing loginPage = new Landing();
            try {
                loginPage.start(primaryStage);
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

        Text companyName = new Text("Abhyasika.com");
        companyName.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        companyName.setFill(Color.web("#2980b9"));

        // Hero Text
        Text heroText = new Text("Welcome to Our Abhyasika.Com\nA World of Knowledge Awaits");
        heroText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        heroText.setFill(Color.web("#2980b9"));
        heroText.setTextAlignment(TextAlignment.CENTER);

        HBox heroTextSection = new HBox(heroText);
        heroTextSection.setAlignment(Pos.CENTER);
        heroTextSection.setPadding(new Insets(20, 0, 0, 600));

        // HBox to combine logo and hero text
        HBox heroSection = new HBox(20, heroTextSection);
        heroSection.setAlignment(Pos.CENTER_LEFT);
        heroSection.setPadding(new Insets(20));
        heroSection.setStyle("-fx-background-color: rgba(236, 240, 241, 0.8);"); // Set a translucent background color

        // Information Section 1
        Text section1Header = new Text("Our Mission");
        section1Header.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        section1Header.setFill(Color.web("#2c3e50"));

        Text section1Content = new Text(
                "Our mission is to provide a welcoming space for the community to explore and discover knowledge and creativity."
                        + "We offer a diverse range of books, resources, and programs to support learning and enrichment for all ages."
                        + "We strive to create a space where everyone feels included and inspired to learn.");
        section1Content.setFont(Font.font("Arial", 36));
        section1Content.setFill(Color.web("#34495e"));

        TextFlow section1TextFlow = new TextFlow(section1Header, new Text("\n"), section1Content);
        section1TextFlow.setTextAlignment(TextAlignment.CENTER);
        section1TextFlow.setPadding(new Insets(20, 40, 20, 40));

        Image section1Image = new Image("woman.jpg");
        ImageView section1ImageView = new ImageView(section1Image);
        section1ImageView.setFitWidth(450);
        section1ImageView.setFitHeight(250);
        section1ImageView.setPreserveRatio(true);
        section1ImageView.setEffect(new DropShadow(10, Color.BLACK));

        HBox section1 = new HBox(20, section1TextFlow, section1ImageView);
        section1.setAlignment(Pos.CENTER);
        section1.setPadding(new Insets(20, 0, 20, 0));

        // Information Section 2
        Text section2Header = new Text("Our Services");
        section2Header.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        section2Header.setFill(Color.web("#2c3e50"));

        Text section2Content = new Text(
                "We offer a wide range of services including book lending, digital resources, community programs, and educational workshops."
                        + "Our friendly staff are here to help you make the most of our resources and support your learning journey."
                        + "Join us for author talks, book clubs, and special events designed to bring our community together.");
        section2Content.setFont(Font.font("Arial", 36));
        section2Content.setFill(Color.web("#34495e"));

        TextFlow section2TextFlow = new TextFlow(section2Header, new Text("\n"), section2Content);
        section2TextFlow.setTextAlignment(TextAlignment.CENTER);
        section2TextFlow.setPadding(new Insets(20, 40, 20, 40));

        Image section2Image = new Image("hb.jpeg");
        ImageView section2ImageView = new ImageView(section2Image);
        section2ImageView.setFitWidth(450);
        section2ImageView.setFitHeight(250);
        section2ImageView.setPreserveRatio(true);
        section2ImageView.setEffect(new DropShadow(10, Color.BLACK));

        HBox section2 = new HBox(20, section2ImageView, section2TextFlow);
        section2.setAlignment(Pos.CENTER);
        section2.setPadding(new Insets(20, 0, 20, 0));

        // Feature Cards Section
        HBox featureCards = new HBox(20);
        featureCards.setAlignment(Pos.CENTER);
        featureCards.setPadding(new Insets(20));

        VBox card1 = createFeatureCard("Nikhil Talekar", "dev.jpg");
        VBox card2 = createFeatureCard("Dilip Chitale", "dev.jpg");
        VBox card3 = createFeatureCard("Aditya Kardile", "dev.jpg");
        VBox card4 = createFeatureCard("Akash Sabale", "dev.jpg");

        featureCards.getChildren().addAll(card1, card2, card3, card4);

        // Acknowledgment Section
        Text acknowledgmentHeader = new Text("Acknowledgment");
        acknowledgmentHeader.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        acknowledgmentHeader.setFill(Color.web("#2c3e50"));

        Text acknowledgmentContent = new Text(
                "Special thanks to Shashi Sir (Core2Web) for his exceptional guidance & teaching. "
                        + "Enough of his pertinence was why we developed our study library management system effectively.\n\n"
                        + "We would like to extend our sincere gratitude towards Core2Web for giving us an opportunity to showcase our \t project on stage, in front of hundreds. "
                        + "It was an opportunity to present our work and \n we were extremely grateful for it.\n\n"
                        + "Thank you Core2Web & Shashi Sir, for the immense support always and giving us this opportunity to present our projects publicly.");
        acknowledgmentContent.setFont(Font.font("Arial", 36));
        acknowledgmentContent.setFill(Color.web("#34495e"));
        acknowledgmentContent.setTextAlignment(TextAlignment.JUSTIFY);

        TextFlow acknowledgmentTextFlow = new TextFlow(acknowledgmentHeader, new Text("\n"), acknowledgmentContent);
        acknowledgmentTextFlow.setTextAlignment(TextAlignment.CENTER);
        acknowledgmentTextFlow.setPadding(new Insets(20, 40, 20, 40));

        HBox acknowledgmentSection = new HBox(acknowledgmentTextFlow);
        acknowledgmentSection.setAlignment(Pos.CENTER);
        acknowledgmentSection.setPadding(new Insets(20, 0, 20, 0));

        // Main Layout
        VBox mainLayout = new VBox(30, logoAndBackBox, heroSection, section1, section2, acknowledgmentSection, featureCards);
        mainLayout.setStyle("-fx-background-color: rgba(236, 240, 241, 0.8);"); // Set a translucent background color
        mainLayout.setFillWidth(true);

        // ScrollPane for main layout
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);

        // Fade-in animation for Information Sections
        applyFadeTransition(section1);
        applyFadeTransition(section2);
        applyFadeTransition(featureCards);
        applyFadeTransition(acknowledgmentSection);

        Scene scene = new Scene(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        return scene;
    }

    private void applyFadeTransition(HBox section) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), section);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setDelay(Duration.seconds(0));
        fadeTransition.play();
    }

    private VBox createFeatureCard(String title, String imagePath) {
        Image cardImage = new Image(imagePath);
        ImageView cardImageView = new ImageView(cardImage);
        cardImageView.setFitWidth(200); // Increased width
        cardImageView.setFitHeight(200); // Increased height
        cardImageView.setPreserveRatio(true);

        Text cardTitle = new Text(title);
        cardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        cardTitle.setFill(Color.web("#ffffff"));

        VBox card = new VBox(10, cardImageView, cardTitle);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #2980b9; -fx-background-radius: 10;");
        card.setPrefWidth(200);
        card.setPrefHeight(300); // Increased height
        card.setEffect(new DropShadow(10, Color.BLACK));

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #3498db; -fx-background-radius: 10;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #2980b9; -fx-background-radius: 10;"));

        return card;
    }
}
