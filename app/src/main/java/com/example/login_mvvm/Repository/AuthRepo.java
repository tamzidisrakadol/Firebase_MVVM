package com.example.login_mvvm.Repository;

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
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    MutableLiveData<FirebaseUser> firebaseUserMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return firebaseUserMutableLiveData;
    }


    public void register(String email,String password,String name,String address){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Map<String,Object> map = new HashMap<>();
                   map.put("name",name);
                   map.put("email",email);
                   map.put("address",address);
                   FirebaseDatabase.getInstance().getReference()
                           .child("Users")
                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .updateChildren(map)
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   Log.d("tag","sucess");
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Log.d("tag","msg "+e.getMessage().toString());
                               }
                           });
               }else{
                   Log.d("tag","onmsg "+task.getException().toString());
               }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag","msg "+e.getMessage().toString());
            }
        });

    }



}
