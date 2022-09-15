package com.example.login_mvvm.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.login_mvvm.Repository.AuthRepo;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    AuthRepo authRepo;
    MutableLiveData<FirebaseUser> userMutableLiveData;


    //authViewModel constructor
    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo(application);
        userMutableLiveData = authRepo.getFirebaseUserMutableLiveData();
    }

    //authregister
    public void authRegister(String name,String email,String pass,String address) {
        authRepo.register(name,email,pass,address);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
