package com.example.login_mvvm.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.login_mvvm.Model.FUser;
import com.example.login_mvvm.R;
import com.example.login_mvvm.ViewModels.AuthViewModel;
import com.example.login_mvvm.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Profile extends Fragment {

    FragmentProfileBinding fragmentProfileBinding;
    AuthViewModel authViewModel;
    NavController navController;
    private static final int REQUEST_GALLERY = 33;
    ProgressDialog progressDialog;

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
        getUserDetails();
        authViewModel.getLogUser().observe(getViewLifecycleOwner(), loggout -> {
            if (loggout){
             //   navController.navigate(R.id.action_profile_to_splash);
                requireActivity().finish();
                Toast.makeText(getContext(), "User Sign-Out", Toast.LENGTH_SHORT).show();
            }
        });
        fragmentProfileBinding.profileImgview.setOnClickListener(v ->
                getPermission());

        fragmentProfileBinding.profileSignOutBtn.setOnClickListener(v ->
                authViewModel.logOut());

    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_GALLERY){
            if (data.getData() != null){
                fragmentProfileBinding.profileImgview.setImageURI(data.getData());
                progressDialog= new ProgressDialog(requireActivity());
                progressDialog.setTitle("uploading");
                progressDialog.setMessage("uploading your profile image");
                progressDialog.setCancelable(false);
                progressDialog.show();
                final StorageReference reference = FirebaseStorage.getInstance().getReference()
                        .child("profileImages")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        reference.putFile(data.getData())
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        FirebaseDatabase
                                                .getInstance()
                                                .getReference()
                                                .child("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("profileImg")
                                                .setValue(uri.toString());
                                        Toast.makeText(requireContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                progressDialog.dismiss();
                                Toast.makeText(requireActivity(), "Profile Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }

    private void getUserDetails() {
        progressDialog= new ProgressDialog(requireActivity());
        progressDialog.setTitle("loading");
        progressDialog.setMessage("loading your profile");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()){
                            progressDialog.hide();
                            FUser fuser = snapshot.getValue(FUser.class);
                            fragmentProfileBinding.profileUserNameTV.setText(fuser.getName());
                            fragmentProfileBinding.profileEmailTV.setText(fuser.getEmail());
                            fragmentProfileBinding.profileAddressTV.setText(fuser.getAddress());
                            Glide.with(requireActivity()).load(fuser.getProfileImg()).placeholder(R.drawable.profile).into(fragmentProfileBinding.profileImgview);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.hide();
                    }
                });
    }

}