package com.example.login_mvvm.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login_mvvm.R;
import com.example.login_mvvm.ViewModels.AuthViewModel;
import com.example.login_mvvm.databinding.FragmentRegistrationBinding;
import com.google.firebase.auth.FirebaseUser;


public class Registration extends Fragment {
    FragmentRegistrationBinding fragmentRegistrationBinding;
    NavController navController;
    AuthViewModel authViewModel;


    public Registration() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegistrationBinding=FragmentRegistrationBinding.inflate(inflater,container,false);
        return fragmentRegistrationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        navController = Navigation.findNavController(view);

        //viewmodel
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        String name = fragmentRegistrationBinding.registerUserName.getText().toString();
        String email = fragmentRegistrationBinding.registerEmail.getText().toString();
        String pass = fragmentRegistrationBinding.registerPassword.getText().toString();
        String address = fragmentRegistrationBinding.registerAddress.getText().toString();

        fragmentRegistrationBinding.registerBtn.setOnClickListener(v -> {
            if (name.length()==0 && email.length()==0 && pass.length()==0 && address.length()==0){
                return;
            }else {
                authViewModel.authRegister(name,email,pass,address);
            }
        });



    }
}