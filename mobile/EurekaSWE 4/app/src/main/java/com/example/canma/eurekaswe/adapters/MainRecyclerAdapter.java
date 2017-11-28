package com.example.canma.eurekaswe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.components.NewImageView;
import com.example.canma.eurekaswe.data.CellData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by canma on 10/31/2017.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int position;

    private Context context;

    private ArrayList<CellData> cellDatas = new ArrayList<>();


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public MainRecyclerAdapter(Context context) {
        this.context = context;


    }




 /*   public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
*/

    public void addCellData(List<CellData> adds) {
        cellDatas.addAll(adds);
        notifyDataSetChanged();
    }

    public void removeCellDatas(int index) {
        cellDatas.remove(index);
        notifyItemRemoved(index);
    }

    public ArrayList<CellData> getCellDatas() {
        return cellDatas;
    }


    public class MainRecyclerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.user_tv)
        TextView userTv;
        @BindView(R.id.date_tv)
        TextView dateTv;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_location)
        TextView itemLocation;
        @BindView(R.id.item_image)
        NewImageView itemImage;
        @BindView(R.id.item_expl)
        TextView itemExpl;
        @BindView(R.id.item_time_info)
        TextView time_detail;

        @BindView(R.id.categories)
        TextView categories;


        public MainRecyclerAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eureka_cell, parent, false);
        return new MainRecyclerAdapterViewHolder(v);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final MainRecyclerAdapterViewHolder recyclerAdapterViewHolder = (MainRecyclerAdapterViewHolder) holder;
this.position=position;

        final CellData cellData = cellDatas.get(position);



((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).userTv.setText(cellData.owner.name);

((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).dateTv.setText(cellData.createdAt==null?"null":cellData.createdAt);
((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).itemExpl.setText(cellData.description);
((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).itemLocation.setText("null-no location");
((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).itemName.setText(cellData.name);

        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).categories.setText(cellData.category);


        String temp="";


        for(int x=0;x<cellData.time.count;x++){
            temp+=cellData.time.values.get(x)+" - ";
        }

        temp.substring(0,temp.length()-3);

        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).time_detail.setText(cellData.time.name+" "+temp);

        Glide
                .with(context)
                .load(cellData.image)
                .into( recyclerAdapterViewHolder.itemImage);




        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(recyclerAdapterViewHolder.getPosition());
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return cellDatas.size();
    }


}

