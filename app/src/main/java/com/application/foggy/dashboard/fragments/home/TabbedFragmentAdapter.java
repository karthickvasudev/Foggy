package com.application.foggy.dashboard.fragments.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.application.foggy.dashboard.fragments.home.newordertab.NewOrderFragment;
import com.application.foggy.dashboard.fragments.home.pendingordertab.PendingOrderFragment;

public class TabbedFragmentAdapter extends FragmentStateAdapter {


    public TabbedFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            default:
                return new PendingOrderFragment();
            case 1:
                return new NewOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
