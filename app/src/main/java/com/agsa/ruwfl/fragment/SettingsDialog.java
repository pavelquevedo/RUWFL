package com.agsa.ruwfl.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.agsa.ruwfl.R;
import com.agsa.ruwfl.seguridad.Token;

/**
 * Created by Evillatoro on 07/12/2016.
 */

public class SettingsDialog extends DialogFragment {
    private Context context;

    public interface OnSaveSettings {
        void onSave(String url);
    }

    private OnSaveSettings onSaveSettings;

    public static SettingsDialog newInstance(String url) {
        SettingsDialog setDialog = new SettingsDialog();

        Bundle args = new Bundle();
        args.putString("url", url);

        setDialog.setArguments(args);

        return setDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.dialog_setting_title));

        context = getActivity().getApplicationContext();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activiy_settings, null);
        EditText editTextUrl1 = (EditText) v.findViewById(R.id.edittext_url);
        editTextUrl1.setText(getArguments().getString("url"));
        builder.setView(v);

        builder.setPositiveButton(R.string.action_accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editTextUrl = (EditText) getDialog().findViewById(R.id.edittext_url);
                Token.setURL(context, editTextUrl.getText().toString());
                onSaveSettings.onSave("Su configuración ha sido guardada con éxito");
            }
        });
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.onSaveSettings = (OnSaveSettings)activity;
    }
}
