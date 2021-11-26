package com.example.mycompany.avitoparseapp.presentation.view.carbrandpicker;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.databinding.CarBrandPickerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.carmodelpicker.CarModelPickerFragment;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент для выбора марки автомобиля
 */
public class CarBrandPickerFragment extends Fragment {
    private AvitoParseViewModel avitoParseViewModel;
    private CarBrandPickerFragmentLayoutBinding mBinding;
    private ViewGroup container;
    private CarBrandAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CarBrandPickerFragmentLayoutBinding.inflate(inflater, container, false);
        this.container = container;
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
        mBinding.search.setOnFocusChangeListener(ofcListener);
        mBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (adapter != null && adapter.isCached()) {
                    adapter.loadFromCache();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        avitoParseViewModel = new ViewModelProvider(this.getActivity()).get(AvitoParseViewModel.class);
        avitoParseViewModel.getIsInProgressBrandListLoading().observe(this.getActivity(), this::isProgressVisible);
        avitoParseViewModel.getIsErrorAtBrandListLoading().observe(this.getActivity(), this::showErrorDialog);
        avitoParseViewModel.getBrandListData().observe(this.getActivity(), this::showBrands);
        if (savedInstanceState == null) {
            avitoParseViewModel.loadBrandsData();
        }
        mBinding.erroricon.setOnClickListener(v -> {
            showErrorDialog(false);
            isProgressVisible(true);
            avitoParseViewModel.loadBrandsData();
        });
    }

    private void showBrands(List<Brand> carBrands) {
        adapter = new CarBrandAdapter();
        adapter.setAction(brandModelsLink -> {
            CarBrandPickerFragment.this.getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .add(container.getId(), CarModelPickerFragment.newInstance(brandModelsLink))
                    .commit();
            mBinding.search.clearFocus();
        });
        adapter.setModels(carBrands);
        mBinding.recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        if (adapter != null) {
            List<Brand> filteredBrandList = new ArrayList<>();
            for (Brand b : adapter.getModels()) {
                if (b.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredBrandList.add(b);
                }
            }
            adapter.filterList(filteredBrandList);
        }
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mBinding.search.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.erroricon.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        mBinding.recyclerView.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
        mBinding.search.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(v.getId() == R.id.search && !hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
}

