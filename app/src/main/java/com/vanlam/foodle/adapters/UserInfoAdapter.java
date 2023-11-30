package com.vanlam.foodle.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vanlam.foodle.R;
import com.vanlam.foodle.listeners.UserInfoListener;
import com.vanlam.foodle.models.User;

import java.util.List;


public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder> {
    private List<User> listInfoUser;
    private Context mContext;
    private UserInfoListener infoListener;

    public UserInfoAdapter(List<User> listInfoUser, UserInfoListener listener) {
        this.listInfoUser = listInfoUser;
        this.infoListener = listener;
    }

    @NonNull
    @Override
    public UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_location_info, parent, false);
        mContext = parent.getContext();
        return new UserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User item = listInfoUser.get(position);
        holder.getTvUserName().setText(item.getName());
        holder.getTvPhoneNumber().setText(item.getPhoneNumber());
        holder.getTvAddress().setText(item.getAddress());

        if (item.isDefault()) {
            holder.getTvDefault().setVisibility(View.VISIBLE);
        }
        else {
            holder.getTvDefault().setVisibility(View.GONE);
        }
        holder.getLayoutContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = getInfoAtPosition(position);
                infoListener.onClick(user);
            }
        });
    }

    public User getInfoAtPosition(int pos) {
        return listInfoUser.get(pos);
    }

    @Override
    public int getItemCount() {
        return listInfoUser.size();
    }

    static class UserInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName, tvPhoneNumber, tvAddress, tvDefault;
        private ConstraintLayout layoutContainer;

        public UserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            tvAddress = itemView.findViewById(R.id.tv_user_address);
            tvDefault = itemView.findViewById(R.id.tv_default);
            layoutContainer = itemView.findViewById(R.id.layout_container_info);
        }

        public TextView getTvUserName() {
            return tvUserName;
        }

        public void setTvUserName(TextView tvUserName) {
            this.tvUserName = tvUserName;
        }

        public TextView getTvPhoneNumber() {
            return tvPhoneNumber;
        }

        public void setTvPhoneNumber(TextView tvPhoneNumber) {
            this.tvPhoneNumber = tvPhoneNumber;
        }

        public TextView getTvAddress() {
            return tvAddress;
        }

        public void setTvAddress(TextView tvAddress) {
            this.tvAddress = tvAddress;
        }

        public TextView getTvDefault() {
            return tvDefault;
        }

        public void setTvDefault(TextView tvDefault) {
            this.tvDefault = tvDefault;
        }
        public ConstraintLayout getLayoutContainer() {
            return layoutContainer;
        }

        public void setLayoutContainer(ConstraintLayout layoutContainer) {
            this.layoutContainer = layoutContainer;
        }
    }
}
