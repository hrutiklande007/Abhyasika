package com.firebase.notification;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Notification {

    public static void showNotification(Stage owner, String message) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10px;");
        label.setMinWidth(Region.USE_PREF_SIZE);
        label.setMinHeight(Region.USE_PREF_SIZE);

        HBox box = new HBox(label);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-radius: 10; -fx-background-color: rgba(0, 0, 0, 0.8);");

        popup.getContent().add(box);

        // Position the popup at the bottom right corner of the owner stage
        Scene scene = owner.getScene();
        popup.setOnShown(e -> {
            popup.setX(scene.getWindow().getX() + scene.getWidth() - popup.getWidth() - 20);
            popup.setY(scene.getWindow().getY() + scene.getHeight() - popup.getHeight() - 20);
        });

        popup.show(owner);

        // Fade out transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), box);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> popup.hide());
        fadeTransition.play();
    }
}
