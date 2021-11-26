package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycompany.avitoparseapp.databinding.MainContainerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.carbrandpicker.CarBrandPickerFragment;

/**
 * Фрагмент контейнер для фрагментов во вкладке "Поиск"
 */
public class MainContainerFragment extends Fragment {
    private MainContainerFragmentLayoutBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = MainContainerFragmentLayoutBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            CarBrandPickerFragment carBrandPickerFragment = new CarBrandPickerFragment();
            getChildFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(mBinding.mainContainer.getId(), carBrandPickerFragment)
                    .commit();
        }
    }

    public static MainContainerFragment newInstance() {
        return new MainContainerFragment();
    }
}
