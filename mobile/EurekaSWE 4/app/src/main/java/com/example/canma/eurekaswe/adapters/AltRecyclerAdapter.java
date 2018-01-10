package com.example.canma.eurekaswe.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.data.AltCellData;
import com.example.canma.eurekaswe.data.Body;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.Creator;
import com.example.canma.eurekaswe.data.Selector;
import com.example.canma.eurekaswe.data.eventBusEvents.BussedToHighlight;
import com.tumblr.remember.Remember;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by suzaneceada on 10/31/2017.
 */

public class AltRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int position;

    private Context context;


    String myId;


    private ArrayList<AltCellData> cellDatas = new ArrayList<>();


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    Retrofit retrofit;


    String auth;

    public AltRecyclerAdapter(Context context, Retrofit retrofit) {
        this.context = context;


        this.retrofit = retrofit;


        auth = Remember.getString("token", "oops");
        myId = Remember.getString("uid", "oops");

    }


    public void addAltCellData(List<AltCellData> adds) {

        cellDatas= new ArrayList<>();
        cellDatas.addAll(adds);
        notifyDataSetChanged();
    }

    public void removeAltCellDatas(int index) {
        cellDatas.remove(index);
        notifyItemRemoved(index);
    }

    public ArrayList<AltCellData> getAltCellDatas() {
        return cellDatas;
    }


    public class AltRecyclerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {




        @BindView(R.id.alt_user_tv)
        TextView altUserTv;
        @BindView(R.id.alt_text_view)
        TextView altTextView;
        @BindView(R.id.alt_image)
        ImageView altImage;

AltCellData altCllD;
        public AltRecyclerAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);



            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {


            EventBus.getDefault().post(new BussedToHighlight(altCllD.selector,cellDatas.indexOf(altCllD)));

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alt_cell, parent, false);
        return new AltRecyclerAdapterViewHolder(v);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final AltRecyclerAdapterViewHolder recyclerAdapterViewHolder = (AltRecyclerAdapterViewHolder) holder;
        this.position = position;

        final AltCellData altCellData = cellDatas.get(position);
recyclerAdapterViewHolder.altCllD=altCellData;

   List<Body> b=altCellData.body;

        Creator c= altCellData.creator;

        recyclerAdapterViewHolder.altUserTv.setText(c.nickname);
for (Body onebody: b) {
    if (onebody.type.contentEquals("Image")) {

        recyclerAdapterViewHolder.altImage.setVisibility(View.VISIBLE);
        Glide
                .with(context)
                .load(onebody.value)
                .into(recyclerAdapterViewHolder.altImage);

    }
    if (onebody.type.contentEquals("TextualBody")) {
        recyclerAdapterViewHolder.altTextView.setVisibility(View.VISIBLE);
        recyclerAdapterViewHolder.altTextView.setText(onebody.value);
        recyclerAdapterViewHolder.altUserTv.setText(c.nickname);


    }
}

    }

    @Override
    public int getItemCount() {
        return cellDatas.size();
    }


}

