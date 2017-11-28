package com.example.canma.eurekaswe.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.canma.eurekaswe.EurekaApplication;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.adapters.MainRecyclerAdapter;
import com.example.canma.eurekaswe.data.CategoryFormat;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.CreateData;
import com.example.canma.eurekaswe.data.ResponseLogin;
import com.example.canma.eurekaswe.data.TimeFormat;
import com.example.canma.eurekaswe.data.TimeInfo;
import com.example.canma.eurekaswe.interfaces.calls.CreateApi;
import com.example.canma.eurekaswe.interfaces.calls.ListApi;
import com.google.gson.Gson;
import com.tumblr.remember.Remember;

import java.util.ArrayList;
import java.util.List;

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


public class MainCreate extends Fragment {

    View view = null;


    MainActivity mainActivity;

    Unbinder unbinder;

    @Inject
    @Named("regular")
    Retrofit retrofit;

    @BindView(R.id.title_edit_text)
    EditText titleEditText;
    @BindView(R.id.detail_edit_text)
    EditText detailEditText;
    @BindView(R.id.link_edit_text)
    EditText linkEditText;
    @BindView(R.id.location_edit_text)
    EditText locationEditText;

    @BindView(R.id.date1)
    EditText date1;
    @BindView(R.id.date2)
    EditText date2;
    @BindView(R.id.categoryspinner)
    Spinner categorySpinner;
    @BindView(R.id.datespinner)
    Spinner dateSpinner;



    @OnClick(R.id.create_b)
    public void createPressed(){


        sendContent();
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

        view = inflater.inflate(R.layout.fragment_main_create, container, false);





        unbinder = ButterKnife.bind(this, view);







        ArrayAdapter<CategoryFormat> adapter = new ArrayAdapter<CategoryFormat>(mainActivity, android.R.layout.simple_spinner_item,mainActivity.catf);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

categorySpinner.setAdapter(adapter);






        final ArrayAdapter<TimeFormat> adapter1 = new ArrayAdapter<TimeFormat>(mainActivity, android.R.layout.simple_spinner_item,mainActivity.timef);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dateSpinner.setAdapter(adapter1);









        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TimeFormat timeFormat = adapter1.getItem(position);



                if(timeFormat.value_count>1){
                    date2.setVisibility(View.VISIBLE);
                    date1.setVisibility(View.VISIBLE);

                }else if(timeFormat.value_count==1) {
                    date2.setVisibility(View.GONE);
                    date1.setVisibility(View.VISIBLE);
                }else {
                    date2.setVisibility(View.GONE);

                    date1.setVisibility(View.GONE);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        return view;
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();


    }


public  void sendContent(){
    CreateApi createApi=retrofit.create(CreateApi.class);

    CreateData wills= new CreateData();

    TimeInfo t = new TimeInfo();

    wills.description=detailEditText.getText().toString().trim();
    wills.image=linkEditText.getText().toString().trim();
    wills.name=titleEditText.getText().toString().trim();
    wills.category=((CategoryFormat)categorySpinner.getItemAtPosition(categorySpinner.getSelectedItemPosition())).id;


    TimeFormat timeFormat = (TimeFormat)dateSpinner.getItemAtPosition(dateSpinner.getSelectedItemPosition());



    if(timeFormat.value_count>1){
        t.value_1=date1.getText().toString().trim();
        t.value_2=date2.getText().toString().trim();
    }else if(timeFormat.value_count==1) {
        t.value_1=date1.getText().toString().trim();
    }else {


    }

t.id=((TimeFormat)dateSpinner.getItemAtPosition(dateSpinner.getSelectedItemPosition())).id;

wills.timeInfo=t;


    Gson gson=new Gson();



    String request= gson.toJson(wills);

    String auth= Remember.getString("token","oops");


    Call<CellData> call=createApi.list("application/json", auth,wills);


    call.enqueue(new Callback<CellData>() {
        @Override
        public void onResponse(Call<CellData> call, Response<CellData> response) {



            if(response.isSuccessful()){

                //ana ekrana don

                android.support.v4.app.FragmentManager manager= mainActivity.getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
manager.popBackStack();



            }



        }

        @Override
        public void onFailure(Call<CellData> call, Throwable t) {

        }
    });





}







}
