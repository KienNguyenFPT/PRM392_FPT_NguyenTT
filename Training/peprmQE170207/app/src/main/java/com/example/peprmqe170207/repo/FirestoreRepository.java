package com.example.peprmqe170207.repo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.peprmqe170207.model.BaseModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FirestoreRepository<T> {
    private final FirebaseFirestore db;
    private final Class<T> type;
    private final String collectionName;
    private final Context context;

    public FirestoreRepository(FirebaseFirestore db, Class<T> type, String collectionName, Context context) {
        this.db = db;
        this.type = type;
        this.collectionName = collectionName;
        this.context = context;
    }

    public CompletableFuture<DocumentReference> add(T data, String... keys) {
        CompletableFuture<DocumentReference> future = new CompletableFuture<>();

        Map<String, Object> dataMap = ((BaseModel) data).toMap();

        Query query = db.collection(collectionName);
        for (String key : keys) {
            query = query.whereEqualTo(key, dataMap.get(key));
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null && task.getResult().isEmpty()) {
                    db.collection(collectionName).add(data)
                            .addOnSuccessListener(docRef -> {
                                String generatedId = docRef.getId();
                                db.collection(collectionName).document(generatedId)
                                        .update("id", generatedId)
                                        .addOnSuccessListener(aVoid -> {
                                            future.complete(docRef);
                                            Toast.makeText(context, "Add success!", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            future.completeExceptionally(e);
                                            Toast.makeText(context, "Failed to set ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            })
                            .addOnFailureListener(e -> {
                                future.completeExceptionally(e);
                                Toast.makeText(context, "Add fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Log.d("Firestore", collectionName + " already exists: " + dataMap);
                    future.completeExceptionally(new Exception("Document already exists"));
                }
            } else {
                Log.e("Firestore", "Error getting documents: ", task.getException());
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }



    public CompletableFuture<List<T>> getAll() {
        CompletableFuture<List<T>> future = new CompletableFuture<>();
        db.collection(collectionName).get()
                .addOnSuccessListener(querySnapshot -> {
                    List<T> result = querySnapshot.toObjects(type);

                    for (T item : result) {
                        String id = ((BaseModel) item).getId();
                        ((BaseModel) item).setId(id);
                    }

                    future.complete(result);
                    Toast.makeText(context, "Get all success!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                    Toast.makeText(context, "Get all fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return future;
    }

    public CompletableFuture<T> getById(String id) {
        CompletableFuture<T> future = new CompletableFuture<>();
        db.collection(collectionName).document(id).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        T data = snapshot.toObject(type);
                        ((BaseModel) data).setId(id);
                        future.complete(data);
                        Toast.makeText(context, "Get success!", Toast.LENGTH_SHORT).show();
                    } else {
                        future.complete(null);
                        Toast.makeText(context, "Not exist!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                    Toast.makeText(context, "Get fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return future;
    }


    public CompletableFuture<Void> update(String id, Map<String, Object> data) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        data.put("id", id);

        db.collection(collectionName).document(id).set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    future.complete(null);
                    Toast.makeText(context, "Update success!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                    Toast.makeText(context, "Update fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return future;
    }

    public CompletableFuture<Void> delete(String id) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        db.collection(collectionName).document(id).delete()
                .addOnSuccessListener(aVoid -> {
                    future.complete(null);
                    Toast.makeText(context, "Delete success!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                    Toast.makeText(context, "Delete fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return future;
    }

    public CompletableFuture<List<T>> search(String field, String value) {
        CompletableFuture<List<T>> future = new CompletableFuture<>();
        Query query = db.collection(collectionName).whereEqualTo(field, value);
        query.get()
                .addOnSuccessListener(querySnapshot -> {
                    future.complete(querySnapshot.toObjects(type));
                    Toast.makeText(context, "Search success!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                    Toast.makeText(context, "Search fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return future;
    }

    public CompletableFuture<List<T>> filter(String field, Object value) {
        CompletableFuture<List<T>> future = new CompletableFuture<>();
        Query query = db.collection(collectionName).whereEqualTo(field, value);
        query.get()
                .addOnSuccessListener(querySnapshot -> {
                    future.complete(querySnapshot.toObjects(type));
                    Toast.makeText(context, "Filter success!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                    Toast.makeText(context, "Filter fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return future;
    }
}