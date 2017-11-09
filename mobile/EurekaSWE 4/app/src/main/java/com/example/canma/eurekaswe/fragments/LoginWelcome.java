package com.example.canma.eurekaswe.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canma.eurekaswe.LoginActivity;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginWelcome extends  android.support.v4.app.Fragment {

    View view = null;

    LoginActivity loginActivity;


@OnClick(R.id.button_start)
public void changeFrag(){
    Log.d("signup","signup");

    android.support.v4.app.FragmentManager manager= loginActivity.getSupportFragmentManager();
    android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();


    LoginSignUp loginSignUp= new LoginSignUp();
    transaction.addToBackStack("WELCOME");



    transaction.replace(R.id.fragment_container_login,loginSignUp);
    transaction.commitAllowingStateLoss();

    manager.executePendingTransactions();


}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
loginActivity= (LoginActivity) getActivity();



    }




Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_welcome, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
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







    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();


    }










}
