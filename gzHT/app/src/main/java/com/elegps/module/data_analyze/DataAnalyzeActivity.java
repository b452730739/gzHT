package com.elegps.module.data_analyze;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.elegps.gz_customerservice.R;
import com.elegps.javabean.DataAnalyzeWraper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataAnalyzeActivity extends Activity implements DataAnalyzeContract.View{

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_PrevMonthProduction)
    TextView tvPrevMonthProduction;
    @Bind(R.id.tv_PrevMonthStock)
    TextView tvPrevMonthStock;
    @Bind(R.id.tv_PrevMonthPerHour)
    TextView tvPrevMonthPerHour;
    @Bind(R.id.tv_NextMonthProduction)
    TextView tvNextMonthProduction;
    @Bind(R.id.tv_NextMonthStock)
    TextView tvNextMonthStock;
    @Bind(R.id.tv_NextMonthPerHour)
    TextView tvNextMonthPerHour;

    private DataAnalyzePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_analyze_info);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        presenter = new DataAnalyzePresenter(this);
        presenter.AppDataAnalyze();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void Fail(String message) {

    }

    @Override
    public void Succeeded(ArrayList<DataAnalyzeWraper> arrayList) {


        tvPrevMonthProduction.setText(arrayList.get(0).getPrevMonthPerHour());
        tvPrevMonthStock.setText(arrayList.get(0).getPrevMonthStock());
        tvPrevMonthPerHour.setText(arrayList.get(0).getPrevMonthPerHour());
        tvNextMonthProduction.setText(arrayList.get(0).getNextMonthProduction());
        tvNextMonthStock.setText(arrayList.get(0).getNextMonthStock());
        tvNextMonthPerHour.setText(arrayList.get(0).getNextMonthPerHour());
    }

    @Override
    public void setPresenter(DataAnalyzeContract.Presenter presenter) {

    }
}
