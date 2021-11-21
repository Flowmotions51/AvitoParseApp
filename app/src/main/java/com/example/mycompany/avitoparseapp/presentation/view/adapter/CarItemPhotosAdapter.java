package com.example.mycompany.avitoparseapp.presentation.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycompany.avitoparseapp.utils.IOnItemTextAction;
import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.databinding.PhotoCarLayoutBinding;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarItemPhotosAdapter extends RecyclerView.Adapter<CarItemPhotosAdapter.CarItemPhotoHolder> {

    private List<String> photoLinks;
    private IOnItemTextAction action;

    public void setPhotoLinks(List<String> photoLinks) {
        this.photoLinks = photoLinks;
    }

    public void setAction(IOnItemTextAction action) {
        this.action = action;
    }

    @NonNull
    @Override
    public CarItemPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarItemPhotoHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.photo_car_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarItemPhotoHolder holder, int position) {
        holder.setPhoto(photoLinks.get(holder.getBindingAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return photoLinks.size();
    }

    static class CarItemPhotoHolder extends RecyclerView.ViewHolder {

        private PhotoCarLayoutBinding mBinding;

        public CarItemPhotoHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = PhotoCarLayoutBinding.bind(itemView);
        }

        public void setPhoto(String photoLink) {
            Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                    .setBaseColor(Color.parseColor("#F3F3F3"))
                    .setBaseAlpha(1)
                    .setHighlightColor(Color.parseColor("#E7E7E7"))
                    .setHighlightAlpha(1)
                    .setDropoff(50)
                    .build();
            ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
            shimmerDrawable.setShimmer(shimmer);
            Picasso.get().load(photoLink)
                    .placeholder(shimmerDrawable)
                    .into(mBinding.carPhoto);
        }
    }
}
