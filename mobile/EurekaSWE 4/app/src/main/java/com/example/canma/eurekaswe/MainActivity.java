package com.example.canma.eurekaswe;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.canma.eurekaswe.data.CategoryFormat;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.TimeFormat;
import com.example.canma.eurekaswe.fragments.LoginWelcome;
import com.example.canma.eurekaswe.fragments.MainCreate;
import com.example.canma.eurekaswe.fragments.MainList;
import com.example.canma.eurekaswe.interfaces.calls.CategoryApi;
import com.example.canma.eurekaswe.interfaces.calls.TimeApi;
import com.tumblr.remember.Remember;

import java.security.PublicKey;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {




    public static List<TimeFormat> timef;

    public static List<CategoryFormat> catf;

    @Inject
    @Named("regular")
    public Retrofit retrofit;

    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;


    @OnClick(R.id.fab)
    public void fabClick(){



        android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();


        MainCreate mainCreate= new MainCreate();
        transaction.replace(R.id.fragment_container,mainCreate);
        transaction.addToBackStack("create");
        transaction.commit();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ((EurekaApplication) getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);


formatlariCek();


        android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();


        MainList mainList= new MainList();

        transaction.replace(R.id.fragment_container,mainList);
        transaction.addToBackStack(null);
        transaction.commit();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void formatlariCek(){

        final TimeApi timeApi= retrofit.create(TimeApi.class);

        CategoryApi categoryApi =retrofit.create(CategoryApi.class);

        String auth= Remember.getString("token","oops");

        Call<List<TimeFormat>>  callTime=timeApi.list("application/json", auth);

        Call<List<CategoryFormat>>  callCategory=categoryApi.list("application/json", auth);
        callTime.enqueue(new Callback<List<TimeFormat>>() {
            @Override
            public void onResponse(Call<List<TimeFormat>> call, Response<List<TimeFormat>> response) {


                if(response.isSuccessful()){


                    timef=response.body();



                }



            }

            @Override
            public void onFailure(Call<List<TimeFormat>> call, Throwable t) {

            }
        });

        callCategory.enqueue(new Callback<List<CategoryFormat>>() {
            @Override
            public void onResponse(Call<List<CategoryFormat>> call, Response<List<CategoryFormat>> response) {




                if(response.isSuccessful()){



                    catf=response.body();


                }

            }

            @Override
            public void onFailure(Call<List<CategoryFormat>> call, Throwable t) {

            }
        });







    }

}
