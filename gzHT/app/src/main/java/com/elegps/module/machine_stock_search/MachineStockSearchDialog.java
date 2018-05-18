package com.elegps.module.machine_stock_search;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.elegps.UIManager.Dialog_UI;
import com.elegps.adapter.SpinnerApadterByArray;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.MachineStockInfo;
import com.elegps.javabean.TaskInfoList;
import com.view.DateEditTextForData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MachineStockSearchDialog extends Dialog implements MachineStockSearchContract.View{


    @Bind(R.id.et_strMachineNO)
    EditText etStrMachineNO;
    @Bind(R.id.et_strMachineModel)
    EditText etStrMachineModel;
    @Bind(R.id.startData)
    DateEditTextForData startData;
    @Bind(R.id.endData)
    DateEditTextForData endData;

    @Bind(R.id.search)
    Button search;

    private Context mContext;
    private GetMachineStockInfoList infoList;
    private MachineStockSearchPresenter presenter = null;
    private Dialog_UI loading ;

    public MachineStockSearchDialog(Context context, GetMachineStockInfoList infoList) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        this.infoList = infoList;
        setContentView(R.layout.dialog_machine_stock_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        loading = new Dialog_UI(mContext,"正在查询...");
        presenter = new MachineStockSearchPresenter(this);

    }

    public void searchTask(){
        this.dismiss();
        loading.show();
        presenter.searchMachineStock(etStrMachineNO.getText().toString(),etStrMachineModel.getText().toString(),startData.getText().toString(),endData.getText().toString());

    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        searchTask();
    }

    @Override
    public void Fail(String message) {
        loading.dismiss();
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Succeeded(ArrayList<MachineStockInfo> arrayList) {
        loading.dismiss();
        infoList.machineStockList(arrayList);
    }

    @Override
    public void setPresenter(MachineStockSearchContract.Presenter presenter) {

    }

    interface GetMachineStockInfoList{
        public void machineStockList(ArrayList<MachineStockInfo> arrayList);
    }
}
