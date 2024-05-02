package com.example.yallain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mData;

    public CustomAdapter(Context context, List<String> data) {
        super(context, R.layout.custom_item, data);
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_item, parent, false);
        }

        TextView textViewItem = convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(mData.get(position));

        return convertView;
    }
}


