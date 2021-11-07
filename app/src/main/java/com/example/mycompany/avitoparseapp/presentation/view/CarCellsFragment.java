package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycompany.avitoparseapp.IOnCarCellAction;
import com.example.mycompany.avitoparseapp.databinding.CarCellsFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarCellsAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.List;

public class CarCellsFragment extends Fragment {
    private CarCellsFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private RecyclerView recyclerView;
    private CarCellsAdapter recyclerViewCarCellsAdapter;
    private ViewGroup container;
    private String carModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = CarCellsFragmentLayoutBinding.inflate(inflater, container, false);
        this.container = container;
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carModel = getArguments().getString("Model");
        recyclerViewCarCellsAdapter = new CarCellsAdapter();
        recyclerViewCarCellsAdapter.setHelper(new IOnCarCellAction() {
            @Override
            public void action(CarCell carCell) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("CarCell", carCell);
                CarItemFragment carItemFragment = new CarItemFragment();
                carItemFragment.setArguments(bundle);
                CarCellsFragment.this.getParentFragmentManager().beginTransaction()
                        .addToBackStack("CarItemFragment")
                        .add(container.getId(), carItemFragment)
                        .commit();
            }
        });
        avitoParseViewModel = new ViewModelProvider(requireActivity()).get(AvitoParseViewModel.class);
        avitoParseViewModel.getIsInProgress().observe(getViewLifecycleOwner(), this::isProgressVisible);
        avitoParseViewModel.resetLiveDataCarCells();
        avitoParseViewModel.getCarCellsData().observe(getViewLifecycleOwner(), this::showCars);
        avitoParseViewModel.loadCellsData(carModel);
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressframelayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showCars(List<CarCell> carCells) {
        recyclerViewCarCellsAdapter.setImageUris(carCells);
        mBinding.recyclerView.setAdapter(recyclerViewCarCellsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        avitoParseViewModel.getCarCellsData().removeObservers(getViewLifecycleOwner());
    }
}
