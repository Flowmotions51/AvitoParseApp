package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycompany.avitoparseapp.IOnItemTextAction;
import com.example.mycompany.avitoparseapp.databinding.CarModelPickerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarModelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент для выбора марки автомобиля
 */
public class CarBrandPickerFragment extends Fragment {
    private CarModelPickerFragmentLayoutBinding mBinding;
    private ViewGroup container;
    private List<String> carBrands = new ArrayList<>();

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
        carBrands.add("Audi");
        carBrands.add("Toyota");
        carBrands.add("BMW");
        CarModelAdapter adapter = new CarModelAdapter(carBrands);
        adapter.setAction(new IOnItemTextAction() {
            @Override
            public void onAction(String brand) {
                Bundle bundle = new Bundle();
                bundle.putString("Brand", brand + "/");
                CarModelPickerFragment carModelPickerFragment = new CarModelPickerFragment();
                carModelPickerFragment.setArguments(bundle);
                CarBrandPickerFragment.this.getParentFragmentManager().beginTransaction()
                        .addToBackStack("CarModelPickerFragment")
                        .add(container.getId(), carModelPickerFragment)
                        .commit();
            }
        });
        mBinding.recyclerView.setAdapter(adapter);
    }
}
