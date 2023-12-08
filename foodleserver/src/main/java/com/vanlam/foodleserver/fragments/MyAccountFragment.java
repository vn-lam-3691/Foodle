package com.vanlam.foodleserver.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanlam.foodleserver.R;

import com.vanlam.foodleserver.activities.SignInActivity;
import com.vanlam.foodleserver.utils.Preferences;

public class MyAccountFragment extends Fragment {
    private ImageView imgSignOut;
    private TextView tvPartnerName;

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
                Preferences.clearData(getContext());
                startActivity(new Intent(getContext(), SignInActivity.class));
                getActivity().finish();
            }
        });

        tvPartnerName = view.findViewById(R.id.name_account);
        tvPartnerName.setText(Preferences.getDataUser(getContext()).getName());
    }
}