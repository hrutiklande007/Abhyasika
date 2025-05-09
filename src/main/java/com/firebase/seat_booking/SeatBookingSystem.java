package com.firebase.seat_booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firebase.Login_page.AdminDashboard;
import com.firebase.firebase_connection.FirestoreService1;
import com.firebase.login_apk.logintrial;
import com.firebase.membership.LibraryMembershipForm;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SeatBookingSystem {

    private static final int ROWS = 11;
    private static final int COLS = 10;
    private Button[][] seats = new Button[ROWS][COLS];
    private Label libraryTagLine = new Label("Online Seat Booking System");
    public FirestoreService1 fireservice = new FirestoreService1();

    public Scene createSeatBookingScene(Stage SeatBookStage,String usrename) {
        SeatBookStage.setTitle("Seat Booking System");
        SeatBookStage.setWidth(1920);
        SeatBookStage.setHeight(1080);

        // Set the stage to full screen
        SeatBookStage.setFullScreen(true);

        // Set the stage to maximized
        SeatBookStage.setMaximized(true);

        // Set the stage size to the full screen size
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        SeatBookStage.setWidth(bounds.getWidth());
        SeatBookStage.setHeight(bounds.getHeight());

        // Background image setup
        Image image = new Image("back.jpg");
        ImageView iv = new ImageView(image);
        iv.setPreserveRatio(false);

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
        back.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
        back.setOnAction(event -> {

            if(fireservice.isAdmin(usrename)){
                AdminDashboard admindashboard = new AdminDashboard();
                try {
                    admindashboard.createScene(SeatBookStage,usrename);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else{
                logintrial loginPage = new logintrial();
                try {
                    loginPage.start(SeatBookStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
          
            
        });

        // HBox for logo and logo label
        HBox logoBox = new HBox(10, logoImageView, logoLabel); // 10 is the spacing between elements
        logoBox.setAlignment(Pos.TOP_LEFT);

        // VBox for logoBox and Go Back button
        VBox logoAndBackBox = new VBox(10, logoBox, back); // 10 is the spacing between elements
        logoAndBackBox.setAlignment(Pos.TOP_LEFT);
        logoAndBackBox.setPadding(new Insets(0, 0, 0, 10));

        // GridPane for seat buttons
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        // Labels for seat status
        Label confirmation1 = new Label("Not Booked Yet");
        Label confirmation2 = new Label("Already Booked");

        // Buttons for confirmation
        Button btforconfirmation1 = new Button();
        btforconfirmation1.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold;");
        btforconfirmation1.setPrefSize(70, 40);
        Button btforconfirmation2 = new Button();
        btforconfirmation2.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btforconfirmation2.setPrefSize(70, 40);

        // Fetch booked seats from Firestore
        FirestoreService1 firestoreService = new FirestoreService1();
        List<String> bookedSeats = firestoreService.getBookedSeats();

        // Initialize seat buttons
        int seatNumber = 1;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (row != 2 && row != 5 && row != 8) {
                    String seatNumString = "Seat " + seatNumber;
                    Button seatButton = createSeatButton(gridPane, row, col, seatNumString);
                    if (bookedSeats.contains(seatNumString)) {
                        seatButton.setText("Booked");
                        seatButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                    }
                    seatNumber++;
                }
            }
        }

        libraryTagLine.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 50)); // Modern font
        libraryTagLine.setAlignment(Pos.CENTER);
        libraryTagLine.setTextFill(Color.web("white")); // Modern color
        libraryTagLine.setEffect(new Glow(0.8)); // Add glow effect
        libraryTagLine.setMaxWidth(700);

        // Title box
        VBox titleBox = new VBox(libraryTagLine);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20));
        titleBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 15;");

        // Seat layout with transparent background
        VBox seatLayout = new VBox(20, gridPane);
        seatLayout.setAlignment(Pos.CENTER);
        seatLayout.setPadding(new Insets(20));
        seatLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 15;");
        seatLayout.setMaxWidth(1000); // Set the max width to make the layout narrower

        // Adding spaces after every two rows
        for (int i = 2; i < ROWS; i += 3) {
            Region spacer = new Region();
            spacer.setMinHeight(25);
            gridPane.add(spacer, 0, i, COLS, 1);
        }

        // Confirmation boxes
        HBox confirmationBox1 = new HBox(10, btforconfirmation1, confirmation1, btforconfirmation2, confirmation2);
        confirmationBox1.setAlignment(Pos.CENTER);

        VBox confirmationBoxes = new VBox(10, confirmationBox1);
        confirmationBoxes.setAlignment(Pos.CENTER);

        Button goBackButton = new Button("Get MemberShip");
        goBackButton.setFont(Font.font(20));
        goBackButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        goBackButton.setOnAction(event -> {
            LibraryMembershipForm membershipForm = new LibraryMembershipForm();
            Scene membershipFormScene = membershipForm.createMembershipForm(SeatBookStage);
            SeatBookStage.setScene(membershipFormScene);
        });

        // Overall content layout
        VBox content = new VBox(20, logoAndBackBox, titleBox, seatLayout, confirmationBoxes, goBackButton);
        content.setAlignment(Pos.TOP_CENTER);

        // Root stack pane
        StackPane root = new StackPane();
        root.getChildren().addAll(iv, content);

        // Scene setup
        Scene scene = new Scene(root);

        // Bind background image dimensions to scene dimensions
        iv.fitWidthProperty().bind(scene.widthProperty());
        iv.fitHeightProperty().bind(scene.heightProperty());

        return scene;
    }

    private Button createSeatButton(GridPane gridPane, int row, int col, String seatNumber) {
        Button seatButton = new Button(seatNumber);
        seatButton.setPrefSize(150, 50); // Smaller size for modern look
        seatButton.setStyle(
                "-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10;");
        seatButton.setOnAction(event -> handleSeatSelection(seatButton));
        seatButton.setOnMouseEntered(event -> animateButton(seatButton));
        seatButton.setOnMouseExited(event -> resetButtonAnimation(seatButton));
        seats[row][col] = seatButton;

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.LIGHTGRAY);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        seatButton.setEffect(dropShadow);

        gridPane.add(seatButton, col, row);
        return seatButton;
    }

    private void animateButton(Button seatButton) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), seatButton);
        st.setToX(1.1);
        st.setToY(1.1);
        st.play();
    }

    private void resetButtonAnimation(Button seatButton) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), seatButton);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    private void handleSeatSelection(Button seatButton) {
        if ("Booked".equals(seatButton.getText())) {
            System.out.println("Seat is already booked.");
            return;
        }

        // Get the seat number from the button
        String seatNumber = seatButton.getText();

        // Mark the seat as booked
        seatButton.setText("Booked"); // You can remove this line if you want to keep the seat number displayed
        seatButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        System.out.println("Seat booked: " + seatNumber);

        // Save the booking to Firestore
        FirestoreService1 firestoreService = new FirestoreService1();
        Map<String, Object> bookedSeat = new HashMap<>();
        bookedSeat.put("seatNumber", seatNumber); // Save the seat number instead of "Booked"
        firestoreService.db.collection("booked_seats").add(bookedSeat);
    }
}
