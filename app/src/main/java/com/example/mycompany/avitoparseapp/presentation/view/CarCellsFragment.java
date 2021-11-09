package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mycompany.avitoparseapp.IOnCarCellAction;
import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.databinding.CarCellsFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarCellsAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.List;

/**
 * Фрагмент для отображения объявлений, согласено выбранным параметрам
 */
public class CarCellsFragment extends Fragment {
    private static final String MODEL_PARAM = "Model";
    private CarCellsFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private CarCellsAdapter recyclerViewCarCellsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewGroup container;
    private String carModel;
    private AlertDialog.Builder builderDialog;
    private AlertDialog alertDialog;

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
        if(savedInstanceState == null) {
            swipeRefreshLayout = mBinding.swiperLayout;
            recyclerViewCarCellsAdapter = new CarCellsAdapter();
            recyclerViewCarCellsAdapter.setHelper(carCell ->
                    CarCellsFragment.this
                            .getParentFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .addToBackStack("CarItemFragment")
                            .add(container.getId(), CarItemFragment.newInstance(carCell), getTag())
                            .commit()
            );
            avitoParseViewModel = new ViewModelProvider(this.getActivity()).get(AvitoParseViewModel.class);
            carModel = getArguments().getString("Model");
            avitoParseViewModel.getIsInProgress().observe(this.getActivity(), this::isProgressVisible);
            avitoParseViewModel.getCarCellsData().observe(this.getActivity(), this::showCars);

            avitoParseViewModel.getIsError().observe(this.getActivity(), this::showErrorDialog);

            avitoParseViewModel.loadCellsData(carModel, true);
            swipeRefreshLayout.setOnRefreshListener(() ->
                    avitoParseViewModel.loadCellsData(carModel, false));
        }
    }

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.errorLayout.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
    }

    public static CarCellsFragment newInstance(String model) {
        CarCellsFragment fragment = new CarCellsFragment();
        Bundle args = new Bundle();
        args.putString(MODEL_PARAM, model);
        fragment.setArguments(args);
        return fragment;
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.progressframelayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showCars(List<CarCell> carCells) {
        recyclerViewCarCellsAdapter.setImageUris(carCells);
        mBinding.recyclerView.setAdapter(recyclerViewCarCellsAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //avitoParseViewModel.getCarCellsData().removeObservers(this.getActivity());
    }
}
