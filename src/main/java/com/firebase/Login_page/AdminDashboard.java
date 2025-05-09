package com.firebase.Login_page;

import java.util.List;
import java.util.concurrent.ExecutionException;
import com.firebase.firebase_connection.FirestoreService1;
import com.firebase.login_apk.Landing;
import com.firebase.login_apk.logintrial;
import com.firebase.seat_booking.SeatBookingSystem;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.application.Platform;
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

public class AdminDashboard {

    private FirestoreService1 firestoreService = new FirestoreService1();
    private VBox vbox = new VBox();
    private VBox liveMembersBox = new VBox();
    private VBox seatVacantsBox = new VBox();
    private VBox seatStatusZeroBox = new VBox();
    private VBox seatAllocationBox = new VBox();
    Stage primaryStage;

    public Scene createScene(Stage primaryStage,String username) {
        this.primaryStage = primaryStage;

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



        // HBox for logo and logo label
        HBox logoBox = new HBox(10, logoImageView, logoLabel); // 10 is the spacing between elements
        logoBox.setAlignment(Pos.TOP_LEFT);

        // VBox for logoBox and Go Back button
        VBox logoAndBackBox = new VBox(10, logoBox); // 10 is the spacing between elements
        logoAndBackBox.setAlignment(Pos.TOP_LEFT);
        logoAndBackBox.setPadding(new Insets(0, 0, 0, 10));
        BorderPane root = new BorderPane();

        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        listenForUserInformationUpdates(username); // Listen for real-time updates
        updateLiveMembers();
        updateSeatVacants();
        updateSeatStatusZero();
        updateSeatAllocation(username);


        ScrollPane mainScrollPane = new ScrollPane(root);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);


        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setStyle("-fx-background: #f0f0f0; -fx-padding: 10;");

        Button btforadminseatcontrol = new Button("Update Student\n    Seat Days");
        btforadminseatcontrol.setStyle("-fx-font-size:18px;-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btforadminseatcontrol.setPrefSize(200,65);
        btforadminseatcontrol.setOnAction(e -> updateSeatDays()); // Add event handler for button

        Button removeExpiredMembersButton = new Button("Remove Expired\n    Members");
        removeExpiredMembersButton.setStyle("-fx-font-size:18px;-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        removeExpiredMembersButton.setPrefSize(220, 65);
        removeExpiredMembersButton.setOnAction(e -> removeExpiredMembers());
        HBox firstHBox = new HBox();
        firstHBox.setAlignment(Pos.CENTER);
        firstHBox.setSpacing(20);
        firstHBox.setPrefHeight(350);

        Button freeExpiredSeatsButton = new Button("Free Expired\n    Seats");
        freeExpiredSeatsButton.setStyle("-fx-font-size:18px;-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        freeExpiredSeatsButton.setPrefSize(170, 65);
        freeExpiredSeatsButton.setOnAction(e -> {
        
           firestoreService.removeExpiredMembers();
    
        });
       
        
        Image backgroundImage = new Image("a.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(400);
        backgroundImageView.setFitHeight(200);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");

        VBox textVBox = new VBox();
        textVBox.setAlignment(Pos.CENTER_LEFT);
        textVBox.setSpacing(10);

        Text title = new Text("Admin Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        title.setFill(Color.WHITE);

        Text subtitle = new Text("Welcome to the Admin Dashboard");
        subtitle.setFont(Font.font("Arial", 24));
        subtitle.setFill(Color.WHITE);

        textVBox.getChildren().addAll(title, subtitle);
        firstHBox.getChildren().addAll(logoAndBackBox, backgroundImageView, textVBox, btforadminseatcontrol,freeExpiredSeatsButton,removeExpiredMembersButton);
        firstHBox.setStyle("-fx-background-color: #282C34;");

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.font("Arial", 16));
        logoutButton.setOnAction(event -> {
            Landing log = new Landing();
            
            try {
                log.start(primaryStage);
            }catch (Exception e) {
                e.printStackTrace();
            }
            });

        VBox footerVBox = new VBox(logoutButton);
        footerVBox.setAlignment(Pos.CENTER);
        footerVBox.setPadding(new Insets(20));

        HBox mainContent = new HBox();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setSpacing(20);


        HBox topRow = new HBox(20, liveMembersBox, seatVacantsBox); // 20 is the spacing between elements
        topRow.setAlignment(Pos.CENTER);
        
        HBox bottomRow = new HBox(20, seatStatusZeroBox, seatAllocationBox); // 20 is the spacing between elements
        bottomRow.setAlignment(Pos.CENTER);
        
        VBox combinedBoxes = new VBox(20, topRow, bottomRow); // 20 is the spacing between rows
        combinedBoxes.setAlignment(Pos.CENTER);
        combinedBoxes.setPadding(new Insets(50,150,0,0));

        ScrollPane scrollPane1 = new ScrollPane(vbox);
        scrollPane1.setFitToWidth(true);
        scrollPane1.setPadding(new Insets(10));
        scrollPane1.setStyle("-fx-background: #f0f0f0; -fx-padding: 10;");

        mainContent.getChildren().addAll(scrollPane1);
        // logo and the first hbox
        VBox logoAndFirstVBox = new VBox(20, logoAndBackBox, firstHBox);

        root.setTop(logoAndFirstVBox);
        root.setCenter(mainContent);
        root.setBottom(footerVBox);
        root.setRight(combinedBoxes);

        Scene scene = new Scene(mainScrollPane); // Increase the scene width to 1000
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.show();

        return scene;
    }


    private void listenForUserInformationUpdates(String username) {
        firestoreService.getUserInformationCollection()
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot snapshots, FirestoreException e) {
                    if (e != null) {
                        e.printStackTrace();
                        showError("Failed to listen for user information updates: " + e.getMessage());
                        return;
                    }

                    if (snapshots != null) {
                        Platform.runLater(() -> {
                            vbox.getChildren().clear();
                            for (DocumentSnapshot document : snapshots) {
                                VBox userBox = createUserInfoBox(document);
                                vbox.getChildren().add(userBox);
                            }
                            updateLiveMembers();
                            updateSeatVacants();
                            updateSeatStatusZero();
                            updateSeatAllocation(username);
                        });
                    }
                }
            });
    }

    private VBox createUserInfoBox(DocumentSnapshot document) {
        VBox userBox = new VBox();
        userBox.setAlignment(Pos.CENTER_LEFT);
        userBox.setSpacing(5);
        userBox.setPadding(new Insets(10));
        userBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        Label nameLabel = new Label("Name: " + document.getString("studentName"));
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label addressLabel = new Label("Address: " + document.getString("address"));
        addressLabel.setFont(Font.font("Arial", 16));

        Label contactLabel = new Label("Contact: " + document.getString("contactNumber"));
        contactLabel.setFont(Font.font("Arial", 16));

        Label seatnumberLabel = new Label("Seat Number: " + document.getString("seatNumber"));
        contactLabel.setFont(Font.font("Arial", 16));

        Label genderLabel = new Label("Gender: " + document.getString("gender"));
        genderLabel.setFont(Font.font("Arial", 16));

        Label membershipLabel = new Label("Membership: " + document.getString("membership"));
        membershipLabel.setFont(Font.font("Arial", 16));

        Label paymentphoto = new Label("Payment Screenshot: " + document.getString("paymentScreenshot"));
        addressLabel.setFont(Font.font("Arial", 16));

        int remainingDays = document.contains("remainingDays") ? document.getLong("remainingDays").intValue() : calculateRemainingDays(document.getString("membership"));
        Label remainingDaysLabel = new Label("Days Remaining: " + remainingDays);
        remainingDaysLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        userBox.getChildren().addAll(nameLabel,seatnumberLabel, contactLabel, genderLabel, membershipLabel, addressLabel,paymentphoto, remainingDaysLabel);
        userBox.setPrefWidth(600); // Set a preferred width for the user box

        return userBox;
    }

    private int calculateRemainingDays(String membershipType) {
        if (membershipType == null) {
            return 0;
        }
        switch (membershipType) {
            case "Monthly":
                return 28;
            case "Half Yearly":
                return 168;
            case "Yearly":
                return 365;
            default:
                return 0;
        }
    }

    private void updateSeatDays() {
        try {
            List<QueryDocumentSnapshot> documents = firestoreService.getUserInformation();
            for (DocumentSnapshot document : documents) {
                String membershipType = document.getString("membership");
                if (membershipType != null) {
                    int remainingDays;
                    if (document.contains("remainingDays")) {
                        remainingDays = document.getLong("remainingDays").intValue();
                    } else {
                        remainingDays = calculateRemainingDays(membershipType);
                    }
                    if (remainingDays > 0) {
                        remainingDays -= 1;
                        updateMembershipDays(document.getId(), remainingDays);
                    }
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            showError("Failed to update seat days: " + e.getMessage());
        }
    }

    private void updateMembershipDays(String documentId, int remainingDays) {
        try {
            firestoreService.updateUserMembershipDays(documentId, remainingDays);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            showError("Failed to update membership days: " + e.getMessage());
        }
    }

    // card1 for the live members *\\
    private void updateLiveMembers() {
        int liveMembers = vbox.getChildren().size();
        liveMembersBox.getChildren().clear();
        liveMembersBox.setAlignment(Pos.TOP_CENTER);
        liveMembersBox.setStyle(
            "-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);"
            + "-fx-padding: 20;"
            + "-fx-background-radius: 15;"
            + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);"
        );
        liveMembersBox.setPadding(new Insets(20));
        liveMembersBox.setPrefWidth(300);
        liveMembersBox.setPrefHeight(300);

        // Add the image
        Image image = new Image("Livemember.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Add the title
        Label titleLabel = new Label("Live Members");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-underline: true;");
        VBox.setMargin(titleLabel, new Insets(10, 0, 10, 0)); // Add margin to title

        // Add the count of live members
        Label countLabel = new Label(String.valueOf(liveMembers));
        countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 38));
        countLabel.setTextFill(Color.YELLOW);
        VBox.setMargin(countLabel, new Insets(10, 0, 0, 0)); // Add margin to count

        liveMembersBox.getChildren().addAll(imageView, titleLabel, countLabel);
    }

    // card2 for the vacant seats *\\
    private void updateSeatVacants() {
        int seatVacants = 80 - vbox.getChildren().size();
        seatVacantsBox.getChildren().clear();
        seatVacantsBox.setAlignment(Pos.TOP_CENTER);
        seatVacantsBox.setStyle(
            "-fx-background-color: linear-gradient(to right, #ff7e5f, #feb47b);"
            + "-fx-padding: 20;"
            + "-fx-background-radius: 15;"
            + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);"
        );
        seatVacantsBox.setPadding(new Insets(20));
        seatVacantsBox.setPrefWidth(300);
        seatVacantsBox.setPrefHeight(300);

        // Add the image
        Image image = new Image("SeatVacant.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Add the title
        Label titleLabel = new Label("Seat Vacants");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-underline: true;");
        VBox.setMargin(titleLabel, new Insets(10, 0, 10, 0)); // Add margin to title

        // Add the count of seat vacants
        Label countLabel = new Label(String.valueOf(seatVacants));
        countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 38));
        countLabel.setTextFill(Color.WHITE);
        VBox.setMargin(countLabel, new Insets(10, 0, 0, 0)); // Add margin to count

        seatVacantsBox.getChildren().addAll(imageView, titleLabel, countLabel);
    }

    
    private void updateSeatStatusZero() {
        try {
            // Fetch user information from Firestore
            List<QueryDocumentSnapshot> documents = firestoreService.getUserInformation();

            // Count the number of users with zero days remaining
            int seatStatusZero = 0;
            for (DocumentSnapshot document : documents) {
                int remainingDays = document.contains("remainingDays") ? document.getLong("remainingDays").intValue()
                        : calculateRemainingDays(document.getString("membership"));
                if (remainingDays == 0) {
                    seatStatusZero++;
                }
            }

            // Update the seatStatusZeroBox with the count
            seatStatusZeroBox.getChildren().clear();
            seatStatusZeroBox.setAlignment(Pos.TOP_CENTER);
            seatStatusZeroBox.setStyle(
                    "-fx-background-color: linear-gradient(to right, #36D1DC, #5B86E5);"
                            + "-fx-padding: 20;"
                            + "-fx-background-radius: 15;"
                            + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);");
            seatStatusZeroBox.setPadding(new Insets(20));
            seatStatusZeroBox.setPrefWidth(300);
            seatStatusZeroBox.setPrefHeight(300);

            // Add the image
            Image image = new Image("SeatStatus.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
            imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

            // Add the title
            Label titleLabel = new Label("Seat Status Zero");
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            titleLabel.setTextFill(Color.WHITE);
            titleLabel.setStyle("-fx-underline: true;");
            VBox.setMargin(titleLabel, new Insets(10, 0, 10, 0)); // Add margin to title

            // Add the count of seat status zero
            Label countLabel = new Label(String.valueOf(seatStatusZero));
            countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 38));
            countLabel.setTextFill(Color.WHITE);
            VBox.setMargin(countLabel, new Insets(10, 0, 0, 0)); // Add margin to count

            seatStatusZeroBox.getChildren().addAll(imageView, titleLabel, countLabel);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            showError("Failed to update seat status zero: " + e.getMessage());
        }
    }
    // card4 for the seat allocation *\\
    private void updateSeatAllocation(String username) {
        seatAllocationBox.getChildren().clear();
        seatAllocationBox.setAlignment(Pos.TOP_CENTER);
        seatAllocationBox.setStyle(
            "-fx-background-color: linear-gradient(to right, #FF512F, #DD2476);"
            + "-fx-padding: 20;"
            + "-fx-background-radius: 15;"
            + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);"
        );
        seatAllocationBox.setPadding(new Insets(20));
        seatAllocationBox.setPrefWidth(300);
        seatAllocationBox.setPrefHeight(300);
    
        // Add the image
        Image image = new Image("SeatsAdmin.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
    
        // Add the title
        Label titleLabel = new Label("Seats");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-underline: true;");
        VBox.setMargin(titleLabel, new Insets(10, 0, 10, 0)); // Add margin to title
    
        // Add a button
        Button updateButton = new Button("Updated Seat Structure");
        updateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        updateButton.setPrefHeight(35);
        updateButton.setStyle(
            "-fx-background-color: #4CAF50;"
            + "-fx-text-fill: white;"
            + "-fx-padding: 10 20;"
            + "-fx-background-radius: 10;"
        );
    
        // Set action to the button
        updateButton.setOnAction(e -> {
            // Add action logic here if needed
           // System.out.println("Update Button Clicked!");
           SeatBookingSystem Seat = new SeatBookingSystem();
           Scene SeatScene = Seat.createSeatBookingScene(primaryStage,username);
           primaryStage.setScene(SeatScene);
        });
    
        // Add all elements to the VBox
        seatAllocationBox.getChildren().addAll(imageView, titleLabel, updateButton);
    }
    
    private void removeExpiredMembers() {
        try {
            List<QueryDocumentSnapshot> documents = firestoreService.getUserInformationCollection().get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                if (document.contains("remainingDays") && document.getLong("remainingDays") == 0) {
                    firestoreService.getUserInformationCollection().document(document.getId()).delete();
                }
            }
            showError("Expired members removed successfully!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            showError("Failed to remove expired members: " + e.getMessage());
        }
    }

    private void showError(String errorMessage) {
        Platform.runLater(() -> {
            Stage errorStage = new Stage();
            errorStage.setTitle("Error");

            VBox errorBox = new VBox();
            errorBox.setPadding(new Insets(20));
            errorBox.setSpacing(10);
            errorBox.setAlignment(Pos.CENTER);

            Label errorLabel = new Label(errorMessage);
            errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            errorLabel.setTextFill(Color.RED);

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> errorStage.close());

            errorBox.getChildren().addAll(errorLabel, closeButton);

            Scene errorScene = new Scene(errorBox, 400, 200);
            errorStage.setScene(errorScene);
            errorStage.show();
        });
    }

    


}