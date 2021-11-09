package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycompany.avitoparseapp.IOnItemTextAction;
import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.databinding.CarModelPickerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarModelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент для выбора модели автомобиля
 */
public class CarModelPickerFragment extends Fragment {
    private CarModelPickerFragmentLayoutBinding mBinding;
    private ViewGroup container;
    private List<String> carModels;
    private int backStackNumber;
    private String brand;

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
        brand = getArguments().getString("Brand");
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
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .addToBackStack("CarCellsFragment")
                        .add(container.getId(), carCellsFragment, getTag())
                        .commit();
            }
        });
        mBinding.recyclerView.setAdapter(adapter);
    }

    public List<String> getModelsOfBrand(String brand) {
        List<String> brandModels = new ArrayList<>();
        switch (brand) {
            case "vaz_lada/": {
                brandModels.add("2101");
                brandModels.add("2107");
                brandModels.add("2109");
                brandModels.add("2114_samara");
                brandModels.add("Vesta");
                brandModels.add("Priora");
                brandModels.add("Kalina");
                break;
            }
            case "Audi/": {
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
            case "Toyota/": {
                brandModels.add("Chaser");
                brandModels.add("Mark_II");
                brandModels.add("Crown");
                brandModels.add("Supra");
                break;
            }
            case "BMW/": {
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
