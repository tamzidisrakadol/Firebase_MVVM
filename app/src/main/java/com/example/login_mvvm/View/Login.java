package com.example.login_mvvm.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.login_mvvm.databinding.FragmentLoginBinding;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseUser;


public class Login extends Fragment {
    FragmentLoginBinding fragmentLoginBinding;
    AuthViewModel authViewModel;
    NavController navController;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //navigation
        navController = Navigation.findNavController(view);

        //ViewModel
        authViewModel= new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser!=null){
                navController.navigate(R.id.action_login_to_home2);
            }
        });

        //loginBtn
        fragmentLoginBinding.loginBtn.setOnClickListener(v -> {
            String email = fragmentLoginBinding.loginEditText.getText().toString();
            String pass = fragmentLoginBinding.loginPassText.getText().toString();
            if (!email.isEmpty() && !pass.isEmpty()){
                authViewModel.loginUser(email,pass);
            }else{
                Toast.makeText(getContext(), "enter value", Toast.LENGTH_SHORT).show();
            }

        });

        //login -> register
        fragmentLoginBinding.LoginResgisterTV.setOnClickListener(v -> {
            navController.navigate(R.id.action_login_to_registration);
        });
    }
}