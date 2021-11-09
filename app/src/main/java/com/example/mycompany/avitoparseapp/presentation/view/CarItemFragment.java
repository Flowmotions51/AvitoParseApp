package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.databinding.CarItemFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarItemPhotosAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

public class CarItemFragment extends Fragment {
    private static final String CAR_CELL_PARAM = "CarCell";
    private CarItemFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private CarItemPhotosAdapter carItemPhotosAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CarItemFragmentLayoutBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CarCell carCell = (CarCell) getArguments().get("CarCell");
        mBinding.addItemToFavoritesBtn.setOnClickListener(v -> {
            avitoParseViewModel.addCarToFavorites(carCell);
        });
        avitoParseViewModel = new ViewModelProvider(getActivity()).get(AvitoParseViewModel.class);
        avitoParseViewModel.getCarData().observe(getViewLifecycleOwner(), this::carInfoReceived);
        avitoParseViewModel.getIsInProgress2().observe(getViewLifecycleOwner(), this::isProgressVisible);
        carItemPhotosAdapter = new CarItemPhotosAdapter();
        avitoParseViewModel.loadCarData(carCell);
    }

    public void carInfoReceived(Car car) {
        carItemPhotosAdapter.setPhotoLinks(car.getPhotoLinks());
        mBinding.recyclerView.setAdapter(carItemPhotosAdapter);
        mBinding.itemName.setText(car.getCarName());
        mBinding.carDescription.setText(car.getCarDescription());
    }

    public static CarItemFragment newInstance(CarCell carCell) {
        CarItemFragment fragment = new CarItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(CAR_CELL_PARAM, carCell);
        fragment.setArguments(args);
        return fragment;
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressframelayoutCar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        avitoParseViewModel.getCarData().removeObservers(getViewLifecycleOwner());
    }
}
