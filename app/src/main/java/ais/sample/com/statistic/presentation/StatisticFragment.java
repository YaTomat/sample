package ais.sample.com.statistic.presentation;


import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.Switch;

import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import ais.sample.com.MyApplication;
import ais.sample.com.R;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.DividerItemDecoration;
import ais.sample.com.common.FullscreenProgressDialogFragment;
import ais.sample.com.dagger.statistic.DaggerStatisticComponent;
import ais.sample.com.dagger.statistic.StatisticComponent;
import ais.sample.com.statistic.data.StatisticsResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static ais.sample.com.common.FragmentUtils.show;

/**
 * Created by YaTomat on 30.08.2017.
 */

public class StatisticFragment extends BaseTitleFragment implements StatisticView {

    @Inject
    Map<String, BaseStatelessPresenter> statelessPresenterMap;
    @Inject
    StatisticPresenter presenter;

    @BindView(R.id.et_begin_date)
    EditText etStartDate;
    @BindView(R.id.et_end_date)
    EditText etEndDate;
    @BindView(R.id.switch_type_user)
    Switch switchType;
    @BindView(R.id.rv_statistic)
    RecyclerView rvStatistic;
    private FullscreenProgressDialogFragment dialogFragment;
    private StatisticComponent injectionComponent;
    private StatisticAdapter statisticAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjectionComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onCreated(this, savedInstanceState);
        etStartDate.setFocusable(false);
        etEndDate.setFocusable(false);
        Calendar calendarInstance = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener startDate = (view1, year, monthOfYear, dayOfMonth) -> {
            calendarInstance.set(Calendar.YEAR, year);
            calendarInstance.set(Calendar.MONTH, monthOfYear);
            calendarInstance.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate(calendarInstance.getTime(), etStartDate);
        };
        DatePickerDialog.OnDateSetListener endDate = (view1, year, monthOfYear, dayOfMonth) -> {
            calendarInstance.set(Calendar.YEAR, year);
            calendarInstance.set(Calendar.MONTH, monthOfYear);
            calendarInstance.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate(calendarInstance.getTime(), etEndDate);
        };

        etStartDate.setOnClickListener(v -> new DatePickerDialog(getContext(), startDate, calendarInstance
                .get(Calendar.YEAR), calendarInstance.get(Calendar.MONTH),
                calendarInstance.get(Calendar.DAY_OF_MONTH)).show());
        etEndDate.setOnClickListener(v -> new DatePickerDialog(getContext(), endDate, calendarInstance
                .get(Calendar.YEAR), calendarInstance.get(Calendar.MONTH),
                calendarInstance.get(Calendar.DAY_OF_MONTH)).show());
        statisticAdapter = new StatisticAdapter();
        rvStatistic.setAdapter(statisticAdapter);
        rvStatistic.addItemDecoration(new DividerItemDecoration(getContext()));
        rvStatistic.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Nullable
    @Override
    protected CharSequence provideTitle() {
        return getString(R.string.statistic_title);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstance();
        statelessPresenterMap.put(StatisticPresenter.class.getSimpleName(), presenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroyed();
    }

    private void updateDate(Date date, EditText editText) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        editText.setText(simpleDateFormat.format(date));
    }

    @Override
    public void removePresenter() {
        statelessPresenterMap.remove(StatisticPresenter.class.getSimpleName());
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

    @Override
    public Observable<String> onStartDateChanged() {
        return RxTextView.textChanges(etStartDate).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<String> onEndDateChanged() {
        return RxTextView.textChanges(etEndDate).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<Boolean> onTypeReportChanged() {
        return RxCompoundButton.checkedChanges(switchType);
    }

    @Override
    public void updateView(StatisticsResponse statisticsResponse) {
        statisticAdapter.setStatisticItems(statisticsResponse.statisticItems);
        statisticAdapter.notifyDataSetChanged();
    }

    private StatisticComponent setUpInjectionComponent() {
        if (injectionComponent == null) {
            injectionComponent = DaggerStatisticComponent.builder()
                    .applicationComponent(MyApplication.getComponent())
                    .build();
        }
        return injectionComponent;
    }
}
