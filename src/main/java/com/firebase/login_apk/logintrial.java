package com.firebase.login_apk;

import com.firebase.BrowseLib.BrowseLibrary;
import com.firebase.Login_page.AboutUs;
import com.firebase.seat_booking.SeatBookingSystem;
import com.firebase.news.News;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class logintrial extends Application {
    private Label welcomeLabel;
    String username = "";
    Stage primaryStage;
    @Override
    public void start(Stage mainLayout2) {
        this.primaryStage = mainLayout2;
        mainLayout2.setMaximized(true);
       // mainLayout2.setHeight(1080);
        //mainLayout2.setWidth(1920);
       
        // Background images
        Image backgroundImage1 = new Image("Welcome1.png");
        Image backgroundImage2 = new Image("Welcome1.png");


        

        VBox backgroundContainer = new VBox();
        backgroundContainer.getChildren().addAll(
                new ImageView(backgroundImage1),
                new ImageView(backgroundImage2));

        // Layout for main content
        VBox mainLayout = new VBox(30);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setLayoutY(10);
        mainLayout.setPrefSize(1000, 1200);

        // Add space above the header
        VBox headerContainer = new VBox();
        headerContainer.setPadding(new Insets(4, 0, 0, 0));

        // Header with menu buttons
        HBox header = new HBox(30);
        header.setAlignment(Pos.TOP_RIGHT);
        header.setStyle("-fx-background-color:transparent;");

        // Logo Image and Label
        Image logoImage = new Image("logo.png"); // Update the path to the logo image
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(85); // Set the width of the logo image
        logoImageView.setFitHeight(85); // Set the height of the logo image
        logoImageView.setPreserveRatio(true);
        
        Label logoLabel = new Label("𝐀𝐛𝐡𝐲𝐚𝐬𝐢𝐤𝐚.𝐜𝐨𝐦");
        logoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        logoLabel.setStyle("-fx-text-fill: black;");
        logoLabel.setPadding(new Insets(20, 790, 0, 0));

        Button LogoutButton = createStyledButton("Logout");
        LogoutButton.setPrefSize(200, 50);
        Button browseButton = createStyledButton("Browse Library");
        browseButton.setPrefSize(300, 50);
        Button AboutButton = createStyledButton("About Us");
        AboutButton.setPrefSize(200, 50);

        //Action for the Logout
        LogoutButton.setOnAction(event -> {
            Landing landingPage = new Landing();
            try {
                Scene landingScene = landingPage.createLandingScene(mainLayout2);
                mainLayout2.setScene(landingScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
               
        //Action for the Browse Library Button
        browseButton.setOnAction(e->{
            BrowseLibrary Bl = new BrowseLibrary();
            Scene BrowseLibraryScene = Bl.createLibrary(mainLayout2);
            mainLayout2.setScene(BrowseLibraryScene);
        });

         //Action for the About us Button
         AboutButton.setOnAction(e->{
            AboutUs About = new AboutUs();
            Scene AboutScene = About.createAboutScene(mainLayout2);
            mainLayout2.setScene(AboutScene);
        });

        header.getChildren().addAll(logoImageView,logoLabel,browseButton,AboutButton,LogoutButton);
        headerContainer.getChildren().add(header);

        Region spacer = new Region();
        spacer.setPrefHeight(80);

        // Welcome label
        welcomeLabel = new Label();
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        welcomeLabel.setVisible(false);

        // Middle box
        VBox mvb = new VBox(20);
        mvb.setPrefHeight(400);
        mvb.setPrefWidth(900);
        mvb.setPadding(new Insets(50));
        mvb.setAlignment(Pos.CENTER_LEFT);
        mvb.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 0, 0, 0.6),
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        // Title and search bar
        Label title = new Label("Abhyasika.com");
        title.setStyle("-fx-font-size: 50px; -fx-text-fill: white; -fx-font-weight: bold");
        TextField searchField = new TextField();
        searchField.setPromptText("Search the library here...");
        searchField.setStyle("-fx-text-fill: white ;-fx-font-size:22px;-fx-background-color: Black");
        searchField.setMaxWidth(600);
        searchField.setPrefHeight(70);

        Rectangle clip = new Rectangle(500, 500);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        searchField.setClip(clip);

        // Welcome message
        Label taglinelb = new Label("|| विद्यां ददाति विनयं विनयाद् याति पात्रताम् ||");
        taglinelb.setStyle("-fx-text-fill:white;-fx-font-weight: bold;-fx-font-size: 40px");
        Label welcomeMessage = new Label(
                "Welcome to Library.com! your comprehensive solution for managing and accessing a vast collection of data resources with ease. Our platform is designed to streamline your library experience.");
        welcomeMessage.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        welcomeMessage.setWrapText(true);
        welcomeMessage.setMaxWidth(600);

        mvb.getChildren().addAll(title, searchField, taglinelb, welcomeMessage);

        // Side image
        Image image = new Image("Reading (2).jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);

        // Add drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        mvb.setEffect(dropShadow);
        imageView.setEffect(dropShadow);

        // HBox for mvb and side image at the same level
        HBox contentBox = new HBox(0);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(mvb, imageView);

         // Create cards box
        HBox cardsBox = createImageCardsBox();
        
        // Library Benefits Section
        HBox benefitsBox = createBenefitsBox();

        // Student Reviews Section
        VBox reviewsBox = createReviewsBox();

        Region spacer1 = new Region();
        spacer1.setPrefHeight(350);

        // Contact Section
        HBox contactBox = createContactBox();

        mainLayout.getChildren().addAll(headerContainer,spacer,contentBox,cardsBox,benefitsBox,reviewsBox,contactBox);

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundContainer,mainLayout);

        // ScrollPane to make the content scrollable
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        // Scene
        Scene scene = new Scene(scrollPane);
        mainLayout2.setTitle("Library Homepage");
        mainLayout2.setScene(scene);
        mainLayout2.show();

        // Resize mainLayout when window is resized
        mainLayout2.widthProperty().addListener((obs, oldVal, newVal) -> {
            mainLayout.setPrefWidth(newVal.doubleValue());
        });
        mainLayout2.heightProperty().addListener((obs, oldVal, newVal) -> {
            mainLayout.setPrefHeight(newVal.doubleValue());
        });

    }

    //Styling for Header Buttons
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent;" +
                "-fx-background-radius: 30; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 30px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20;");
        return button;
    }
    //Function cards
   
    //Main HBox for Benefits
    private HBox createBenefitsBox() {
        HBox benefitsBox = new HBox(50); 
        benefitsBox.setPadding(new Insets(20));
        benefitsBox.setAlignment(Pos.CENTER);
        benefitsBox.setStyle("-fx-background-color: transparent;"); 

        VBox userFriendlyBox = createInfoBox(
                "User-Friendly System",
                new String[] { "Simple and easy to use", "Online and offline data storage",
                        "Automatic updates and backups", "Flexible and fully configurable" },
                "-fx-background-color: linear-gradient(to bottom, #f5f7fa, #c3cfe2);"); 
                                                                                       
        addHoverAnimation(userFriendlyBox);

        VBox engagementBox = createInfoBox(
                "Increased Member Engagement",
                new String[] { "Accessible from anywhere", "Smartphone and tablet access", "Reliable and secure",
                        "Review and track library functions" },
                "-fx-background-color: linear-gradient(to bottom, #e0c3fc, #8ec5fc);"); 
                                                                                        
        addHoverAnimation(engagementBox);

        VBox costEffectiveBox = createInfoBox(
                "Cost Effective",
                new String[] { "Reduces paperwork", "Lower maintenance costs", "No manual entries",
                        "Error-free and accurate database" },
                "-fx-background-color: linear-gradient(to bottom, #fbc2eb, #a6c1ee);"); 
                                                                                        
        addHoverAnimation(costEffectiveBox);
        benefitsBox.getChildren().addAll(userFriendlyBox, engagementBox, costEffectiveBox);

        return benefitsBox;
    }
    //VBox for Benefits Styling Box 
    private VBox createInfoBox(String title, String[] details, String backgroundColor) {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(400, 350);
        box.setPadding(new Insets(20));
        box.setStyle(backgroundColor
                + " -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;"); 
                                                                                                    
        VBox detailsBox = new VBox(5);
        for (String detail : details) {
            Label detailLabel = new Label(detail);
            detailLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #555555;"); 
            detailsBox.getChildren().add(detailLabel);
        }
        box.getChildren().addAll(titleLabel, detailsBox);
        return box;
    }

    //Hover Animation for Benefits HomePage
    private void addHoverAnimation(VBox box) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), box);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1); 
        scaleTransition.setToY(1.1);

        box.setOnMouseEntered(e -> {
            scaleTransition.setRate(1);
            scaleTransition.play();
        });

        box.setOnMouseExited(e -> {
            scaleTransition.setRate(-1);
            scaleTransition.play();
        });
    }

    //Student Review HomePage
    private VBox createReviewCard(String studentName, String reviewText, String[] highlights) {
        VBox reviewCard = new VBox(10);
        reviewCard.setAlignment(Pos.TOP_CENTER);
        reviewCard.setPrefSize(400, 350);
        reviewCard.setPadding(new Insets(20));
        reviewCard.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label nameLabel = new Label(studentName);
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label reviewLabel = new Label(reviewText);
        reviewLabel.setWrapText(true);
        reviewLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        reviewCard.getChildren().addAll(nameLabel, reviewLabel);

        return reviewCard;
    }
    
    //Student Reviews HomePage
    private VBox createReviewsBox() {
        VBox reviewsBox = new VBox(20);
        reviewsBox.setPadding(new Insets(20));
        reviewsBox.setAlignment(Pos.CENTER);
        reviewsBox.setStyle("-fx-background-color: tranpernt;");

        Label reviewsTitle = new Label("Student Reviews");
        reviewsTitle.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        HBox reviewsContent = new HBox(20);
        reviewsContent.setAlignment(Pos.CENTER);

        VBox review1 = createReviewCard("Student 1",
                "Great library with excellent facilities!",
                new String[] { "excellent facilities", "comfortable and safe place" });
        VBox review2 = createReviewCard("Student 2",
                "A very comfortable and safe place to study.",
                new String[] { "comfortable and safe place" });
        VBox review3 = createReviewCard("Student 3",
                "Love the 24/7 availability and the computer lab.",
                new String[] { "24/7 availability", "computer lab" });
        VBox review4 = createReviewCard("Student 4",
                "The digital catalog is very convenient for my research.",
                new String[] { "digital catalog", "convenient for research" });
        VBox review5 = createReviewCard("Student 5",
                "The staff are very helpful and the environment is perfect for studying.",
                new String[] { "helpful staff", "perfect environment for studying" });

        reviewsContent.getChildren().addAll(review1, review2, review3, review4, review5);
        reviewsBox.getChildren().addAll(reviewsTitle, reviewsContent);

        return reviewsBox;
    }
   
    //HBox for Comapny Info And  ConatctFrom HomePage
    private HBox createContactBox() {
        HBox contactBox = new HBox(40);
        contactBox.setPadding(new Insets(40));
        contactBox.setAlignment(Pos.CENTER);

        VBox contactInfo = createContactInfo();
        VBox contactForm = createContactForm();

        contactBox.getChildren().addAll(contactInfo, contactForm);
        contactBox.setStyle("-fx-background-color: linear-gradient(to right, #434343, #000000);");

        return contactBox;
    }
    
    //Company Info
    private VBox createContactInfo() {
        VBox contactInfo = new VBox(20);
        contactInfo.setPadding(new Insets(20));
        contactInfo.setAlignment(Pos.CENTER_LEFT);

        Label contactTitle = new Label("Talk With Us");
        contactTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        contactTitle.setTextFill(Color.WHITE);

        Label contactDesc = new Label("You can share your details with us to receive updates and\nemail notifications");
        contactDesc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        contactDesc.setTextFill(Color.LIGHTGRAY);

        HBox addressBox = createInfoBox("location.png", "123 Library Street, Knowledge City, Country");
        HBox emailBox = createInfoBox("email.png", "Library.com@gmail.com");
        HBox phoneBox = createInfoBox("phone.jpg", "+91 1234567890");
        HBox socialMediaBox = createInfoBox("socialmedia.jpg", "Follow us on Facebook, Twitter, LinkedIn");

        contactInfo.getChildren().addAll(contactTitle, contactDesc, addressBox, emailBox, phoneBox, socialMediaBox);
        return contactInfo;
    }
    
    // Styling for Company Info home page
    private HBox createInfoBox(String iconPath, String infoText) {
        HBox infoBox = new HBox(10);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(40);
        icon.setFitHeight(40);

        Label infoLabel = new Label(infoText);
        infoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        infoLabel.setTextFill(Color.WHITE);

        infoBox.getChildren().addAll(icon, infoLabel);
        return infoBox;
    }
    
    //contact Form of company HomePage
    private VBox createContactForm() {
        VBox contactForm = new VBox(20);
        contactForm.setPadding(new Insets(20));
        contactForm.setAlignment(Pos.CENTER_LEFT);

        Label formTitle = new Label("Contact Form / Optional");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        formTitle.setTextFill(Color.WHITE);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");

        TextArea Message = new TextArea();
        Message.setPromptText(" Message");
        Message.setStyle("-fx-background-color: #333333; -fx-text-fill: white;");
        Message.setPrefRowCount(4);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #ff4500; -fx-text-fill: white; -fx-font-weight: bold;");
        submitButton.setPrefSize(200, 40);

        contactForm.getChildren().addAll(formTitle, nameField, emailField, phoneField, Message, submitButton);
        contactForm.setPrefHeight(650); 

        return contactForm;
    }
    // Cards with images and buttons
    private HBox createImageCardsBox() {
    HBox cardsBox = new HBox(10);
    cardsBox.setAlignment(Pos.CENTER);
    cardsBox.setPadding(new Insets(20));

    // Create individual cards
    VBox card1 = createCard("Book Seat", "Seat.png");
    VBox card3 = createCard("News", "News.jpg");
    //VBox card4 = createCard("Books", "home.png");

    // Add cards to the HBox
    cardsBox.getChildren().addAll(card1,card3);

    return cardsBox;
}

    private VBox createCard(String title, String imagePath) {
    VBox card = new VBox(20);
    card.setAlignment(Pos.CENTER);
    card.setPrefSize(230, 280);
    card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 10;");
    card.setEffect(new DropShadow());

    ImageView cardImage = new ImageView(new Image(imagePath));
    cardImage.setFitWidth(180);
    cardImage.setFitHeight(180);
    cardImage.setPreserveRatio(true);

    Label cardLabel = new Label(title);
    cardLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    cardLabel.setTextFill(Color.BLACK);

    Button cardButton1 = new Button("View More");
    cardButton1.setStyle("-fx-background-color: #FF4500; -fx-text-fill: white; -fx-font-weight: bold;");
    cardButton1.setOnAction(e -> {
        if (title.equals("Book Seat")) {
            SeatBookingSystem Seat = new SeatBookingSystem();
            Scene SeatScene = Seat.createSeatBookingScene(primaryStage,username);
            primaryStage.setScene(SeatScene);
        } else if (title.equals("News")) {
            News news = new News();
            VBox mainLayout = new VBox(); // Assuming you have a VBox or another layout in your scene
            news.createScene(primaryStage);
            Scene newsScene = new Scene(mainLayout, 1920, 1080); 
            primaryStage.setScene(newsScene);
        }/*  else if (title.equals("Books")) {
            // handle Books
        }*/
    });

    card.getChildren().addAll(cardImage, cardLabel, cardButton1);

    return card;
}

}




