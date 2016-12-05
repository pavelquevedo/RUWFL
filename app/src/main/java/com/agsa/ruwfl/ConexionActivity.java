package com.agsa.ruwfl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.agsa.ruwfl.adapters.ConexionListViewAdapter;
import com.agsa.ruwfl.callback.SuccessArray;
import com.agsa.ruwfl.callback.SuccessObject;
import com.agsa.ruwfl.controller.AuthController;
import com.agsa.ruwfl.model.ClsConexiones;

import java.util.List;

public class ConexionActivity extends AppCompatActivity {
    private List<ClsConexiones> mConexions;
    private View mProgressView;
    private View mFormView;
    private ListView mListViewConexiones;
    private ConexionListViewAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion);

        mProgressView = findViewById(R.id.progress_conexion);
        mFormView = findViewById(R.id.form_conexion);

        mListViewConexiones = (ListView) findViewById(R.id.listview_conexion);

        getConnections();
    }

    protected void getConnections() {
        showProgress(true);
        AuthController.obtenerConexions(getApplicationContext(), new SuccessArray() {
            @Override
            public void onSuccess(List objectList) {
                mConexions = objectList;
                if (mConexions.size() == 1 && mConexions.get(0).getIntCodConexion() == 101) {
                    obtenerToken(mConexions.get(0));
                    //Toast.makeText(ConexionActivity.this, "Eres supremo", Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter = new ConexionListViewAdapter(mConexions, mListViewConexiones.getContext());
                    mAdapter.setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClsConexiones mData = mAdapter.getItem(Integer.valueOf(view.getTag().toString()));
                            obtenerToken(mData);
                            //Toast.makeText(view.getContext(), "Gello", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mListViewConexiones.setAdapter(mAdapter);
                }
                showProgress(false);
            }

            @Override
            public void onError(Object object) {
                showProgress(false);
                mListViewConexiones.setAdapter(null);
                Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void obtenerToken(ClsConexiones mData) {
        showProgress(true);
        AuthController.ObtenerToken(String.valueOf(mData.getIntCodConexion()), getApplicationContext(), new SuccessObject() {
            @Override
            public void onSuccess(Object object) {
                showProgress(false);
                Intent myIntent = new Intent(getBaseContext(), AgentSearchActivity.class);
                startActivity(myIntent);
                finish();
            }

            @Override
            public void onError(Object object) {
                showProgress(false);
                if (object != null)
                    Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    ///Show Progress Bar
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
