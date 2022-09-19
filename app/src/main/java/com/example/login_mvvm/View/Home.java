package com.example.login_mvvm.View;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.login_mvvm.Model.FUser;
import com.example.login_mvvm.R;
import com.example.login_mvvm.ViewModels.AuthViewModel;
import com.example.login_mvvm.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Home extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    AuthViewModel authViewModel;
    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false);
        return fragmentHomeBinding.getRoot();
    }


    //onbackPressmethod
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController  = Navigation.findNavController(view);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser!=null){
                fragmentHomeBinding.emailTV.setText(firebaseUser.getEmail());
            }
        });


        fragmentHomeBinding.homeProfileImg.setOnClickListener(v -> {
            navController.navigate(R.id.action_home2_to_profile);
        });
    }
}