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
    MutableLiveData<Boolean> logUser;

    //authViewModel constructor
    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo(application);
        userMutableLiveData = authRepo.getFirebaseUserMutableLiveData();
        logUser = authRepo.getLogUserLiveData();
    }

    //authregister
    public void authRegister(String name,String email,String pass,String address) {
        authRepo.register(name,email,pass,address);
    }

    //login user
    public void loginUser(String email,String pass){
        authRepo.loginUser(email, pass);
    }

    //logout user
    public void logOut(){
        authRepo.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLogUser() {
        return logUser;
    }

}
