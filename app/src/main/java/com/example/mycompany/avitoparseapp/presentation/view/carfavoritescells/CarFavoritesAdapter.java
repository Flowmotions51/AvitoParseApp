package com.example.mycompany.avitoparseapp.presentation.view.carfavoritescells;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.databinding.CarCellLayoutBinding;
import com.example.mycompany.avitoparseapp.utils.IOnCarCellAction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarFavoritesAdapter extends RecyclerView.Adapter<CarFavoritesAdapter.CarImageViewHolder> {
    private List<CarCell> carCells = new ArrayList<>();
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
        holder.setPrice(carCells.get(holder.getBindingAdapterPosition()).getPrice());
        holder.setImageByUrl(carCells.get(holder.getBindingAdapterPosition()).getPreviewImageUrl());
        holder.setDescription(carCells.get(holder.getBindingAdapterPosition()).getCarName());
        holder.setOnCLickListener(v -> helper.action(carCells.get(holder.getBindingAdapterPosition())));
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
        private TextView textView;
        private TextView textPrice;

        private CarCellLayoutBinding mBinding;

        public void setPrice(String price) {
            textPrice.setText(price + " ₽");
        }

        public CarImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = CarCellLayoutBinding.bind(itemView);
            imageView = mBinding.carImagePlaceholder;
            textView = mBinding.carDescription;
            textPrice = mBinding.price;
        }

        public void setOnCLickListener(View.OnClickListener listener) {
            imageView.setOnClickListener(listener);
        }

        public void setImageByUrl(String url) {
            Picasso.get().load(url)
                    .into(imageView);
        }

        public void setDescription(String description) {
            textView.setText(description);
        }
    }


}
