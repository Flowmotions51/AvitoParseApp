package com.example.mycompany.avitoparseapp.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycompany.avitoparseapp.IOnItemTextAction;
import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.databinding.ModelPickerItemLayoutBinding;

import java.util.List;

public class CarModelAdapter extends RecyclerView.Adapter<CarModelAdapter.CarModelViewHolder> {

    private List<String> models;
    private IOnItemTextAction action;

    public CarModelAdapter(List<String> models) {
        this.models = models;
    }

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
        holder.setModel(models.get(holder.getBindingAdapterPosition()));
        holder.setOnCLickListener(v ->
                action.onAction(models.get(holder.getBindingAdapterPosition())));
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
