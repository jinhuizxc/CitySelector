package com.example.jinhui.cityselector.effcet1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jinhui.cityselector.R;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private String[] mStringArray;

    public Adapter(Context context, String[] args) {
        this.context = context;
        this.mStringArray = args;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.textView.setText(mStringArray[position]);
    }

    @Override
    public int getItemCount() {
        return mStringArray.length;
    }

    // 定义viewholaer

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

    }

}
