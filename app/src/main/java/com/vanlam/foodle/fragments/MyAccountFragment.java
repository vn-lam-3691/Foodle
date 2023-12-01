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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vanlam.foodle.R;
import com.vanlam.foodle.activities.OrderManagementActivity;
import com.vanlam.foodle.activities.SignInActivity;
import com.vanlam.foodle.activities.UserInformationActivity;
import com.vanlam.foodle.adapters.Preferences;

public class MyAccountFragment extends Fragment {
    private ImageView imgSignOut;
    private LinearLayout layoutSavedLocation;
    private LinearLayout layoutHistoryOrder;
    private TextView tvUserName;

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
        layoutSavedLocation = view.findViewById(R.id.layout_saved_location);
        layoutSavedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserInformationActivity.class);
                startActivity(intent);
            }
        });

        imgSignOut = view.findViewById(R.id.image_sign_out);
        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutAccount();
            }
        });

        layoutHistoryOrder = view.findViewById(R.id.layout_history_order);
        layoutHistoryOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OrderManagementActivity.class));
            }
        });

        tvUserName = view.findViewById(R.id.name_account);
        tvUserName.setText(Preferences.getDataUser(getContext()).getName());
    }

    private void signOutAccount() {
        startActivity(new Intent(getContext(), SignInActivity.class));
        Preferences.clearData(getContext());
        getActivity().finish();
    }
}