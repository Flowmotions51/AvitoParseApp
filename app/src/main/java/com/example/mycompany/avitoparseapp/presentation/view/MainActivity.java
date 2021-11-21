package com.example.mycompany.avitoparseapp.presentation.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mycompany.avitoparseapp.BaseApplication;
import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.databinding.ActivityMainBinding;
import com.example.mycompany.avitoparseapp.presentation.view.adapter.ViewPagerAdapter;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AvitoParseViewModel avitoParseViewModel;

    private ActivityMainBinding mBinding;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ((BaseApplication)getApplication())
                .getAppComponent()
                .inject(this);

        avitoParseViewModel
                = new ViewModelProvider(this, viewModelFactory).get(AvitoParseViewModel.class);
//
//        avitoParseViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                return (T) new AvitoParseViewModel(parserRepository);
//            }
//        }).get(AvitoParseViewModel.class);


//        Parser parser = new Parser();
//        ParserRepository parserRepository = new ParserRepository(parser);
//        avitoParseViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                return (T) new AvitoParseViewModel(parserRepository);
//            }
//        }).get(AvitoParseViewModel.class);
        viewPager2 = mBinding.viewPager;
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(viewPagerAdapter);

        TabLayoutMediator tabLayoutMediator =
                new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Поиск");
                                break;
                            case 1:
                                tab.setText("Избранное");
                                break;
                        }
                    }
                });
        tabLayoutMediator.attach();


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CarCellsFavorites", "");
        if(!json.equals("")) {
            List<LinkedTreeMap> linkedTreeMaps = gson.fromJson(json, ArrayList.class);
            List<CarCell> carCells = new ArrayList<>();
            for(LinkedTreeMap map : linkedTreeMaps) {
                String previewImageUrl = (String) map.get("previewImageUrl");
                String firstImgUrl = (String) map.get("firstImgUrl");
                String linkToItem = (String) map.get("linkToItem");
                String carName = (String) map.get("carName");
                carCells.add(new CarCell(previewImageUrl, firstImgUrl, linkToItem, carName));
            }
            avitoParseViewModel.setCarCellsFavorites(carCells);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentViewpagerFragment = getSupportFragmentManager()
                .findFragmentByTag("f" + viewPager2.getCurrentItem());
        FragmentManager childFragmentManager = currentViewpagerFragment.getChildFragmentManager();
        if (childFragmentManager.getFragments().size() == 1) {
            //super.onBackPressed();
            return;
        }
        Fragment topFragment = childFragmentManager.getFragments().get(childFragmentManager.getFragments().size() - 1);
        childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
                .hide(topFragment)
                .commitNow();
        childFragmentManager.beginTransaction()
                .remove(topFragment)
                .commit();
    }

    @Override
    protected void onStop() {
        List<CarCell> carCellsFavorites = avitoParseViewModel.getCarCellsFavorites();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(carCellsFavorites);
        editor.putString("CarCellsFavorites", json);
        editor.commit();
        super.onStop();
    }

}