package com.agsa.ruwfl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.agsa.ruwfl.R;
import com.agsa.ruwfl.model.PolizaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evillatoro on 16/11/2016.
 */

public class PolizaListViewAdapter extends BaseAdapter {
    List<PolizaModel> mPolizaModelList = new ArrayList<>();
    List<PolizaModel> mPolizaModelListMarked = new ArrayList<>();
    Context mContext;

    public List<PolizaModel> getmPolizaModelListMarked() {
        return mPolizaModelListMarked;
    }

    public PolizaListViewAdapter(Context context, List<PolizaModel> polizaModelList) {
        super();
        this.mContext = context;
        this.mPolizaModelList = polizaModelList;
        this.notifyDataSetChanged();
    }

    public void addListItemToAdapter(List<PolizaModel> list) {
        //Add list to current array list of data
        mPolizaModelList.addAll(list);
        //Notify UI
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPolizaModelList.size();
    }

    @Override
    public PolizaModel getItem(int i) {
        return mPolizaModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, android.view.ViewGroup parent) {
        ViewHolderPoliza mViewHolderAgentSearch;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_poliza, parent, false);
            mViewHolderAgentSearch = new ViewHolderPoliza(convertView, position);
            mViewHolderAgentSearch.mCheckBoxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    int mPostion = (Integer) compoundButton.getTag();
                    getItem(mPostion).setMarked(isChecked);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(mViewHolderAgentSearch);

        } else {
            mViewHolderAgentSearch = (ViewHolderPoliza) convertView.getTag();
        }

        mViewHolderAgentSearch.mTextViewCaso.setText(String.valueOf(getItem(position).getIdCaso()));
        mViewHolderAgentSearch.mTextViewSucursal.setText(getItem(position).getTxtNomSuc());
        mViewHolderAgentSearch.mTextViewRamo.setText(getItem(position).getTxtRamo());
        mViewHolderAgentSearch.mTextViewPoliza.setText(String.valueOf(getItem(position).getNroPol()));
        mViewHolderAgentSearch.mTextViewEndoso.setText(String.valueOf(getItem(position).getTxtEndoso()));
        mViewHolderAgentSearch.mTextViewPagador.setText(getItem(position).getTxtAsegurado());

        mViewHolderAgentSearch.mCheckBoxSelect.setTag(position);
        mViewHolderAgentSearch.mCheckBoxSelect.setChecked(getItem(position).isMarked());

        if (getItem(position).isMarked() && mPolizaModelListMarked.indexOf(mPolizaModelList.get(position)) < 0) {
            mPolizaModelListMarked.add(mPolizaModelList.get(position));
        } else if (mPolizaModelListMarked.indexOf(mPolizaModelList.get(position)) > -1 && !getItem(position).isMarked()) {
            mPolizaModelListMarked.remove(mPolizaModelListMarked.indexOf(mPolizaModelList.get(position)));
        }

        return convertView;
    }

    public static class ViewHolderPoliza {
        TextView mTextViewSucursal, mTextViewRamo, mTextViewPoliza, mTextViewEndoso, mTextViewPagador, mTextViewCaso;
        CheckBox mCheckBoxSelect;


        public ViewHolderPoliza(View view, int position) {
            //TextView
            mTextViewRamo = (TextView) view.findViewById(R.id.text_item_poliza_ramo);
            mTextViewSucursal = (TextView) view.findViewById(R.id.text_item_poliza_sucursal);
            mTextViewPoliza = (TextView) view.findViewById(R.id.text_item_poliza_poliza);
            mTextViewEndoso = (TextView) view.findViewById(R.id.text_item_poliza_endoso);
            mTextViewPagador = (TextView) view.findViewById(R.id.text_item_poliza_pagador);
            //Button
            mCheckBoxSelect = (CheckBox) view.findViewById(R.id.checkbox_item_poliza_select);
            mTextViewCaso = (TextView) view.findViewById(R.id.text_item_poliza_caso);

        }
    }
}
