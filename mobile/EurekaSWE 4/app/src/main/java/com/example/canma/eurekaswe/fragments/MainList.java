package com.example.canma.eurekaswe.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canma.eurekaswe.EurekaApplication;
import com.example.canma.eurekaswe.LoginActivity;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.adapters.MainRecyclerAdapter;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.ResponseLogin;
import com.example.canma.eurekaswe.interfaces.calls.ListApi;
import com.example.canma.eurekaswe.interfaces.calls.LoginApi;
import com.example.canma.eurekaswe.interfaces.calls.SearchListApi;
import com.tumblr.remember.Remember;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainList extends android.support.v4.app.Fragment {

    View view = null;


    MainActivity mainActivity;

    Unbinder unbinder;

    MainRecyclerAdapter adapter;

    @Inject
    @Named("regular")
    Retrofit retrofit;


    @BindView(R.id.recyclerview)
    RecyclerView mainRecyclerview;


    @BindView(R.id.closeButton)
    ImageView closeButton;


    @BindView(R.id.editMobileNo)
    EditText searchBox;




    @OnClick(R.id.closeButton)
    public void refresh(View v){
        listItems();
v.setVisibility(View.GONE);

searchBox.setText("");
        searchBox.clearFocus();
    }


    @OnClick(R.id.outButton)
    public void logout(){

        Intent intent = new Intent(mainActivity, LoginActivity.class);
        startActivity(intent);

mainActivity.finish();



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        ((EurekaApplication) getActivity().getApplication()).getNetComponent().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onStop() {

        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_list, container, false);


        unbinder = ButterKnife.bind(this, view);
        adapter = new MainRecyclerAdapter(getActivity(),retrofit);




        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ( (actionId== EditorInfo.IME_ACTION_DONE)||
                        (actionId== EditorInfo.IME_NULL)) {
                    // Perform action on key press

                    searchItem(((EditText)v).getText().toString());

                    closeButton.setVisibility(View.VISIBLE);


                    return true;
                }



                return false;
            }
        });

        searchBox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

searchItem(((EditText)v).getText().toString());

closeButton.setVisibility(View.VISIBLE);


                    return true;
                }
                return false;
            }
        });

        mainRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        List temp =new ArrayList<CellData>();

        mainRecyclerview.setAdapter(adapter);
    listItems();




adapter.notifyDataSetChanged();


        return view;
    }


    @Override
    public void onPause() {
        super.onPause();

        searchBox.setText("");

    }

    @Override
    public void onResume() {
        super.onResume();


    }


    public void listItems() {


        ListApi listApi = retrofit.create(ListApi.class);



       String auth= Remember.getString("token","oops");

        Call<List<CellData>> call = listApi.list("application/json", auth);

        call.enqueue(new Callback<List<CellData>>() {
            @Override
            public void onResponse(Call<List<CellData>> call, Response<List<CellData>> response) {


adapter.addCellData(response.body());


            }

            @Override
            public void onFailure(Call<List<CellData>> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(mainActivity).create();
                alertDialog.setTitle("Warning");
                alertDialog.setMessage("Please try again later!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });


    }





    public void searchItem(String s) {


        SearchListApi listApi = retrofit.create(SearchListApi.class);

        String auth= Remember.getString("token","oops");

        Call<List<CellData>> call = listApi.search(auth,s);

        call.enqueue(new Callback<List<CellData>>() {
            @Override
            public void onResponse(Call<List<CellData>> call, Response<List<CellData>> response) {

List<CellData> tempList=response.body();

if(tempList.size()>0) {
    adapter.setNewData(response.body());
}else {

    AlertDialog alertDialog = new AlertDialog.Builder(mainActivity).create();
    alertDialog.setTitle("Warning");
    alertDialog.setMessage("Search not found!");
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    refresh(closeButton);
                    dialog.dismiss();
                }
            });
    alertDialog.show();


}




            }

            @Override
            public void onFailure(Call<List<CellData>> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(mainActivity).create();
                alertDialog.setTitle("Warning");
                alertDialog.setMessage("Network connection problem!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });


    }

}
