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
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.databinding.CarFavoritesFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarCellsAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.ArrayList;
import java.util.List;

public class CarFavoritesFragment extends Fragment {
    private CarFavoritesFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private CarCellsAdapter recyclerViewCarCellsAdapter;
    private ViewGroup container;

    public static CarFavoritesFragment newInstance() {
        return new CarFavoritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CarFavoritesFragmentLayoutBinding.inflate(inflater, container, false);
        this.container = container;
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewCarCellsAdapter = new CarCellsAdapter();
        recyclerViewCarCellsAdapter.setHelper(carCell -> {
            CarFavoritesFragment.this.getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .addToBackStack("CarItemFragmentFavs")
                    .add(container.getId(), CarItemFavoritesFragment.newInstance(carCell), getTag())
                    .commit();
        });
        avitoParseViewModel = new ViewModelProvider(getActivity()).get(AvitoParseViewModel.class);
        avitoParseViewModel.getCarFavoritesCellsData().observe(this.getActivity(), this::showCars);
        if(savedInstanceState == null) {
            avitoParseViewModel.loadCarFav();
        } else {
            recyclerViewCarCellsAdapter.setImageUris(savedInstanceState.getParcelableArrayList("CarCellsFavoritesList"));
            recyclerViewCarCellsAdapter.notifyDataSetChanged();
        }
    }

    private void showCars(List<CarCell> carCells) {
        recyclerViewCarCellsAdapter.setImageUris(carCells);
        mBinding.recyclerView.setAdapter(recyclerViewCarCellsAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(recyclerViewCarCellsAdapter != null) {
            outState.putParcelableArrayList("CarCellsFavoritesList", new ArrayList<>(recyclerViewCarCellsAdapter.getCarCells()));
        }
    }
}
