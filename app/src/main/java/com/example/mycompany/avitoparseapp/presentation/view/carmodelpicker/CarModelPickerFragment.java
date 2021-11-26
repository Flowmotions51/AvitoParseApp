package com.example.mycompany.avitoparseapp.presentation.view.carmodelpicker;

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
import com.example.mycompany.avitoparseapp.data.model.Model;
import com.example.mycompany.avitoparseapp.databinding.CarModelPickerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.carcells.CarCellsFragment;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.ArrayList;
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
        brand = getArguments().getString(BRAND_PARAM);
        avitoParseViewModel.getIsInProgressModelsListLoading().observe(this.getActivity(), this::isProgressVisible);
        avitoParseViewModel.getIsErrorAtModelsListLoading().observe(this.getActivity(), this::showErrorDialog);
        avitoParseViewModel.getModelsListData().observe(this.getActivity(), this::showModels);
        adapter = new CarModelAdapter();
        if (savedInstanceState == null) {
            avitoParseViewModel.loadModelsData(brand);
        }
        mBinding.erroricon.setOnClickListener(v -> {
            showErrorDialog(false);
            isProgressVisible(true);
            avitoParseViewModel.loadModelsData(brand);
        });
    }

    private void showModels(List<Model> carBrands) {
        adapter = new CarModelAdapter();
        adapter.setModels(carBrands);
        mBinding.recyclerView.setAdapter(adapter);
        adapter.setAction(model -> {
            CarModelPickerFragment.this.getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .add(container.getId(), CarCellsFragment.newInstance(brand, model), getTag())
                    .commit();
            mBinding.search.clearFocus();
        });
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mBinding.recyclerView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        mBinding.search.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.erroricon.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        mBinding.recyclerView.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
        mBinding.search.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
    }

    private void filter(String text) {
        if (adapter != null) {
            List<Model> filteredBrandList = new ArrayList<>();
            for (Model m : adapter.getModels()) {
                if (m.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredBrandList.add(m);
                }
            }
            adapter.filterList(filteredBrandList);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        avitoParseViewModel.getModelsListData().removeObservers(this.getActivity());
    }

    public static CarModelPickerFragment newInstance(String brandModelsLink) {
        CarModelPickerFragment fragment = new CarModelPickerFragment();
        Bundle args = new Bundle();
        args.putString(BRAND_PARAM, brandModelsLink);
        fragment.setArguments(args);
        return fragment;
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus) {

            if (v.getId() == R.id.search && !hasFocus) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
}
