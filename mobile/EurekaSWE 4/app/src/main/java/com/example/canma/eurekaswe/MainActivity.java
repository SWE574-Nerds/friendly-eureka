package com.example.canma.eurekaswe;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import com.example.canma.eurekaswe.data.CategoryFormat;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.TimeFormat;
import com.example.canma.eurekaswe.data.eventBusEvents.BussedToDetail;
import com.example.canma.eurekaswe.fragments.Detail;
import com.example.canma.eurekaswe.fragments.LoginWelcome;
import com.example.canma.eurekaswe.fragments.MainCreate;
import com.example.canma.eurekaswe.fragments.MainList;
import com.example.canma.eurekaswe.interfaces.calls.CategoryApi;
import com.example.canma.eurekaswe.interfaces.calls.TimeApi;
import com.tumblr.remember.Remember;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

   public FloatingActionButton fab;
    @OnClick(R.id.fab)
    public void fabClick(){
        android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        MainCreate mainCreate= new MainCreate();
        transaction.replace(R.id.fragment_container,mainCreate);
        transaction.addToBackStack("create");
        transaction.commit();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainRecyclerClickedEvent(BussedToDetail bussedToDetail) {
        android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        Detail detail= new Detail();
        transaction.replace(R.id.fragment_container,detail);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", bussedToDetail.cellData);
        detail.setArguments(bundle);
        transaction.addToBackStack("detail");
        transaction.commit();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((EurekaApplication) getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);
        formatlariCek();
        android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        MainList mainList= new MainList();
        transaction.replace(R.id.fragment_container,mainList);
        transaction.addToBackStack("home");
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
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("backstackentry",""+count);
        if (count < 2) {
            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}