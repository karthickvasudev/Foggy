package com.application.foggy.dashboard.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.application.foggy.R;
import com.application.foggy.dashboard.fragments.orders.create.CreateOrderActivity;
import com.application.foggy.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    private View root;
    private FragmentHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FloatingActionButton createOrder;
    private void initInstanceVariables() {
        tabLayout = root.findViewById(R.id.tab_layout);
        viewPager2 = root.findViewById(R.id.view_pager);
        createOrder = root.findViewById(R.id.home_create_order);
    }

    private void initMethods() {
        tabLayoutViewPageAction();
        createOrderBtnAction();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        initInstanceVariables();
        initMethods();
        return root;
    }

    private void createOrderBtnAction() {
        createOrder.setOnClickListener(view -> {
            root.getContext().startActivity(new Intent(root.getContext(), CreateOrderActivity.class));
        });
    }

    private void tabLayoutViewPageAction() {
        TabbedFragmentAdapter pendingOrderFragmentAdapter
                = new TabbedFragmentAdapter(getParentFragment());
        viewPager2.setAdapter(pendingOrderFragmentAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}