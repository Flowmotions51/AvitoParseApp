package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.databinding.CarItemFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.ItemImagesViewPagerAdapter;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.VerticalViewPager;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

public class CarItemFavoritesFragment extends Fragment {
    private static final String CAR_CELL_PARAM = "CarCellFav";
    private CarItemFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private VerticalViewPager imageItemViewPager;
    private ItemImagesViewPagerAdapter itemImagesViewPagerAdapter;
    private CarCell carCell;
    private Car car;

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
        avitoParseViewModel = new ViewModelProvider(getActivity()).get(AvitoParseViewModel.class);
        imageItemViewPager = mBinding.viewPager;
        mBinding.addItemToFavoritesBtn.setVisibility(View.GONE);
        if (savedInstanceState == null) {
            carCell = (CarCell) getArguments().get("CarCellFav");
            avitoParseViewModel.getIsErrorAtFavoriteItemLoading().observe(getViewLifecycleOwner(), this::showErrorDialog);
            avitoParseViewModel.getCarItemDataFavorites().observe(getViewLifecycleOwner(), this::carInfoReceived);
            avitoParseViewModel.getIsInProgressFavoriteItemLoading().observe(getViewLifecycleOwner(), this::isProgressVisible);
//            avitoParseViewModel.loadCarItemFavoriteData(carCell);
        } else {
            carCell = savedInstanceState.getParcelable("CarCellFav");
            car = savedInstanceState.getParcelable("CarItemFav");
            itemImagesViewPagerAdapter = new ItemImagesViewPagerAdapter(getActivity(), car.getPhotoLinks());
            imageItemViewPager.setAdapter(itemImagesViewPagerAdapter);
            mBinding.itemName.setText(car.getCarName());
            mBinding.carDescription.setText(car.getCarDescription());
            showErrorDialog(false);
            isProgressVisible(false);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("CarItemFav", car);
        outState.putParcelable("CarCellFav", carCell);
    }

    public void carInfoReceived(Car car) {
        this.car = car;
        mBinding.errorLayout.setVisibility(View.GONE);
        itemImagesViewPagerAdapter = new ItemImagesViewPagerAdapter(getActivity(), car.getPhotoLinks());
        imageItemViewPager.setAdapter(itemImagesViewPagerAdapter);
        mBinding.itemName.setText(car.getCarName());
        mBinding.carDescription.setText(car.getCarDescription());
        mBinding.phone.setText(car.getPhone());
    }

    public static CarItemFavoritesFragment newInstance(CarCell carCell) {
        CarItemFavoritesFragment fragment = new CarItemFavoritesFragment();
        Bundle args = new Bundle();
        args.putParcelable(CAR_CELL_PARAM, carCell);
        fragment.setArguments(args);
        return fragment;
    }

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.errorLayout.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressframelayoutCar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
