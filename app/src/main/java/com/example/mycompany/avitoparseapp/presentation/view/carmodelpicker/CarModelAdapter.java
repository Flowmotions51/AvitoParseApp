package com.example.mycompany.avitoparseapp.presentation.view.carmodelpicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.data.model.Model;
import com.example.mycompany.avitoparseapp.databinding.ModelPickerItemLayoutBinding;
import com.example.mycompany.avitoparseapp.utils.IOnItemTextAction;

import java.util.ArrayList;
import java.util.List;

public class CarModelAdapter extends RecyclerView.Adapter<CarModelAdapter.CarModelViewHolder> {

    public void setModels(List<Model> models) {
        this.models = models;
    }
    private List<Model> cache = new ArrayList<>();

    private List<Model> models = new ArrayList<>();
    private IOnItemTextAction action;

    public void setAction(IOnItemTextAction action) {
        this.action = action;
    }


    public List<Model> getModels() {
        return models;
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

    public void filterList(List<Model> filteredBrandList) {
        cache = models;
        models = filteredBrandList;
        notifyDataSetChanged();
    }

    public void loadFromCache() {
        models = cache;
        notifyDataSetChanged();
    }

    public boolean isCached() {
        return !cache.isEmpty();
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
