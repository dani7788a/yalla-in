package com.example.yallain;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TextAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int layoutResourceId;
    private List<String> data;

    public TextAdapter(Context context, int layoutResourceId, List<String> items) {
        super(context, layoutResourceId, items);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.data = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }


        TextView textViewItem = convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(data.get(position));

        return convertView;
    }
}

