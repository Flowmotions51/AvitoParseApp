package com.example.mycompany.avitoparseapp.di.components;

import com.example.mycompany.avitoparseapp.di.modules.ContextModule;
import com.example.mycompany.avitoparseapp.di.modules.NetworkModule;
import com.example.mycompany.avitoparseapp.presentation.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
