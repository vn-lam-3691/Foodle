package com.vanlam.foodle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanlam.foodle.R;
import com.vanlam.foodle.models.ViewPagerModel;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {
    private List<ViewPagerModel> listItem;

    public ViewPagerAdapter(List<ViewPagerModel> listItem) {
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
        ViewPagerViewHolder viewPagerViewHolder = new ViewPagerViewHolder(view);
        return viewPagerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        ViewPagerModel item = listItem.get(position);
        holder.getVpg_img().setImageResource(item.getImgPath());
        holder.getTv_title().setText(item.getTitle());
        holder.getTv_desc().setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    static class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        private ImageView vpg_img;
        private TextView tv_title, tv_desc;

        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            vpg_img = itemView.findViewById(R.id.vpg_image);
            tv_title = itemView.findViewById(R.id.vpg_tv_title);
            tv_desc = itemView.findViewById(R.id.vpg_tv_desc);
        }

        public ImageView getVpg_img() {
            return vpg_img;
        }

        public void setVpg_img(ImageView vpg_img) {
            this.vpg_img = vpg_img;
        }

        public TextView getTv_title() {
            return tv_title;
        }

        public void setTv_title(TextView tv_title) {
            this.tv_title = tv_title;
        }

        public TextView getTv_desc() {
            return tv_desc;
        }

        public void setTv_desc(TextView tv_desc) {
            this.tv_desc = tv_desc;
        }
    }
}
