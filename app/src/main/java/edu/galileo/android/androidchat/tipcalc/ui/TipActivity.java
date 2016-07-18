package edu.galileo.android.androidchat.tipcalc.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import edu.galileo.android.androidchat.R;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.addTip.AddTipInteractor;
import edu.galileo.android.androidchat.addTip.AddTipInteractorImpl;
import edu.galileo.android.androidchat.addTip.entities.Tip;
import edu.galileo.android.androidchat.fragments.TipHistoryListFragmen;
import edu.galileo.android.androidchat.fragments.TipHistoryListFragmentListener;
import edu.galileo.android.androidchat.models.TipRecord;

public class TipActivity extends AppCompatActivity {

    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;
    @Bind(R.id.txtTip)
    TextView txtTip;

    public static TipHistoryListFragmentListener fragmentListener;
    private final static int TIP_STEP_CHANGE        = 1;
    private final static int DEFAULT_TIP_PERCENTAGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        ButterKnife.bind(this);

        TipHistoryListFragmen fragment = (TipHistoryListFragmen) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
        fragment.setRetainInstance(true);
        fragmentListener = (TipHistoryListFragmentListener) fragment;

        getTipsFromFirebase();
    }

    @OnClick(R.id.btnSubmit)
    public void handelClickSubmit(){
        hideKeyboard();
        String strInputTotal    =   inputBill.getText().toString().trim();

        if(!strInputTotal.isEmpty()){
            double  total           =   Double.parseDouble(strInputTotal);
            int  tipPercentage      =  getTipPercentage();

            TipRecord tipRecord   =   new TipRecord();
            tipRecord.setBill(total);
            tipRecord.setTipPercentage(tipPercentage);
            tipRecord.setTimestamp(new Date());

            String  strTip       =  String.format(getString(R.string.global_messa_tip,tipRecord.getTip()));

            fragmentListener.addToList(tipRecord);

            Tip tip = new Tip();
            tip.setDate(tipRecord.getTimestamp());
            tip.setPercentage(tipRecord.getTipPercentage());
            tip.setTotal(tipRecord.getBill());
            tip.setTip(tipRecord.getTip());

            AddTipInteractor interactor = new AddTipInteractorImpl();
            interactor.addTip(tip);

            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);
        }
    }
    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease(){
        hideKeyboard();
        handleTipChange(TIP_STEP_CHANGE);
    }

    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease(){
        hideKeyboard();
        handleTipChange(-TIP_STEP_CHANGE);
    }

    @OnClick(R.id.btnClear)
    public void handleClickClear(){
        fragmentListener.clearList();
    }

    private void handleTipChange(int change) {
        int tipPercentage   =   getTipPercentage();
        tipPercentage      += change;

        if(tipPercentage    >   0){
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager =   (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        try{
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (NullPointerException npe){
            Log.e(getLocalClassName(),Log.getStackTraceString(npe));
        }
    }

    public int getTipPercentage() {
        int tipPercentage               =   DEFAULT_TIP_PERCENTAGE;
        String strInputTipPercentage    =   inputPercentage.getText().toString().trim();

        if(!strInputTipPercentage.isEmpty()){
            tipPercentage   =   Integer.parseInt(strInputTipPercentage);
        }else{
            inputPercentage.setText(String.valueOf(tipPercentage));
        }

        return tipPercentage;
    }

    public void getTipsFromFirebase() {

        AddTipInteractor interactor = new AddTipInteractorImpl();
        interactor.getTips();
    }
}
