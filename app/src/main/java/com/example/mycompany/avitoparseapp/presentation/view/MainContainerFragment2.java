package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycompany.avitoparseapp.databinding.MainContainerFavoritesFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

public class MainContainerFragment2 extends Fragment {

    private MainContainerFavoritesFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = MainContainerFavoritesFragmentLayoutBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getChildFragmentManager().beginTransaction()
                .addToBackStack("CarBrandPickerFragment2")
                .add(mBinding.containerFavorites.getId(), new CarFavoritesFragment())
                .commit();
    }

    public static MainContainerFragment2 newInstance() {
        return new MainContainerFragment2();
    }
}
