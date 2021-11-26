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
import androidx.viewpager.widget.ViewPager;

import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.databinding.CarItemFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.ItemImagesViewPagerAdapter;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.VerticalViewPager;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

public class CarItemFavoritesFragment extends Fragment {
    private static final String CAR_CELL_PARAM = "CarCellFav";
    private static final String CAR_ITEM_PARAM = "CarItemFav";
    private static final String IMAGE_POSITION = "PositionFav";
    private CarItemFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private VerticalViewPager imageItemViewPager;
    private ItemImagesViewPagerAdapter itemImagesViewPagerAdapter;
    private int viewPagerCurrentPosition;
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
        mBinding.viewPager.setOffscreenPageLimit(3);
        avitoParseViewModel.getIsErrorAtFavoriteItemLoading().observe(getViewLifecycleOwner(), this::showErrorDialog);
        avitoParseViewModel.getCarItemDataFavorites().observe(getViewLifecycleOwner(), this::carInfoReceived);
        avitoParseViewModel.getIsInProgressFavoriteItemLoading().observe(getViewLifecycleOwner(), this::isProgressVisible);
        mBinding.addItemToFavoritesBtn.setVisibility(View.GONE);
        if (savedInstanceState == null) {
            carCell = (CarCell) getArguments().get(CAR_CELL_PARAM);
            avitoParseViewModel.loadCarItemFavoriteData(carCell);
        } else {
            carCell = savedInstanceState.getParcelable(CAR_CELL_PARAM);
            car = savedInstanceState.getParcelable(CAR_ITEM_PARAM);
            if (car != null) {
                itemImagesViewPagerAdapter = new ItemImagesViewPagerAdapter(getActivity(), car.getPhotoLinks());
                viewPagerCurrentPosition = savedInstanceState.getInt(IMAGE_POSITION);
                mBinding.viewPager.post(() -> mBinding.viewPager.setCurrentItem(viewPagerCurrentPosition, false));
                mBinding.viewPager.setOffscreenPageLimit(3);
                imageItemViewPager.setAdapter(itemImagesViewPagerAdapter);
                mBinding.itemName.setText(car.getCarName());
                mBinding.carDescription.setText(car.getCarDescription());
            }
        }
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBinding.phone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL);
            String telephone = mBinding.phone.getText().toString();
            intent.setData(Uri.parse("tel:" + telephone));
            if (ActivityCompat.checkSelfPermission(CarItemFavoritesFragment.this.getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(IMAGE_POSITION, viewPagerCurrentPosition);
        outState.putParcelable(CAR_ITEM_PARAM, car);
        outState.putParcelable(CAR_CELL_PARAM, carCell);
    }

    public void carInfoReceived(Car car) {
        this.car = car;
        mBinding.erroriconwrapper.setVisibility(View.GONE);
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
        mBinding.erroriconwrapper.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
