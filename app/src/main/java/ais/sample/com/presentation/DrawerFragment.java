package ais.sample.com.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import ais.sample.com.R;
import ais.sample.com.purchase.presentation.DiscountPurchaseFragment;
import ais.sample.com.purchase.presentation.FinishPurchaseFragment;

/**
 * Created by YaTomat on 22.06.2017.
 */

public class DrawerFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationView navigationView = view.findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getActivity());
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                getActivity().findViewById(R.id.drawer_layout),
                getActivity().findViewById(R.id.my_toolbar),
                R.string.openDrawer,
                R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                View view = getActivity().getWindow().getCurrentFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onBackStackChanged() {
        if (actionBarDrawerToggle != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            if (appCompatActivity != null) {
                Fragment fragment = appCompatActivity.getSupportFragmentManager().findFragmentById(R.id.main_container);
                boolean discountPurchase = fragment instanceof DiscountPurchaseFragment;
                boolean finishDiscountPurchase = fragment instanceof FinishPurchaseFragment;
                actionBarDrawerToggle.setDrawerIndicatorEnabled(appCompatActivity.getSupportFragmentManager().getBackStackEntryCount() <= 2 && !discountPurchase && !finishDiscountPurchase);
                if (appCompatActivity.getSupportFragmentManager().getBackStackEntryCount() > 2 ||
                        discountPurchase || finishDiscountPurchase) {
                    actionBarDrawerToggle.setToolbarNavigationClickListener((navigationView -> {
                        appCompatActivity.onBackPressed();
                    }));
                }
            }
            actionBarDrawerToggle.syncState();

        }
    }
}

