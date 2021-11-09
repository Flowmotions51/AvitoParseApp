package com.example.mycompany.avitoparseapp.presentation.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mycompany.avitoparseapp.R;
import com.example.mycompany.avitoparseapp.databinding.ActivityMainBinding;
import com.example.mycompany.avitoparseapp.presentation.viewmodel.AvitoParseViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private AvitoParseViewModel avitoParseViewModel;
    private ViewPager2 viewPager2;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        viewPager2 = mBinding.viewPager;
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(myPagerAdapter);

        TabLayoutMediator tabLayoutMediator =
                new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: tab.setText("Поиск"); break;
                            case 1: tab.setText("Избранное"); break;
                        }
                    }
                });
        tabLayoutMediator.attach();
    }


    private class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return MainContainerFragment.newInstance();
                case 1:
                    return FavoritesFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentViewpagerFragment = getSupportFragmentManager()
                .findFragmentByTag("f" + viewPager2.getCurrentItem());
        FragmentManager childFragmentManager = currentViewpagerFragment.getChildFragmentManager();
        if (currentViewpagerFragment instanceof MainContainerFragment) {
            if (childFragmentManager.getFragments().size() == 0) return;
            Fragment topFragment = childFragmentManager.getFragments().get(childFragmentManager.getFragments().size() - 1);
            if(!(topFragment instanceof CarBrandPickerFragment)) {
                childFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
                        .hide(topFragment)
                        .commitNow();
                childFragmentManager.beginTransaction()
                        .remove(topFragment)
                        .commit();
            }
        } else if (currentViewpagerFragment instanceof FavoritesFragment) {
            if (childFragmentManager.getFragments().size() == 0) return;
            Fragment topFragment = childFragmentManager.getFragments().get(childFragmentManager.getFragments().size() - 1);
            if(!(topFragment instanceof CarBrandPickerFragment)) {
                childFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
                        .hide(topFragment)
                        .commitNow();
                childFragmentManager.beginTransaction()
                        .remove(topFragment)
                        .commit();
            }
        }
    }
}