package com.agsa.ruwfl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agsa.ruwfl.callback.SuccessObject;
import com.agsa.ruwfl.controller.RecieveController;
import com.agsa.ruwfl.model.FirmaModel;
import com.agsa.ruwfl.model.PolizaModel;
import com.agsa.ruwfl.seguridad.Token;

import java.util.List;
import java.util.UUID;

public class ReceiveActivity extends AppCompatActivity {

    /*
        Visual elements
    */
    private Button mButtonAccept;
    private Button mButtonNew;
    private TextView mTextViewAgente;
    private DrawingView mDrawnView;

    private View mProgressView;
    private View mFormView;

    /*
        Objects
     */
    private List<PolizaModel> mPolizaModelList;

    /*Request permissión for write files
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        mProgressView = findViewById(R.id.progress_recieve);
        mFormView = findViewById(R.id.form_recieve);

        mButtonAccept = (Button) findViewById(R.id.button_recieve_accept);
        mButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                saveDraw();
            }
        });

        mButtonNew = (Button) findViewById(R.id.button_recieve_new);
        mButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewDraw();
            }
        });

        mDrawnView = (DrawingView) findViewById(R.id.drawing);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTextViewAgente = (TextView) findViewById(R.id.text_recieve_agente);
            mTextViewAgente.setText(extras.getString("EXTRA_NOMBRE_AGENTE"));
            mPolizaModelList = (List<PolizaModel>) extras.getSerializable("EXTRA_LIST_AGENT");
        }
    }

    //Draw Options
    private void startNewDraw() {
        mDrawnView.startNew();
    }

    private void saveDraw() {
        if (mDrawnView.ismDrawed()) {
            mDrawnView.setDrawingCacheEnabled(true);
            savePicture(mDrawnView.getDrawingCache(), UUID.randomUUID().toString() + ".jpg");
            mDrawnView.destroyDrawingCache();
        } else {
            Toast.makeText(getApplicationContext(), "No se ha colocado la firma para procesar la petición", Toast.LENGTH_SHORT).show();
            showProgress(false);
        }
    }

    //Customs Methods
    private void savePicture(Bitmap bm, String imgName) {
        RecieveController.ProcesarFirma(
                new FirmaModel(
                        RecieveController.getStringImage(bm),
                        imgName,
                        Token.getToken(getApplicationContext())
                ),
                mPolizaModelList,
                getApplicationContext(),
                new SuccessObject() {
                    @Override
                    public void onSuccess(Object object) {
                        Intent mIntent = new Intent(getApplicationContext(), AgentSearchActivity.class);
                        startActivity(mIntent);
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

    //Request Permisions

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    /*public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/
}
