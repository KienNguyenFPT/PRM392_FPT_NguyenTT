package com.example.se1711qe170207nguyencaotrungkien;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.se1711qe170207nguyencaotrungkien.databinding.ActivityMainBinding;
import com.example.se1711qe170207nguyencaotrungkien.repo.BaseModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String userId;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            Log.e("Auth", "User is not authenticated.");
        }

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_home)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private <T> void addIfNotExists(FirebaseFirestore db, String collection, T model, String... keys) {
        Map<String, Object> data = ((BaseModel) model).toMap();

        Query query = db.collection(collection);
        for (String key : keys) {
            query = query.whereEqualTo(key, data.get(key));
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null && task.getResult().isEmpty()) {
                    db.collection(collection).add(data)
                            .addOnSuccessListener(documentReference -> {
                                Log.d("Firestore", "Document added with ID: " + documentReference.getId());
                            })
                            .addOnFailureListener(e -> {
                                Log.w("Firestore", "Error adding document", e);
                            });
                } else {
                    Log.d("Firestore", collection + " already exists: " + data);
                }
            } else {
                Log.e("Firestore", "Error getting documents: ", task.getException());
            }
        });
    }


}