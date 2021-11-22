package com.example.mycompany.avitoparseapp.presentation.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.databinding.CarItemFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarItemPhotosAdapter;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.ItemImagesViewPagerAdapter;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.VerticalViewPager;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;
import com.example.mycompany.avitoparseapp.utils.SetCellFavorite;

public class CarItemFragment extends Fragment {
    private static final String CAR_CELL_PARAM = "CarCell";
    private CarItemFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private CarItemPhotosAdapter carItemPhotosAdapter;
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
        if(savedInstanceState == null) {
            carCell = (CarCell) getArguments().get("CarCell");
            if(carCell.isFavorite()) {
                mBinding.addItemToFavoritesBtn.setActivated(true);
                mBinding.addItemToFavoritesBtn.setSelected(true);
            }
            avitoParseViewModel.getIsErrorAtItemLoading().observe(getViewLifecycleOwner(), this::showErrorDialog);
            avitoParseViewModel.getCarItemData().observe(getViewLifecycleOwner(), this::carInfoReceived);
            avitoParseViewModel.getIsInProgressItemLoading().observe(getViewLifecycleOwner(), this::isProgressVisible);
            avitoParseViewModel.loadCarItemData(carCell);
        } else {
            carCell = (CarCell) savedInstanceState.getParcelable("CarCell");
            car = (Car)savedInstanceState.getParcelable("CarItem");
            itemImagesViewPagerAdapter = new ItemImagesViewPagerAdapter(getActivity(), car.getPhotoLinks());
            imageItemViewPager.setAdapter(itemImagesViewPagerAdapter);
            mBinding.itemName.setText(car.getCarName());
            mBinding.carDescription.setText(car.getCarDescription());
            showErrorDialog(false);
            isProgressVisible(false);
        }
        mBinding.addItemToFavoritesBtn.setOnClickListener(v -> {
            avitoParseViewModel.addCarToFavorites(carCell);
            if(avitoParseViewModel.getCarCellsFavorites().contains(carCell)) {
                for (Fragment f : getParentFragmentManager().getFragments()) {
                    if (f instanceof SetCellFavorite) {
                        mBinding.addItemToFavoritesBtn.setActivated(true);
                        mBinding.addItemToFavoritesBtn.setSelected(true);
                        ((SetCellFavorite) (f)).setCellFavorite(carCell, true);
                    }
                }
            } else {
                for (Fragment f : getParentFragmentManager().getFragments()) {
                    if (f instanceof SetCellFavorite) {
                        mBinding.addItemToFavoritesBtn.setActivated(false);
                        mBinding.addItemToFavoritesBtn.setSelected(false);
                        ((SetCellFavorite) (f)).setCellFavorite(carCell, false);
                    }
                }
            }
        });

        mBinding.phone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL);
            String telephone = mBinding.phone.getText().toString();
            intent.setData(Uri.parse("tel:" + telephone));
            if(ActivityCompat.checkSelfPermission(CarItemFragment.this.getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        });
    }

    public void setFavoritesButton(boolean isActivated) {
        mBinding.addItemToFavoritesBtn.setActivated(isActivated);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("CarItem", car);
        outState.putParcelable("CarCell", carCell);
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

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.errorLayout.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
    }
}
