package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.IOnItemTextAction;
import com.example.mycompany.avitoparseapp.databinding.CarModelPickerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarModelAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class CarModelPickerFragment extends Fragment {
    private CarModelPickerFragmentLayoutBinding mBinding;
    private ViewGroup container;
    private List<String> carModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CarModelPickerFragmentLayoutBinding.inflate(inflater, container, false);
        this.container = container;
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String brand = getArguments().getString("Brand");
        carModels = getModelsOfBrand(brand);
        CarModelAdapter adapter = new CarModelAdapter(carModels);
        adapter.setAction(new IOnItemTextAction() {
            @Override
            public void onAction(String model) {
                Bundle bundle = new Bundle();
                bundle.putString("Model", brand + model);
                CarCellsFragment carCellsFragment = new CarCellsFragment();
                carCellsFragment.setArguments(bundle);
                CarModelPickerFragment.this.getParentFragmentManager().beginTransaction()
                        .addToBackStack("CarCellsFragment")
                        .add(container.getId(), carCellsFragment)
                        .commit();
            }
        });
        mBinding.recyclerView.setAdapter(adapter);
    }

    public List<String> getModelsOfBrand(String brand) {
        List<String> brandModels = new ArrayList<>();
        switch (brand) {
            case "Audi/" : {
                brandModels.add("A1");
                brandModels.add("A3");
                brandModels.add("A4");
                brandModels.add("A5");
                brandModels.add("A6");
                brandModels.add("A7");
                brandModels.add("A8");
                brandModels.add("Q7");
                brandModels.add("Q8");
                break;
            }
            case "Toyota/" : {
                brandModels.add("Chaser");
                brandModels.add("Mark_II");
                brandModels.add("Crown");
                brandModels.add("Supra");
                break;
            }
            case "BMW/" : {
                brandModels.add("1-seriya");
                brandModels.add("2-seriya");
                brandModels.add("3-seriya");
                brandModels.add("4-seriya");
                brandModels.add("5-seriya");
                break;
            }
        }
        return brandModels;
    }
}