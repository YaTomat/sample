package ais.sample.com.returnpurchase.presentation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import ais.sample.com.MyApplication;
import ais.sample.com.R;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.DividerItemDecoration;
import ais.sample.com.common.FullscreenProgressDialogFragment;
import ais.sample.com.dagger.returnpurchase.DaggerReturnPurchaseComponent;
import ais.sample.com.dagger.returnpurchase.ReturnPurchaseComponent;
import ais.sample.com.statistic.data.StatisticItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

import static ais.sample.com.common.FragmentUtils.show;

/**
 * Created by YaTomat on 13.09.2017.
 */

public class ReturnPurchaseFragment extends BaseTitleFragment implements ReturnPurchaseView {

    public static final String ARG_PURCHASE_ITEMS = "ARG_PURCHASE_ITEMS";
    @Inject
    ReturnPurchasePresenter presenter;
    @Inject
    Map<String, BaseStatelessPresenter> statelessPresenterMap;
    @BindView(R.id.rv_purchases)
    RecyclerView rvPurchases;

    private ReturnPurchaseAdapter returnPurchaseAdapter;
    private FullscreenProgressDialogFragment dialogFragment;
    private ReturnPurchaseComponent injectionComponent;


    public static ReturnPurchaseFragment newInstance(ArrayList<StatisticItem> items) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PURCHASE_ITEMS, items);
        ReturnPurchaseFragment fragment = new ReturnPurchaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjectionComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_return_purchase, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onCreated(this, savedInstanceState);
        ArrayList<StatisticItem> statisticItemArrayList = getArguments().getParcelableArrayList(ARG_PURCHASE_ITEMS);
        presenter.setPurchaseList(statisticItemArrayList);
        returnPurchaseAdapter = new ReturnPurchaseAdapter(presenter);
        rvPurchases.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPurchases.setItemAnimator(new FadeInLeftAnimator());
        rvPurchases.addItemDecoration(new DividerItemDecoration(getContext()));
        returnPurchaseAdapter.setMode(Attributes.Mode.Single);
        rvPurchases.setAdapter(returnPurchaseAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Nullable
    @Override
    protected CharSequence provideTitle() {
        return getString(R.string.return_item_title);
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        returnPurchaseAdapter.setOnReturnClickListener(null);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstance();
        statelessPresenterMap.put(ReturnPurchasePresenter.class.getSimpleName(), presenter);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroyed();
        super.onDestroy();
    }

    @Override
    public void removePresenter() {
        statelessPresenterMap.remove(FindPurchasePresenter.class.getSimpleName());
    }

    @Override
    public void handleError() {

    }

    @Override
    public void handleError(@StringRes int errorMessage) {
        handleError(getString(errorMessage));
    }

    @Override
    public void handleError(String errorMessage) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(getString(R.string.login_error_title))
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                })
                .show();
    }

    @Override
    public void showProgress() {
        dialogFragment = FullscreenProgressDialogFragment.newInstance();
        show(getActivity().getSupportFragmentManager(), dialogFragment);
    }

    @Override
    public void showProgress(@StringRes int message) {

    }

    @Override
    public void stopProgress() {
        if (dialogFragment != null && !getActivity().getSupportFragmentManager().isDestroyed()) {
            dialogFragment.dismissAllowingStateLoss();
        }
    }

    private ReturnPurchaseComponent setUpInjectionComponent() {
        if (injectionComponent == null) {
            injectionComponent = DaggerReturnPurchaseComponent.builder()
                    .applicationComponent(MyApplication.getComponent())
                    .build();
        }
        return injectionComponent;
    }

    @Override
    public void updateView(ArrayList<StatisticItem> returnPurchaseResponse) {
        if (returnPurchaseResponse.size() == 0) {
            getActivity().onBackPressed();
        } else {
            if (returnPurchaseAdapter != null) {
                returnPurchaseAdapter.setStatisticItems(returnPurchaseResponse);
                returnPurchaseAdapter.notifyDataSetChanged();
            }
        }
    }
}
