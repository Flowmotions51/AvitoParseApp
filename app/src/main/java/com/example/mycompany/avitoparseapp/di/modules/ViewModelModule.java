package com.example.mycompany.avitoparseapp.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycompany.avitoparseapp.di.ViewModelKey;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AvitoParseViewModel.class)
    abstract ViewModel bindViewModel(AvitoParseViewModel avitoParseViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindFactory(ViewModelFactory factory);
}
