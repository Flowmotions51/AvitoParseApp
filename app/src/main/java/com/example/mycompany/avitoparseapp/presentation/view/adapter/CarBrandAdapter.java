package com.example.mycompany.avitoparseapp.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.databinding.ModelPickerItemLayoutBinding;
import com.example.mycompany.avitoparseapp.utils.IOnItemTextAction;

import java.util.List;

public class CarBrandAdapter extends RecyclerView.Adapter<CarBrandAdapter.CarModelViewHolder> {

    public void setModels(List<Brand> models) {
        this.models = models;
    }

    private List<Brand> models;
    private IOnItemTextAction action;

    public void setAction(IOnItemTextAction action) {
        this.action = action;
    }

    @NonNull
    @Override
    public CarModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarModelViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.model_picker_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarModelViewHolder holder, int position) {
        holder.setModel(models.get(holder.getBindingAdapterPosition()).getName());
        holder.setOnCLickListener(v ->
                action.onAction(models.get(holder.getBindingAdapterPosition()).getLink()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class CarModelViewHolder extends RecyclerView.ViewHolder {

        private ModelPickerItemLayoutBinding mBinding;

        public CarModelViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = ModelPickerItemLayoutBinding.bind(itemView);
        }

        public void setModel(String text) {
            mBinding.modelName.setText(text);
        }

        public void setOnCLickListener(View.OnClickListener listener) {
            mBinding.modelName.setOnClickListener(listener);
        }
    }
}
