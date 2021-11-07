package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.data.parser.Parser;
import com.example.mycompany.avitoparseapp.data.repository.ParserRepository;
import com.example.mycompany.avitoparseapp.databinding.ActivityMainBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.CarCellsAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Parser parser = new Parser();
        ParserRepository parserRepository = new ParserRepository(parser);
        avitoParseViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AvitoParseViewModel(parserRepository);
            }
        }).get(AvitoParseViewModel.class);
        if(getSupportFragmentManager().getBackStackEntryCount() != 0) {

        }
        getSupportFragmentManager().beginTransaction()
                .add(mBinding.mainContainer.getId(), new CarBrandPickerFragment(), "CarModelPickerFragment")
                .commit();
    }
}