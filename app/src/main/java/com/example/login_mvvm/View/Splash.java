package com.example.login_mvvm.View;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login_mvvm.R;
import com.example.login_mvvm.databinding.FragmentSplashBinding;


public class Splash extends Fragment {
    FragmentSplashBinding fragmentSplashBinding;
    NavController navController;


    //backpress
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(getContext())
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                requireActivity().finish();
                            }
                        }).create()
                        .show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,backPressedCallback);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSplashBinding=FragmentSplashBinding.inflate(inflater,container,false);
        return fragmentSplashBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        navController = Navigation.findNavController(view);
        //login_btn
        fragmentSplashBinding.splashloginBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_splash_to_login);
        });

        //registerBtn
        fragmentSplashBinding.splashRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_splash_to_registration);
            }
        });



    }
}