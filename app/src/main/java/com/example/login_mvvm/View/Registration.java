package com.example.login_mvvm.View;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.login_mvvm.R;
import com.example.login_mvvm.ViewModels.AuthViewModel;
import com.example.login_mvvm.databinding.FragmentRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Registration extends Fragment {
    FragmentRegistrationBinding fragmentRegistrationBinding;
    NavController navController;
    AuthViewModel authViewModel;


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

        //hide actionbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        navController = Navigation.findNavController(view);


        //viewModel & live observer
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                navController.navigate(R.id.action_registration_to_home2);
            }
        });

        //registerbtn -> creating user & navigate to home
        fragmentRegistrationBinding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fragmentRegistrationBinding.registerUserName.getText().toString();
                String email = fragmentRegistrationBinding.registerEmail.getText().toString();
                String pass = fragmentRegistrationBinding.registerPassword.getText().toString();
                String address = fragmentRegistrationBinding.registerAddress.getText().toString();

                if(isDataValied()){
                    authViewModel.authRegister(name,email,pass,address);
                }
            }
        });

        //register -> login btn
        fragmentRegistrationBinding.registerLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registration_to_login);
            }
        });

    }

    //checking data of user
    private boolean isDataValied(){
        if (fragmentRegistrationBinding.registerUserName.getText().toString().isEmpty()) {
            fragmentRegistrationBinding.registerUserName.setError("plz enter your name");
            fragmentRegistrationBinding.registerUserName.requestFocus();
            return false;
        }
        else if (fragmentRegistrationBinding.registerEmail.getText().toString().isEmpty()) {
            fragmentRegistrationBinding.registerEmail.setError("plz enter your email");
            fragmentRegistrationBinding.registerEmail.requestFocus();
            return false;
        }
        else if (fragmentRegistrationBinding.registerPassword.getText().toString().isEmpty()) {
            fragmentRegistrationBinding.registerPassword.setError("plz enter your password");
            fragmentRegistrationBinding.registerPassword.requestFocus();
            return false;
        }
        else if(fragmentRegistrationBinding.registerPassword.getText().toString().length()<6){
            fragmentRegistrationBinding.registerPassword.setError("plz enter more than 6 digit for your password");
            fragmentRegistrationBinding.registerPassword.requestFocus();
            return false;
        }
        else if(fragmentRegistrationBinding.registerAddress.getText().toString().isEmpty()){
            fragmentRegistrationBinding.registerAddress.setError("plz enter your address");
            fragmentRegistrationBinding.registerAddress.requestFocus();
            return false;
        }
        return true;
    }


}