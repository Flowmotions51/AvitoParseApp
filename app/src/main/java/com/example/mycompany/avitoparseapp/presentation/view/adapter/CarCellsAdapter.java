package com.example.mycompany.avitoparseapp.presentation.view.adapter;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycompany.avitoparseapp.databinding.CarCellLayoutBinding;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.utils.IOnCarCellAction;
import com.example.mycompany.avitoparseapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarCellsAdapter extends RecyclerView.Adapter<CarCellsAdapter.CarImageViewHolder> {
    private List<CarCell> carCells;
    private IOnCarCellAction helper;

    public void setHelper(IOnCarCellAction helper) {
        this.helper = helper;
    }

    public void setImageUris(List<CarCell> carCells) {
        this.carCells = carCells;
    }

    public List<CarCell> getCarCells() {
        return carCells;
    }

    @NonNull
    @Override
    public CarImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_cell_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarImageViewHolder holder, int position) {
        holder.setImageByUrl(carCells.get(holder.getBindingAdapterPosition()).getPreviewImageUrl());
        holder.setDescription(carCells.get(holder.getBindingAdapterPosition()).getCarName());
        holder.setOnCLickListener(v -> helper.action(carCells.get(holder.getBindingAdapterPosition())));
        holder.isCellFavoriteImage.setVisibility(View.GONE);
        if(carCells.get(holder.getBindingAdapterPosition()).isFavorite()) {
            holder.isCellFavoriteImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return carCells.size();
    }

    @Override
    public void onViewRecycled(@NonNull CarImageViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public static class CarImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private ImageView isCellFavoriteImage;
        private TextView textView;

        private CarCellLayoutBinding mBinding;

        public CarImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = CarCellLayoutBinding.bind(itemView);
            imageView = mBinding.carImagePlaceholder;
            textView = mBinding.carDescription;
            isCellFavoriteImage = mBinding.isCellFavoriteImage;
        }

        public void setOnCLickListener(View.OnClickListener listener) {
            imageView.setOnClickListener(listener);
        }

        public void setImageByUrl(String url) {
            Picasso.get()
                    .load(url)
                    .into(imageView);
        }

        public void setDescription(String description) {
            textView.setText(description);
        }
    }
}
