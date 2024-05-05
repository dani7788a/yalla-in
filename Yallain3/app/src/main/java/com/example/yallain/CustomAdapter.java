package com.example.yallain;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import android.view.View;
import android.widget.AdapterView;
public class CustomAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mData;
    private OnItemClickListener mListener;
    public CustomAdapter(Context context, List<String> data) {
        super(context, R.layout.custom_item, data);
        this.mContext = context;
        this.mData = data;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewItem = convertView.findViewById(R.id.textViewItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String item = mData.get(position);
        viewHolder.textViewItem.setText(item);
// Set click listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(view, position);
                }
            }
        });
        return convertView;
    }
    private static class ViewHolder {
        TextView textViewItem;
    }
}