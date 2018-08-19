package ais.sample.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import ais.sample.com.cardinfo.presentation.CardInfoFragment;
import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.presentation.DrawerFragment;
import ais.sample.com.purchase.presentation.PurchaseFragment;
import ais.sample.com.returnpurchase.presentation.FindPurchaseFragment;
import ais.sample.com.statistic.presentation.StatisticFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BaseTitleFragment.TitleSetter {

    public static final String ARG_ACCESS_CONTROLLER = "ARG_ACCESS_CONTROLLER";
    @Inject
    AccessController accessController;
    private DrawerLayout drawerLayout;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);
        if (accessController.isEmpty() && savedInstanceState == null) {
            invokeLogin();
        } else {
            setContentView(R.layout.activity_main);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);
            initDrawer();
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_container, new PurchaseFragment(), PurchaseFragment.class.getSimpleName())
                        .addToBackStack(PurchaseFragment.class.getSimpleName())
                        .commit();
            } else {
                accessController.setLoginResponse(savedInstanceState.getParcelable(ARG_ACCESS_CONTROLLER));
            }
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_ACCESS_CONTROLLER, accessController.getLoginResponse());
    }

    private void initDrawer() {
        Fragment drawerFragment = getSupportFragmentManager().findFragmentById(R.id.drawer_container);
        if (drawerFragment == null) {
            drawerFragment = new DrawerFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.drawer_container, drawerFragment)
                    .commit();
        }
        getSupportFragmentManager().addOnBackStackChangedListener((DrawerFragment) drawerFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }
        getSupportFragmentManager()
                .popBackStackImmediate(PurchaseFragment.class.getSimpleName(), 0);
        switch (menuItem.getItemId()) {
            case R.id.menu_transaction: {
                return true;
            }
            case R.id.menu_receipt: {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, new StatisticFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            case R.id.menu_logout: {
                accessController.clear();
                invokeLogin();
                return true;
            }
            case R.id.menu_card_info: {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, new CardInfoFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            case R.id.menu_find_purchase: {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, new FindPurchaseFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            default:
                Toast.makeText(this, "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;

        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            } else {
                super.onBackPressed();
            }
        }

    }

    private void invokeLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setScreenTitle(@Nullable CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

}
