package com.agsa.ruwfl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agsa.ruwfl.R;
import com.agsa.ruwfl.model.ClsConexiones;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evillatoro on 05/12/2016.
 */

public class ConexionListViewAdapter extends BaseAdapter {
    List<ClsConexiones> mConexionList = new ArrayList<>();
    Context mContext;
    View.OnClickListener listener;

    public ConexionListViewAdapter(List<ClsConexiones> mConexionList, Context mContext) {
        super();
        this.mConexionList = mConexionList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mConexionList.size();
    }

    @Override
    public ClsConexiones getItem(int i) {
        return mConexionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderConexion mViewHolderConexion;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_conexion, parent, false);
            mViewHolderConexion = new ViewHolderConexion(convertView);
            mViewHolderConexion.btnSelect.setOnClickListener(this.listener);
            mViewHolderConexion.btnSelect.setTag(position);
            convertView.setTag(mViewHolderConexion);

        } else
            mViewHolderConexion = (ViewHolderConexion) convertView.getTag();

        ClsConexiones mConexionModel = getItem(position);
        mViewHolderConexion.txtCodigo.setText(String.valueOf(mConexionModel.getIntCodConexion()));
        mViewHolderConexion.txtNombre.setText(mConexionModel.getStrNombreConexion().toString());

        return convertView;
    }

    private class ViewHolderConexion {
        TextView txtCodigo, txtNombre;
        Button btnSelect;

        public TextView getTxtCodigo() {
            return txtCodigo;
        }

        public ViewHolderConexion(View view) {
            this.txtCodigo = (TextView) view.findViewById(R.id.text_item_conexion_cod);
            this.txtNombre = (TextView) view.findViewById(R.id.text_item_conexion_nom);
            this.btnSelect = (Button) view.findViewById(R.id.button_item_conexion_select);
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
