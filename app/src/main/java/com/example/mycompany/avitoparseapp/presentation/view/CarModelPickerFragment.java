package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.data.model.Model;
import com.example.mycompany.avitoparseapp.databinding.CarModelPickerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarModelAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.List;

/**
 * Фрагмент для выбора модели автомобиля
 */
public class CarModelPickerFragment extends Fragment {
    private static final String BRAND_PARAM = "Brand";
    private AvitoParseViewModel avitoParseViewModel;
    private CarModelPickerFragmentLayoutBinding mBinding;
    private ViewGroup container;
    private String brand;
    private CarModelAdapter adapter;

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
        avitoParseViewModel = new ViewModelProvider(this.getActivity()).get(AvitoParseViewModel.class);
        brand = getArguments().getString("Brand");
        avitoParseViewModel.getIsInProgressModelsListLoading().observe(this.getActivity(), this::isProgressVisible);
        avitoParseViewModel.getIsErrorAtModelsListLoading().observe(this.getActivity(), this::showErrorDialog);
        avitoParseViewModel.getModelsListData().observe(this.getActivity(), this::showModels);
        adapter = new CarModelAdapter();
        avitoParseViewModel.loadModelsData(brand);
    }

    private void showModels(List<Model> carBrands) { adapter = new CarModelAdapter();
        adapter.setModels(carBrands);
        mBinding.recyclerView.setAdapter(adapter);
        adapter.setAction(model -> CarModelPickerFragment.this.getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(container.getId(), CarCellsFragment.newInstance(brand, model), getTag())
                .commit());
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressframelayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.errorLayout.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
    }

    public static CarModelPickerFragment newInstance(String brandModelsLink) {
        CarModelPickerFragment fragment = new CarModelPickerFragment();
        Bundle args = new Bundle();
        args.putString(BRAND_PARAM, brandModelsLink);
        fragment.setArguments(args);
        return fragment;
    }
}
