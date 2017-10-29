package com.example.canma.eurekaswe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.canma.eurekaswe.components.GradientBackgroundPainter;
import com.example.canma.eurekaswe.fragments.LoginWelcome;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {


    @Inject
    @Named("regular")
    public Retrofit retrofit;

    @BindView(R.id.fragment_container_login)
    RelativeLayout fragmentContainerLogin;



    private GradientBackgroundPainter gradientBackgroundPainter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((EurekaApplication) getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);




        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;
        gradientBackgroundPainter = new GradientBackgroundPainter(fragmentContainerLogin, drawables);
        gradientBackgroundPainter.start();

startApp();
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




    public void startApp(){

        android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();


        LoginWelcome loginWelcome= new LoginWelcome();

        transaction.replace(R.id.fragment_container_login,loginWelcome);
        transaction.addToBackStack(null);
        transaction.commit();



    }

}
