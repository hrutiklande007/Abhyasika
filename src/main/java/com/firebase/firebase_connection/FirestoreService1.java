package com.firebase.firebase_connection;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreService1 {
    public Firestore db;

    public FirestoreService1() {
        try {
            if (FirebaseApp.getApps().isEmpty()) { // Check if FirebaseApp is already initialized
                FileInputStream serviceAccount = new FileInputStream(
                        "**Enter address of .json file**");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String signUp(String username, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("password", password);

        ApiFuture<DocumentReference> future = db.collection("users").add(user);
        try {
            return future.get().getId();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean login(String username, String password) {
        ApiFuture<QuerySnapshot> future = db.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get();

        try {
            QuerySnapshot querySnapshot = future.get();
            return !querySnapshot.isEmpty();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String addLibraryMember(Map<String, Object> memberData) {
        // Ensure the 'remainingDays' field is set based on membership type
        String membershipType = (String) memberData.get("membership");
        memberData.put("remainingDays", calculateRemainingDays(membershipType));

        ApiFuture<DocumentReference> future = db.collection("library_members").add(memberData);
        try {
            return future.get().getId();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
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

    public boolean isAdmin(String username) {
        ApiFuture<QuerySnapshot> future = db.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("role", "admin")
                .get();

        try {
            QuerySnapshot querySnapshot = future.get();
            return !querySnapshot.isEmpty();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<QueryDocumentSnapshot> getUserInformation() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("library_members").get();
        return future.get().getDocuments();
    }

    public void updateUserMembershipDays(String documentId, int remainingDays)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("library_members").document(documentId);
        ApiFuture<WriteResult> future = docRef.update("remainingDays", remainingDays);
        future.get();
    }

    public CollectionReference getUserInformationCollection() {
        return db.collection("library_members");
    }

    public List<String> getBookedSeats() {
        ApiFuture<QuerySnapshot> future = db.collection("booked_seats").get();
        try {
            QuerySnapshot querySnapshot = future.get();
            List<String> bookedSeats = new ArrayList<>();
            for (QueryDocumentSnapshot document : querySnapshot) {
                String seatNumber = document.getString("seatNumber");
                bookedSeats.add(seatNumber);
            }
            return bookedSeats;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void addLibraryMemberWithSuccessListener(Map<String, Object> memberData,
            ApiFutureCallback<DocumentReference> callback) {
        // Ensure the 'remainingDays' field is set based on membership type
        String membershipType = (String) memberData.get("membership");
        memberData.put("remainingDays", calculateRemainingDays(membershipType));

        ApiFuture<DocumentReference> future = db.collection("library_members").add(memberData);
        ApiFutures.addCallback(future, callback);
    }

    public void removeExpiredMembers() {
        ApiFuture<QuerySnapshot> future = db.collection("library_members")
                .whereEqualTo("remainingDays", 0)
                .get();

        try {
            QuerySnapshot querySnapshot = future.get();
            for (QueryDocumentSnapshot document : querySnapshot) {
                String seatNumber = document.getString("seatNumber");
                if (seatNumber != null) {
                    deleteBookedSeatBySeatNumber(seatNumber);
                }
                // Optionally, you can delete the member document from library_members as well
                // db.collection("library_members").document(document.getId()).delete();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void deleteBookedSeatBySeatNumber(String seatNumber) {
        ApiFuture<QuerySnapshot> future = db.collection("booked_seats")
                .whereEqualTo("seatNumber", seatNumber)
                .get();

        try {
            QuerySnapshot querySnapshot = future.get();
            for (QueryDocumentSnapshot document : querySnapshot) {
                db.collection("booked_seats").document(document.getId()).delete();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
