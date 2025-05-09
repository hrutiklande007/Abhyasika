package com.firebase.membership;

import com.firebase.firebase_connection.FirestoreService1;
import com.firebase.login_apk.logintrial;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LibraryMembershipForm {

    private FirestoreService1 firebaseService;

    public LibraryMembershipForm() {
        firebaseService = new FirestoreService1();
    }

    public Scene createMembershipForm(Stage primaryStage) {

        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        // Background image
        Image backgroundImage = new Image("back.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setOpacity(0.9); // Adjusted opacity for better contrast
        backgroundImageView.setFitWidth(1920);
        backgroundImageView.setFitHeight(1080);

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
            logintrial loginPage = new logintrial();
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
         logoAndBackBox.setPadding(new Insets(0,0,0,10));
       

        // Introduction text
        Label introText = new Label("Library Membership Registration");
        introText.setFont(Font.font("Verdana", 40));
        introText.setTextFill(Color.WHITE);
        introText.setStyle("-fx-font-weight:bold;");

        // Form fields
        Label studentNameLabel = createFieldLabel("Student's Full Name:");
        studentNameLabel.setStyle("-fx-font-weight:bold;");
        TextField studentNameField = createTextField("Enter Full Name");

        Label addressLabel = createFieldLabel("Address:");
        addressLabel.setStyle("-fx-font-weight:bold;");
        TextField addressField = createTextField("Enter Address");

        Label contactNumberLabel = createFieldLabel("Contact Number:");
        contactNumberLabel.setStyle("-fx-font-weight:bold;");
        TextField contactNumberField = createTextField("Enter Contact Number");

        Label genderLabel = createFieldLabel("Gender:");
        genderLabel.setStyle("-fx-font-weight:bold;");
        ComboBox<String> genderComboBox = createComboBox("Male", "Female", "Other");

        Label membershipLabel = createFieldLabel("Membership:");
        membershipLabel.setStyle("-fx-font-weight:bold;");
        ComboBox<String> membershipComboBox = createComboBox("Monthly", "Half Yearly", "Yearly");

        //payment

         // Load the image
         Image qrImage = new Image("qr.jpg");

         // Create an ImageView for the button
         ImageView qrImageView = new ImageView(qrImage);
         qrImageView.setFitWidth(50);  // Set desired width
         qrImageView.setFitHeight(50); // Set desired height
        
        Label QRLabel = createFieldLabel("QR Code Make A Payment Here:");
        QRLabel.setStyle("-fx-font-weight:bold;");
        Button qrCodeButton = new Button();
        qrCodeButton.setGraphic(qrImageView);

        qrCodeButton.setOnAction(event -> {
            // Create a new stage for displaying the image
            Stage imageStage = new Stage();
            ImageView imageView = new ImageView(qrImage);

            // Optionally set size for the image in the new window
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            imageView.setPreserveRatio(true);

            // Create a layout for the new window
            StackPane imagePane = new StackPane(imageView);
            Scene imageScene = new Scene(imagePane, 320, 320);

            // Set the scene and show the new stage
            imageStage.setScene(imageScene);
            imageStage.setTitle("QR Code");
            imageStage.show();
        });

        Label paymentLabel = createFieldLabel("Payment Screenshot:");
        paymentLabel.setStyle("-fx-font-weight:bold;");
        Button uploadButton = createButton("Upload Screenshot");
        
        Label uploadStatusLabel = new Label();
        uploadStatusLabel.setTextFill(Color.WHITE);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        uploadButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                uploadStatusLabel.setText("File selected: " + file.getName());
            } else {
                uploadStatusLabel.setText("File selection cancelled.");
            }
        });

        // Enter Seat Number field
        Label enterSeatNumberLabel = createFieldLabel("Your Selected Seat : ");
        enterSeatNumberLabel.setStyle("-fx-font-weight:bold;");
        TextField seatNumberField = createTextField("Enter Seat Number");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font(20));
        //submitButton.setPadding(new Insets(0,0,0,70));
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;-fx-font-weight:bold;-fx-pref-width: 120px; -fx-pref-height: 40px;");
        submitButton.setOnAction(event -> {
            Map<String, Object> memberData = new HashMap<>();
            memberData.put("studentName", studentNameField.getText());
            memberData.put("address", addressField.getText());
            memberData.put("contactNumber", contactNumberField.getText());
            memberData.put("gender", genderComboBox.getValue());
            memberData.put("membership", membershipComboBox.getValue());
            memberData.put("seatNumber", seatNumberField.getText());
            // Assuming the upload button sets the file name to the label
            memberData.put("paymentScreenshot", uploadStatusLabel.getText().replace("File selected: ", ""));

            String memberId = firebaseService.addLibraryMember(memberData);
            if (memberId != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Form Submitted");
                alert.setHeaderText(null);
                alert.setContentText("Your admission form has been submitted successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Submission Failed");
                alert.setHeaderText(null);
                alert.setContentText("There was an error submitting your form. Please try again.");
                alert.showAndWait();
            }

            studentNameField.clear();
            addressField.clear();
            contactNumberField.clear();
            genderComboBox.setValue(null);
            membershipComboBox.setValue(null);
            uploadStatusLabel.setText("");
            seatNumberField.clear();

        });


        // Form layout
        GridPane formLayout = new GridPane();
        formLayout.setVgap(20);
        formLayout.setHgap(10);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
        formLayout.setMaxWidth(800);
        formLayout.setMaxHeight(800);

        int row = 0;
        formLayout.addRow(row++, studentNameLabel, studentNameField);
        formLayout.addRow(row++, addressLabel, addressField);
        formLayout.addRow(row++, contactNumberLabel, contactNumberField);
        formLayout.addRow(row++, genderLabel, genderComboBox);
        formLayout.addRow(row++, membershipLabel, membershipComboBox);
        formLayout.addRow(row++, QRLabel, qrCodeButton);
        formLayout.addRow(row++, paymentLabel, uploadButton);
        formLayout.addRow(row++, new Label(), uploadStatusLabel);
        formLayout.addRow(row++, enterSeatNumberLabel, seatNumberField);
        formLayout.addRow(row++, submitButton);

        // Center the form and title in the window
        VBox root = new VBox(30,logoAndBackBox, introText, formLayout);
        root.setAlignment(Pos.TOP_CENTER);
        

        StackPane stackPane = new StackPane(backgroundImageView, root);

        Scene scene = new Scene(stackPane);
        // Add fade-in animation to the form
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), formLayout);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        return scene;
    }

    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font(18));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFont(Font.font(18));
        return textField;
    }

    private ComboBox<String> createComboBox(String... items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setPromptText("Select");
        comboBox.setPrefWidth(200);
        return comboBox;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font(18));
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        return button;
    }
}
