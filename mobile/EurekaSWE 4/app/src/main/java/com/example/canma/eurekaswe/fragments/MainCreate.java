package com.example.canma.eurekaswe.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canma.eurekaswe.EurekaApplication;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.adapters.MainRecyclerAdapter;
import com.example.canma.eurekaswe.components.ContactChip;
import com.example.canma.eurekaswe.data.CategoryFormat;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.CreateData;
import com.example.canma.eurekaswe.data.LatLong;
import com.example.canma.eurekaswe.data.Markers;
import com.example.canma.eurekaswe.data.PassedPolySmth;
import com.example.canma.eurekaswe.data.Polylines;
import com.example.canma.eurekaswe.data.ResponseLogin;
import com.example.canma.eurekaswe.data.TimeFormat;
import com.example.canma.eurekaswe.data.TimeInfo;
import com.example.canma.eurekaswe.interfaces.calls.CreateApi;
import com.example.canma.eurekaswe.interfaces.calls.ListApi;
import com.google.android.gms.maps.model.Polyline;
import com.google.gson.Gson;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.pchmn.materialchips.views.ChipsInputEditText;
import com.tumblr.remember.Remember;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
  //  @BindView(R.id.location_edit_text)
   // EditText locationEditText;

    @BindView(R.id.date1)
    EditText date1;
    @BindView(R.id.date2)
    EditText date2;
    //@BindView(R.id.categoryspinner)
    //Spinner categorySpinner;
    @BindView(R.id.datespinner)
    Spinner dateSpinner;
    @BindView(R.id.chips_input)
    ChipsInput chipsInput;

    @BindView(R.id.mapButton)
    Button mapButton;
    @BindView(R.id.addChip)
    Button addChip;
    @BindView(R.id.textView2)
    TextView locationsFromMapTextView;

    // build the ContactChip list
    List<ContactChip> contactList = new ArrayList<>();
    List<Polylines> polylinesList = new ArrayList<>();
    List<Markers> markersList = new ArrayList<>();

    @OnClick(R.id.create_b)
    public void createPressed(){


        sendContent();
    }


    int counter=100;


    @OnClick(R.id.addChip)
    public void createChip(View view)
    {






counter++;

            // chipsInput.addChip(new ContactChip("890",null,s,null));

            ContactChip contactChip=new ContactChip(""+counter, null, temp, null);
          //  chipsInput.getChipView().inflate(contactChip);
            contactList.add(contactChip);
            chipsInput.setFilterableList(contactList);
        chipsInput.addChip(contactChip);


            //chipsInput.addChip(s,"");


        view.setVisibility(View.GONE);
        mapButton.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.mapButton)
    public void mapButtonPressed(){

        ListoryCreateMapFragment mapFragment = new ListoryCreateMapFragment();

        FragmentManager manager = mainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.fragment_container, mapFragment);
        transaction.addToBackStack("create");
        transaction.commit();


    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        ((EurekaApplication) getActivity().getApplication()).getNetComponent().inject(this);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

String temp;
    ChipsInputEditText chipsInputEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_create, container, false);
        unbinder = ButterKnife.bind(this, view);





        //ArrayAdapter<CategoryFormat> adapter = new ArrayAdapter<CategoryFormat>(mainActivity, android.R.layout.simple_spinner_item,mainActivity.catf);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // categorySpinner.setAdapter(adapter);
        for(int i = 0;i< mainActivity.catf.size();i++){
            CategoryFormat category = mainActivity.catf.get(i);

            contactList.add(new ContactChip(String.valueOf(category.id),null,category.name,null));

        }
        chipsInput.setFilterableList(contactList);
        // chips listener
        chipsInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                Log.d("MainActiviy", "dkaşlskdşlskdşs");
            }
            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {

            }

            @Override
            public void onTextChanged(CharSequence text) {

           temp=text.toString();

                    mapButton.setVisibility(View.GONE);

addChip.setVisibility(View.VISIBLE);




            }
        });


/*

        chipsInput.getEditText().setImeOptions(EditorInfo.IME_ACTION_SEND);

        chipsInput.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    EditText e=(EditText)v;

                    String s=e.getText().toString();


                    Log.d("key","basildi");

                    if(s.trim().length()>0)
                        chipsInput.addChip(s,"");
                    handled = true;
                }
                return handled;
            }
        });
*/




       chipsInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode==KeyEvent.KEYCODE_ENTER){

                        EditText e=(EditText)v;

                        String s=e.getText().toString();


                        Log.d("key","basildi");

                        if(s.trim().length()>0)
                            chipsInput.addChip(s,"");


                        return true;

                    }



                }
                return false;
            }
        });





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
        (  (MainActivity)getActivity()).fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        (  (MainActivity)getActivity()).fab.setVisibility(View.GONE);


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPolylinePressed(LatLong obj) {
        if(locationsFromMapTextView.getText().toString().trim().contentEquals("")){

            locationsFromMapTextView.setText(obj.toString());

        }else{


            locationsFromMapTextView.setText(locationsFromMapTextView.getText()+","+obj.toString());
        }



        if(obj.marker==null){
            polylinesList.add(obj.polylines);
        }else{
            markersList.add(obj.marker);
        }


        Log.d("tionsFromMapTextView ",obj.toString());

    }

    public  void sendContent(){
        CreateApi createApi=retrofit.create(CreateApi.class);

        CreateData wills= new CreateData();

        TimeInfo t = new TimeInfo();

        wills.description=detailEditText.getText().toString().trim();
        wills.image=linkEditText.getText().toString().trim();
        wills.name=titleEditText.getText().toString().trim();

        List<ContactChip> contactsSelected = (List<ContactChip>) chipsInput.getSelectedChipList();
       // CategoryFormat[] catArray = new CategoryFormat[contactsSelected.size()];
        String[] strArray = new String[contactsSelected.size()];

        int i=0;
        for(ContactChip contact: contactsSelected ){
        String category = new String();
        //CategoryFormat category = new CategoryFormat();
            category = contact.name;
          //  category.id = Integer.parseInt(contact.id);
            Log.d("sendContent: ", category);
            strArray[i]= category;
            i++;
        }


        wills.tags=strArray;




        wills.polylines =  polylinesList.toArray(new Polylines[polylinesList.size()]);
        wills.markers = markersList.toArray(new Markers[markersList.size()]);


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
