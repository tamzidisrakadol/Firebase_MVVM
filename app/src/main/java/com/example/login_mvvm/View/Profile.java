package com.example.login_mvvm.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.login_mvvm.R;
import com.example.login_mvvm.ViewModels.AuthViewModel;
import com.example.login_mvvm.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends Fragment {

    FragmentProfileBinding fragmentProfileBinding;
    AuthViewModel authViewModel;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater,container,false);
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);

        authViewModel=new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {

        });
        authViewModel.getLogUser().observe(getViewLifecycleOwner(), loggout -> {
            if (loggout){
             //   navController.navigate(R.id.action_profile_to_splash);
                requireActivity().finish();
                Toast.makeText(getContext(), "User Sign-Out", Toast.LENGTH_SHORT).show();
            }
        });

        fragmentProfileBinding.profileSignOutBtn.setOnClickListener(v ->
                authViewModel.logOut());

    }
}