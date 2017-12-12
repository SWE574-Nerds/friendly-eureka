package com.example.canma.eurekaswe.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.data.CategoryFormat;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

/**
 * Created by suzaneceada on 5.12.2017.
 */



public class RecyclerPostAdapter extends RecyclerView.Adapter<RecyclerPostAdapter.ViewHolder> {
    private CategoryFormat[] mDataSet;
    private Context mContext;


    public List<String> secilen;

    Gson gson;


    public void setmDataSet(CategoryFormat[] mDataSet) {
        this.mDataSet = mDataSet;

        notifyDataSetChanged();
    }



    public RecyclerPostAdapter(Context context) {

        mContext = context;

        gson=new Gson();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public LinearLayout mRelativeLayout;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.category_text);
            mRelativeLayout = (LinearLayout) v.findViewById(R.id.category_relative_post);
        }
    }

    @Override
    public RecyclerPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.category_cell_post, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataSet[position].name);

        holder.mTextView.setTag(false);

        holder.mRelativeLayout.setTag(position);
        // Set a random height for TextView
        holder.mTextView.setBackgroundResource(R.drawable.category_background);


        GradientDrawable draws = (GradientDrawable) holder.mTextView.getBackground();
        draws.setStroke(2, Color.parseColor("#cdcdcd"));



        /*holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                TextView t = (TextView) view.findViewById(R.id.category_text);


                int positionInner = (Integer) view.getTag();

                GradientDrawable drawable = (GradientDrawable) t.getBackground();


                if (t.getTag().equals(false)) {
                    drawable.setColor(Color.parseColor("#" + mDataSet[positionInner].color));
                    t.setTextColor(Color.WHITE);
                    secilen.add(mDataSet[positionInner].id);


                    drawable.setStroke(2, Color.WHITE);

                    t.setTag(true);


                } else {

                    drawable.setStroke(2, Color.parseColor("#cdcdcd"));
                    drawable.setColor(c);
                    t.setTextColor(Color.parseColor("#cdcdcd"));
                    secilen.remove(mDataSet[positionInner].id);

                    t.setTag(false);


                }







                StringBuilder sb = new StringBuilder();
                for (String s : secilen)
                {
                    sb.append(s);
                    sb.append(",");
                }
                //Log.d("Secilen",sb.toString());

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }



    public String getPostCategoryParams(){


        String temp;
        temp= gson.toJson(secilen);

        //  Log.d("secilenler",temp);

        return  temp;
    }
}