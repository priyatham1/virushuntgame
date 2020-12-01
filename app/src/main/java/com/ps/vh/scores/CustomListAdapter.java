package com.ps.vh.scores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ps.vh.R;
import com.ps.vh.constant.C;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private  Context mContext;
    private LayoutInflater inflater;
    private List<Items> itemsItems;



    public CustomListAdapter(Context context, List<Items> itemsItems) {
        this.mContext = context;
        this.itemsItems = itemsItems;

    }

    @Override
    public int getCount() {
        return itemsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return itemsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View scoreView, ViewGroup parent) {
        ViewHolder holder;
        if (inflater == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (scoreView == null) {

            scoreView = inflater.inflate(R.layout.score_list_row, parent, false);
            holder = new ViewHolder();
            holder.photo = (ImageView) scoreView.findViewById(R.id.score_image);
            holder.name = (TextView) scoreView.findViewById(R.id.score_name);
            holder.score = (TextView) scoreView.findViewById(R.id.score_count);
            

            scoreView.setTag(holder);

        } else {
            holder = (ViewHolder) scoreView.getTag();
        }

        final Items m = itemsItems.get(position);

        holder.name.setText(m.getName());
        holder.score.setText(m.getScore());
        Log.d(C.logtag, "pic url:"+m.getPicUrl());
        C.pushRoundImage(holder.photo, m.getPicUrl());
        return scoreView;
    }

    static class ViewHolder {

        ImageView photo;
        TextView name;
        TextView score;

    }

}