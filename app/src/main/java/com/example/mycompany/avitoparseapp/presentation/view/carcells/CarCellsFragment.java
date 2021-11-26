package com.example.mycompany.avitoparseapp.presentation.view.carcells;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.databinding.CarCellsFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.view.caritem.CarItemFragment;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.List;

/**
 * Фрагмент для отображения объявлений, согласено выбранным параметрам
 */
public class CarCellsFragment extends Fragment {
    private static final String MODEL_PARAM = "Model";
    private static final String BRAND_PARAM = "Brand";
    private CarCellsFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private CarCellsAdapter recyclerViewCarCellsAdapter;
    private ViewGroup container;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String carBrand;
    private String carModel;

    public static CarCellsFragment newInstance(String brand, String model) {
        CarCellsFragment fragment = new CarCellsFragment();
        Bundle args = new Bundle();
        args.putString(BRAND_PARAM, brand);
        args.putString(MODEL_PARAM, model);
        fragment.setArguments(args);
        return fragment;
    }

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
        swipeRefreshLayout = mBinding.swiperLayout;
        recyclerViewCarCellsAdapter = new CarCellsAdapter();
        mBinding.recyclerView.setLayoutManager(
                new WrapContentGridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerViewCarCellsAdapter.setHelper(carCell ->
                CarCellsFragment.this
                        .getParentFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(container.getId(), CarItemFragment.newInstance(carCell), getTag())
                        .commit()
        );
        avitoParseViewModel = new ViewModelProvider(this.getActivity()).get(AvitoParseViewModel.class);
        carBrand = getArguments().getString("Brand");
        carModel = getArguments().getString("Model");
        avitoParseViewModel.getIsInProgressCellsLoading().observe(getActivity(), this::isProgressVisible);
        avitoParseViewModel.getCarCellsMutableLiveData().observe(getActivity(), this::showCars);
        avitoParseViewModel.getIsErrorAtCellsLoading().observe(getActivity(), this::showErrorDialog);

        avitoParseViewModel.getMarkerCarCellsFavorites().observe(getActivity(), this::setFavoriteMutubleLiveData);
        mBinding.erroricon.setOnClickListener(v -> {
            showErrorDialog(false);
            isProgressVisible(true);
            avitoParseViewModel.loadCarCellsData(carBrand, carModel, true);
        });
        swipeRefreshLayout.setOnRefreshListener(() ->
                avitoParseViewModel.loadCarCellsData(carBrand, carModel, false));
        if (savedInstanceState == null) {
            avitoParseViewModel.loadCarCellsData(carBrand, carModel, true);
        }
    }

    private void showErrorDialog(Boolean aBoolean) {
        mBinding.recyclerView.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
        mBinding.swiperLayout.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
        mBinding.erroricon.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
    }

    private void isProgressVisible(Boolean isVisible) {
        mBinding.recyclerView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        mBinding.swiperLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        mBinding.progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showCars(List<CarCell> carCells) {
        recyclerViewCarCellsAdapter.setImageUris(carCells);
        mBinding.recyclerView.setAdapter(recyclerViewCarCellsAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        avitoParseViewModel.getMarkerCarCellsFavorites().removeObservers(getActivity());
        avitoParseViewModel.setNewMarkerCarCellsFavorites();
    }

    /**
     * Ищем добавленное в избранное объявление и помечаем его как "избранное"
     * @param cell
     */
    public void setFavoriteMutubleLiveData(CarCell cell) {
        for (CarCell carCell : recyclerViewCarCellsAdapter.getCarCells()) {
            if (carCell.getLinkToItem().equals(cell.getLinkToItem())) {
                carCell.setFavorite(!carCell.isFavorite());
                recyclerViewCarCellsAdapter.notifyDataSetChanged();
            }
        }
    }
}
