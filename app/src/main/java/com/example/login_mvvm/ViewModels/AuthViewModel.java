package com.example.login_mvvm.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.login_mvvm.Repository.AuthRepo;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {
    AuthRepo authRepo = new AuthRepo();
    MutableLiveData<FirebaseUser> userMutableLiveData;

    public void authRegister(String name,String email,String pass,String address){
        authRepo.register(name, email, pass, address);
    }
}
