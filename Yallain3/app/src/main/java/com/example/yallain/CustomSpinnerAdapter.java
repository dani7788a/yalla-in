package com.example.yallain;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mSportsList;
    private List<Drawable> mSportIcons;

    public CustomSpinnerAdapter(Context context, List<String> sportsList, List<Drawable> sportIcons) {
        super(context, 0, sportsList);
        mContext = context;
        mSportsList = sportsList;
        mSportIcons = sportIcons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_item, parent, false);
        }

        TextView textViewSport = convertView.findViewById(R.id.textViewSport);
        ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);

        String sport = mSportsList.get(position);
        Drawable icon = mSportIcons.get(position);

        textViewSport.setText(sport);
        imageViewIcon.setImageDrawable(icon);

        return convertView;
    }
}
