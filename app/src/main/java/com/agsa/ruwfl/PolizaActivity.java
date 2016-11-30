package com.agsa.ruwfl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.agsa.ruwfl.adapters.PolizaListViewAdapter;
import com.agsa.ruwfl.callback.SuccessArray;
import com.agsa.ruwfl.controller.PolizaController;
import com.agsa.ruwfl.model.PolizaModel;

import java.util.ArrayList;
import java.util.List;


public class PolizaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    //OBJECTS
    private List<PolizaModel> mPolizaModelList;
    private Handler mHandler;
    private boolean mIsLoading = false;
    private int mRecordsPerCharge = 35;
    private int mLastRecord = 0;
    private PolizaListViewAdapter mAdapter = null;
    private String mAgente;
    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;

    //UI OBJECTS
    private Button mButtonAccept;
    private Button mButtonCancel;
    private CheckBox mCheckBoxSelectAll;
    private ListView mListViewPolizas;
    private TextView mTextViewAgente;
    private View mFooterView;
    private View mProgressView;
    private View mFormView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poliza);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTextViewAgente = (TextView) findViewById(R.id.text_poliza_agente);
            mTextViewAgente.setText(extras.getString("EXTRA_CODIGO_AGENTE") + " " + extras.getString("EXTRA_NOMBRE_AGENTE"));
            mAgente = extras.getString("EXTRA_CODIGO_AGENTE");
        }
        getIntent().getExtras().clear();

        mProgressView = findViewById(R.id.progress_poliza);
        mFormView = findViewById(R.id.form_poliza);

        mListViewPolizas = (ListView) findViewById(R.id.listview_poliza);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_poliza);
        swipeRefreshLayout.setOnRefreshListener(this);

        mButtonAccept = (Button) findViewById(R.id.button_poliza_accept);
        mButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick_ButtonAccept();
            }
        });

        mButtonCancel = (Button) findViewById(R.id.button_poliza_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick_Cancel();
            }
        });

        mCheckBoxSelectAll = (CheckBox) findViewById(R.id.checkbox_poliza_select);
        mCheckBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                attemptMarkElements(b);
            }
        });

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooterView = li.inflate(R.layout.item_footer_poliza, null);
        mHandler = new MyHandler();

        AlertDialog.Builder builder = new AlertDialog.Builder(PolizaActivity.this);
        builder.setMessage(R.string.dialog_message_poliza)
                .setTitle(R.string.dialog_info);
        builder.setPositiveButton(R.string.action_accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent mIntent = new Intent(getApplicationContext(), AgentSearchActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
        mDialog = builder.create();

        mListViewPolizas.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                if (view.getLastVisiblePosition() == totalItemCount && mListViewPolizas.getCount() >= mRecordsPerCharge && mIsLoading == false && mPolizaModelList.size() > mLastRecord) {
                    mIsLoading = true;
                    Thread thread = new ThreadGetMoreData();
                    //Start thread
                    thread.start();
                }
            }
        });

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshAction();
                                    }
                                }
        );
    }

    //Actions
    @Override
    public void onRefresh() {
        refreshAction();
    }

    private void refreshAction() {
        swipeRefreshLayout.setRefreshing(true);
        PolizaController.buscarPolizasPendientes(mAgente, getApplicationContext(), new SuccessArray() {
            @Override
            public void onSuccess(List objectList) {
                mPolizaModelList = objectList;
                mAdapter = new PolizaListViewAdapter(mListViewPolizas.getContext(), getMoreData());
                mListViewPolizas.setAdapter(mAdapter);
                /*mListViewPolizas.notify();*/
                mLastRecord = 0;
                if (mPolizaModelList.size() == 0) {
                    swipeRefreshLayout.setRefreshing(false);
                    mDialog.show();
                } else swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Object object) {
                swipeRefreshLayout.setRefreshing(false);
                mListViewPolizas.setAdapter(null);
                Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void attemptMarkElements(boolean isMarked) {
        for (int i = 0; i < mPolizaModelList.size(); i++) {
            mPolizaModelList.get(i).setMarked(isMarked);
        }
        mAdapter = new PolizaListViewAdapter(mListViewPolizas.getContext(), mPolizaModelList);
        mListViewPolizas.setAdapter(mAdapter);
        mLastRecord = mPolizaModelList.size() - 1;
    }

    private void onClick_ButtonAccept() {
        ArrayList<PolizaModel> lstData = new ArrayList<PolizaModel>(mAdapter.getmPolizaModelListMarked());
        if (lstData.size() > 0) {
            Intent mIntent = new Intent(getApplicationContext(), ReceiveActivity.class);
            mIntent.putExtra("EXTRA_NOMBRE_AGENTE", mTextViewAgente.getText().toString());
            mIntent.putExtra("EXTRA_LIST_AGENT", lstData);
            startActivity(mIntent);
            this.finish();
        } else
            Toast.makeText(getApplicationContext(), getString(R.string.error_elements_required), Toast.LENGTH_SHORT).show();

    }

    private void onClick_Cancel() {
        Intent mIntent = new Intent(getApplicationContext(), AgentSearchActivity.class);
        startActivity(mIntent);
        finish();
    }

    //Methods

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //Aaa loading view during search processing
                    mListViewPolizas.addFooterView(mFooterView);
                    break;
                case 1:
                    //Update data adapter and UI
                    mAdapter.addListItemToAdapter((ArrayList<PolizaModel>) msg.obj);
                    //Remove loading view after update listview
                    mListViewPolizas.removeFooterView(mFooterView);
                    mIsLoading = false;
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<PolizaModel> getMoreData() {
        ArrayList<PolizaModel> list = new ArrayList<>();
        if ((mPolizaModelList.size()) >= (mLastRecord + mRecordsPerCharge)) {
            list = new ArrayList<PolizaModel>(mPolizaModelList.subList(mLastRecord, mLastRecord + mRecordsPerCharge));
            mLastRecord += mRecordsPerCharge;
        } else {
            list = new ArrayList<PolizaModel>(mPolizaModelList.subList(mLastRecord, mPolizaModelList.size()));
            mLastRecord = mPolizaModelList.size();
        }
        //Sample Code get new data

        return list;
    }

    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            //Add footer to view after get data
            mHandler.sendEmptyMessage(0);
            //Search more data
            ArrayList<PolizaModel> lstResult = getMoreData();
            //Delay time to show loading footer when debug, remove it when release
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Send the result to Handle
            Message msg = mHandler.obtainMessage(1, lstResult);
            mHandler.sendMessage(msg);
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
