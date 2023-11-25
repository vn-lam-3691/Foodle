package com.vanlam.foodle.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vanlam.foodle.R;
import com.vanlam.foodle.activities.SignInActivity;
import com.vanlam.foodle.adapters.Preferences;

public class MyAccountFragment extends Fragment {
    private ImageView imgSignOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgSignOut = view.findViewById(R.id.image_sign_out);
        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutAccount();
            }
        });
    }

    private void signOutAccount() {
        startActivity(new Intent(getContext(), SignInActivity.class));
        Preferences.clearData(getContext());
        getActivity().finish();
    }
}