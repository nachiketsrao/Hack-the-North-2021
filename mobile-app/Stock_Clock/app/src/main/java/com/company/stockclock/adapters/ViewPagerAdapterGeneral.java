package com.company.stockclock.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.company.stockclock.fragments.fragmentNews;
import com.company.stockclock.fragments.fragmentReddit;
import com.company.stockclock.fragments.fragmentQuickStats;

public class ViewPagerAdapterGeneral extends FragmentStateAdapter {

    public ViewPagerAdapterGeneral(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public Fragment createFragment(int position) {

        Fragment fragment;

        switch (position)
        {
            case 0:
                fragment = fragmentQuickStats.newInstance();
                break;
            case 1:
                fragment = fragmentNews.newInstance();
                break;
            case 2:
                fragment = fragmentReddit.newInstance();
                break;
            default:
                return null;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {

        return 3;

    }
}
