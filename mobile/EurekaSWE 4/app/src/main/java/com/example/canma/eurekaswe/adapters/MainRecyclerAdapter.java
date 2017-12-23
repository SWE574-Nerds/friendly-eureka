package com.example.canma.eurekaswe.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.components.NewImageView;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.eventBusEvents.BussedToDetail;
import com.example.canma.eurekaswe.fragments.MainCreate;
import com.example.canma.eurekaswe.interfaces.calls.DeleteListoryApi;
import com.example.canma.eurekaswe.interfaces.calls.FollowApi;
import com.example.canma.eurekaswe.interfaces.calls.UnFollowApi;
import com.tumblr.remember.Remember;

import org.greenrobot.eventbus.EventBus;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by suzaneceada on 10/31/2017.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int position;

    private Context context;


    String myId;



    private ArrayList<CellData> cellDatas = new ArrayList<>();


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }




    FollowApi followApi;
    UnFollowApi unFollowApi;
    DeleteListoryApi deleteApi;


    Retrofit retrofit;


    String auth;

    public MainRecyclerAdapter(Context context,Retrofit retrofit) {
        this.context = context;


        this.retrofit=retrofit;

        followApi=retrofit.create(FollowApi.class);
        unFollowApi=retrofit.create(UnFollowApi.class);
        deleteApi=retrofit.create(DeleteListoryApi.class);



        auth= Remember.getString("token","oops");
        myId= Remember.getString("uid","oops");

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



RecyclerPostAdapter categoryAdapter;



        @BindView(R.id.user_tv)
        TextView userTv;
        @BindView(R.id.date_tv)
        TextView dateTv;
        @BindView(R.id.item_name)
        TextView itemName;
     //   @BindView(R.id.item_location)


       // TextView itemLocation;
        @BindView(R.id.item_image)
        NewImageView itemImage;
        @BindView(R.id.item_expl)
        TextView itemExpl;
        @BindView(R.id.item_time_info)
        TextView time_detail;

        @BindView(R.id.follow_button)
        Button followBut;

        @BindView(R.id.delete_button)
        Button deleteBut;

        @BindView(R.id.category_recycler)
        RecyclerView categoriesRecycler;

        CellData cdata;

        @OnClick(R.id.follow_button)
        public void followRequest(final View view){


            Call<ResponseBody> call = followApi.follow(auth, (String) view.getTag());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){


                        view.setVisibility(View.GONE);
                        Toast.makeText(context, "Follow successful", Toast.LENGTH_SHORT).show();


                    }




                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }

        @OnClick(R.id.delete_button)
        public void DeleteRequest(final View view){


            Call<ResponseBody> call = deleteApi.delete(auth, ""+view.getTag());


            Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    view.setVisibility(View.GONE);
                    Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show();

                    for (CellData c:cellDatas){
                        if(c.listoryId==(int)view.getTag()){
                            removeCellDatas(cellDatas.indexOf(c));
                            break;
                        }

                    }



                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }



        public MainRecyclerAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);



            categoryAdapter= new RecyclerPostAdapter(context);
            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {





            EventBus.getDefault().post(new BussedToDetail(cdata));


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

recyclerAdapterViewHolder.cdata=cellData;
        long l = Long.parseLong(cellData.createdAt)* 1000;
        //current android time in epoch
//Converts epoch to "dd/MM/yyyy HH:mm:ss" dateformat
        String NormalDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(l));
        long time = Long.parseLong(cellData.createdAt);
        //current android time in epoch
//Converts epoch to "dd/MM/yyyy HH:mm:ss" dateformat
        SimpleDateFormat f = new java.text.SimpleDateFormat("hh:mm");
        f.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        String timeDate =f.format(new java.util.Date(time));



        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).userTv.setText(cellData.owner.name);

        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).dateTv.setText(cellData.createdAt==null?"10 m":timeDate+" "+NormalDate);

        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).itemExpl.setText(cellData.description);
//((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).itemLocation.setText("");
((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).itemName.setText(cellData.name);
        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).followBut.setTag(cellData.listoryId);
        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).deleteBut.setTag(cellData.listoryId);
      recyclerAdapterViewHolder.categoryAdapter.setmDataSet(cellData.tags);



        if(cellData.owner.userId!=Integer.parseInt(myId)){

            recyclerAdapterViewHolder.deleteBut.setVisibility(View.INVISIBLE);

        }else {
            recyclerAdapterViewHolder.deleteBut.setVisibility(View.VISIBLE);

        }



        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerAdapterViewHolder.categoriesRecycler.setLayoutManager(mLayoutManager);

        recyclerAdapterViewHolder.categoriesRecycler.setAdapter(recyclerAdapterViewHolder.categoryAdapter);

        String temp="";


        for(int x=0;x<cellData.time.count;x++){
            temp+=cellData.time.values.get(x)+" - ";
        }

        temp.substring(0,temp.length()-3);

        ((MainRecyclerAdapterViewHolder) recyclerAdapterViewHolder).time_detail.setText(cellData.time.name+" "+temp);


        if(cellData.image.length()<1){
            recyclerAdapterViewHolder.itemImage.setVisibility(View.GONE);
        }else {

            if(!cellData.image.startsWith("http")){
                cellData.image="http://"+cellData.image;
            }

            Glide
                    .with(context)
                    .load(cellData.image)
                    .dontAnimate()
                    .into(recyclerAdapterViewHolder.itemImage);


        }

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


    public void setNewData(List<CellData> datas){
        cellDatas.clear();
        cellDatas.addAll(datas);
        notifyDataSetChanged();

    }

}

