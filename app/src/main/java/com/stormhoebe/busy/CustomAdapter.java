package com.stormhoebe.busy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Guest on 6/19/17.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Need> needArrayList;


    public CustomAdapter(Context context, ArrayList<Need> modelArrayList) {

        this.context = context;
        this.needArrayList = modelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return needArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return needArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.optionTextView = (TextView) convertView.findViewById(R.id.option);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.optionTextView.setText(needArrayList.get(position).getNeed());

        holder.checkBox.setChecked(needArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView optionTextView = (TextView) tempview.findViewById(R.id.option);
                Integer pos = (Integer)  holder.checkBox.getTag();
                Toast.makeText(context, optionTextView.getText().toString() +" selected!", Toast.LENGTH_SHORT).show();

                if(needArrayList.get(pos).getSelected()){
                    needArrayList.get(pos).setSelected(false);
                }else {
                    needArrayList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView optionTextView;

    }

}

