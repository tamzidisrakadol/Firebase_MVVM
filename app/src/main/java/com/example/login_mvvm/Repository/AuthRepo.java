package com.example.login_mvvm.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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


    public AuthRepo(Application application) {
        this.application = application;
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("name", name);
//                    map.put("email", email);
//                    map.put("address", address);
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("Users")
//                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .updateChildren(map)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Log.d("tag", "sucess");
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("tag", "msg " + e.getMessage().toString());
//                                    Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
                } else {
                    Log.d("tag", "onmsg " + task.getException().toString());
                    Toast.makeText(application, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "msg " + e.getMessage().toString());
                Toast.makeText(application, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }
}
