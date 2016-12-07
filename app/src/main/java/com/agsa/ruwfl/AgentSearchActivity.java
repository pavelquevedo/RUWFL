package com.agsa.ruwfl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.agsa.ruwfl.adapters.AgentSearchListViewAdapters;
import com.agsa.ruwfl.callback.SuccessArray;
import com.agsa.ruwfl.controller.AgenteController;
import com.agsa.ruwfl.fragment.SettingsDialog;
import com.agsa.ruwfl.model.AgenteModel;
import com.agsa.ruwfl.seguridad.Token;

import java.net.URL;
import java.util.List;

public class AgentSearchActivity extends AppCompatActivity implements SettingsDialog.OnSaveSettings {
    private List<AgenteModel> mAgenteModelList;
    private View mProgressView;
    private View mSearchAgentFormView;
    private ListView mListViewAgentes;
    private Button mButtonConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_search);

        Button mSearchButton = (Button) findViewById(R.id.button_agent_search_search);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAgent();
            }
        });

        if (Token.getEsAdmin(getApplicationContext())) {
            mButtonConfig = (Button) findViewById(R.id.button_agent_search_conf);
            mButtonConfig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialogSetting();
                }
            });
            mButtonConfig.setVisibility(View.VISIBLE);
        }

        mListViewAgentes = (ListView) findViewById(R.id.listview_agent_search);

        mSearchAgentFormView = findViewById(R.id.form_search_agent);
        mProgressView = findViewById(R.id.progress_search_agent);
    }

    public void openDialogSetting(){
        SettingsDialog dialog = SettingsDialog.newInstance(Token.getURL(getApplicationContext()));
        dialog.show(getFragmentManager(),"");

    }

    public void searchAgent() {
        EditText mEditTextPattern = (EditText) findViewById(R.id.edittext_agent_search_pattern);
        mEditTextPattern.setError(null);

        String mPattern = mEditTextPattern.getText().toString().trim();
        boolean mCancel = false;
        View mFocusView = null;

        if (TextUtils.isEmpty(mPattern)) {
            mEditTextPattern.setError(getString(R.string.error_field_required));
            mFocusView = mEditTextPattern;
            mCancel = true;
        }

        if (mCancel) {
            mFocusView.requestFocus();
        } else {
            showProgress(true);
            AgenteController.buscarAgente(mPattern, getApplicationContext(), new SuccessArray() {
                @Override
                public void onSuccess(List objectList) {
                    mAgenteModelList = objectList;
                    if (mAgenteModelList.size() > 1) {
                        AgentSearchListViewAdapters adapter = new AgentSearchListViewAdapters(mListViewAgentes.getContext(), mAgenteModelList);
                        mListViewAgentes.setAdapter(adapter);
                        showProgress(false);
                    } else {
                        mListViewAgentes.setAdapter(null);
                        Intent mIntent = new Intent(getApplicationContext(), PolizaActivity.class);
                        mIntent.putExtra("EXTRA_CODIGO_AGENTE", mAgenteModelList.get(0).getmKey());
                        mIntent.putExtra("EXTRA_NOMBRE_AGENTE", mAgenteModelList.get(0).getTxtChequeANom());
                        startActivity(mIntent);
                        showProgress(false);
                        //Toast.makeText(getApplicationContext(), "SOLO HAY UN AGENTE", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Object object) {
                    showProgress(false);
                    mListViewAgentes.setAdapter(null);
                    Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSearchAgentFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSearchAgentFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSearchAgentFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSearchAgentFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void buttonSelect_OnClick(View v) {
        AgentSearchListViewAdapters.ViewHolderAgentSearch itemHolderAgentSearch = (AgentSearchListViewAdapters.ViewHolderAgentSearch) ((View) v.getParent()).getTag();
        Intent mIntent = new Intent(getApplicationContext(), PolizaActivity.class);
        mIntent.putExtra("EXTRA_CODIGO_AGENTE", itemHolderAgentSearch.mTextViewAgentCode.getText().toString());
        mIntent.putExtra("EXTRA_NOMBRE_AGENTE", itemHolderAgentSearch.mTextViewAgentName.getText().toString());
        startActivity(mIntent);
    }

    @Override
    public void onSave(String url) {
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
    }
}
