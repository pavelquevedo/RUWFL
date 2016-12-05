package com.agsa.ruwfl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.agsa.ruwfl.R;
import com.agsa.ruwfl.model.AgenteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evillatoro on 15/11/2016.
 */
public class AgentSearchListViewAdapters extends BaseAdapter {
    List<AgenteModel> mAgenteModelList = new ArrayList<>();
    Context mContext;

    public AgentSearchListViewAdapters(Context context, List<AgenteModel> agenteModelList) {
        super();
        this.mContext = context;
        this.mAgenteModelList = agenteModelList;
    }

    @Override
    public int getCount() {
        return mAgenteModelList.size();
    }

    @Override
    public AgenteModel getItem(int i) {
        return mAgenteModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, android.view.ViewGroup parent) {
        ViewHolderAgentSearch mViewHolderAgentSearch;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_agent_search, parent, false);
            mViewHolderAgentSearch = new ViewHolderAgentSearch(convertView);
            convertView.setTag(mViewHolderAgentSearch);

        } else mViewHolderAgentSearch = (ViewHolderAgentSearch) convertView.getTag();

        AgenteModel mAgenteModel = getItem(position);
        mViewHolderAgentSearch.mTextViewAgentCode.setText(mAgenteModel.getmKey());
        mViewHolderAgentSearch.mTextViewAgentName.setText(mAgenteModel.getTxtChequeANom());
        mViewHolderAgentSearch.mTextViewAgentTickets.setText(String.valueOf(mAgenteModel.getTickets()));

        return convertView;
    }


    public static class ViewHolderAgentSearch {
        public TextView mTextViewAgentCode, mTextViewAgentName, mTextViewAgentTickets;
        public Button mButtonSelect;

        public ViewHolderAgentSearch(View view) {
            //TextView
            mTextViewAgentName = (TextView) view.findViewById(R.id.text_item_agent_search_name);
            mTextViewAgentCode = (TextView) view.findViewById(R.id.text_item_agent_search_code);
            mTextViewAgentTickets = (TextView)view.findViewById(R.id.text_item_agent_search_ticket);
            //Button
            mButtonSelect = (Button) view.findViewById(R.id.button_item_agent_search_select);
        }
    }

}
