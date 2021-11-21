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
import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.databinding.CarModelPickerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarBrandAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.List;

/**
 * Фрагмент для выбора марки автомобиля
 */
public class CarBrandPickerFragment extends Fragment {
    private AvitoParseViewModel avitoParseViewModel;
    private CarModelPickerFragmentLayoutBinding mBinding;
    private ViewGroup container;
    private CarBrandAdapter adapter;

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
        avitoParseViewModel.getIsInProgressBrandListLoading().observe(this.getActivity(), this::isProgressVisible);
        avitoParseViewModel.getIsErrorAtBrandListLoading().observe(this.getActivity(), this::showErrorDialog);
        avitoParseViewModel.getBrandListData().observe(this.getActivity(), this::showBrands);
        avitoParseViewModel.loadBrandsData();
    }

    private void showBrands(List<Brand> carBrands) {
        adapter = new CarBrandAdapter();
        adapter.setAction(brandModelsLink ->
                CarBrandPickerFragment.this.getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .addToBackStack("CarModelPickerFragment")
                        .add(container.getId(), CarModelPickerFragment.newInstance(brandModelsLink))
                        .commit());
        adapter.setModels(carBrands);
        mBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressframelayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.errorLayout.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
    }
}
