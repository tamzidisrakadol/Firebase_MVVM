package com.example.login_mvvm.Repository;

import android.app.Application;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class AuthRepo {
    Application application;
    FirebaseAuth firebaseAuth;
    public MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;

    //authrepo constructor
    public AuthRepo(Application application) {
        this.application = application;
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
    }

    //create register method
    public void register(String name,String email,String password,String address) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                    //save data to Firebase_Database
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("address", address);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(application, "User Created", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                              //Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
             // Toast.makeText(application, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }
}
