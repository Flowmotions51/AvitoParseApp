package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.data.parser.Parser;
import com.example.mycompany.avitoparseapp.data.repository.ParserRepository;
import com.example.mycompany.avitoparseapp.databinding.MainContainerFragmentLayoutBinding;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

public class MainContainerFragment extends Fragment {
    private MainContainerFragmentLayoutBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;

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
        Parser parser = new Parser();
        ParserRepository parserRepository = new ParserRepository(parser);
        avitoParseViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AvitoParseViewModel(parserRepository);
            }
        }).get(AvitoParseViewModel.class);
        Bundle bundle = new Bundle();
        bundle.putInt("BackStackNumber", 1);
        bundle.putString("BackStackTag", "BackStackNumber1");
        CarBrandPickerFragment carBrandPickerFragment = new CarBrandPickerFragment();
        carBrandPickerFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction()
                .addToBackStack("CarBrandPickerFragment1")
                .add(mBinding.mainContainer.getId(), carBrandPickerFragment, "BackStackNumber1")
                .commit();
    }



    public static MainContainerFragment newInstance() {
        return new MainContainerFragment() ;
    }
}
