package com.example.canma.eurekaswe.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canma.eurekaswe.EurekaApplication;
import com.example.canma.eurekaswe.LoginActivity;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.data.ResponseRegister;
import com.example.canma.eurekaswe.interfaces.calls.RegisterApi;

import org.json.JSONObject;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginSignUp extends  android.support.v4.app.Fragment {

    View view = null;



    LoginActivity loginActivity;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.reenter)
    EditText reenter;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.button_sign_up)
    Button buttonSignUp;
    Unbinder unbinder;

    @Inject
    @Named("regular")
    Retrofit retrofit;




    @OnClick(R.id.bottom_linear)
    public void openSignInScreen(){

        FragmentManager manager = loginActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        LoginSignIn loginWelcome = new LoginSignIn();
        transaction.addToBackStack("signIn");

        transaction.replace(R.id.fragment_container_login, loginWelcome);
        transaction.commit();


    }


    @OnClick(R.id.button_sign_up)
    public void signUp() {



        if(email.getText().length()>0 &&
                username.getText().length()>0&&
                password.getText().length()>0&&
                reenter.getText().length()>0&&
                reenter.getText().toString().trim().contentEquals(password.getText().toString().trim())) {


tryLogin(username.getText().toString(),password.getText().toString(),email.getText().toString());





        }else {


            AlertDialog alertDialog = new AlertDialog.Builder(loginActivity).create();
            alertDialog.setTitle("Uyarı");
            alertDialog.setMessage("Uyarı");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity= (LoginActivity) getActivity();
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

        view = inflater.inflate(R.layout.fragment_login_sign_up, container, false);


        unbinder = ButterKnife.bind(this, view);
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




    public void tryLogin(String name,String password, String mail){


        RegisterApi registerApi = retrofit.create(RegisterApi.class);
        Map<String, Object> map = new ArrayMap<>();

        map.put("name", name);
        map.put("password", password);
        map.put("mail", mail);

        Log.d("TEST-REQUEST",(new JSONObject(map)).toString());


        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(map)).toString());


      retrofit2.Call<ResponseRegister> call= registerApi.register("application/json","application/json",body);

        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseRegister> call, Response<ResponseRegister> response) {

                if(response.isSuccessful()){

                    ResponseRegister r= response.body();
                   Log.d("NAME-RESPONSE: ",r.name);
                    Log.d("UserId-RESPONSE: ",""+r.userId);

                    Toast.makeText(getActivity(), "REGISTER BAŞARILI", Toast.LENGTH_SHORT).show();


                    FragmentManager manager = loginActivity.getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    // BURAYA SIGN IN (LOGIN) EKRANINI KOYACAKSIN vvvvvv
                    LoginSignIn loginWelcome = new LoginSignIn();

transaction.addToBackStack("signIn2");
                    transaction.replace(R.id.fragment_container_login, loginWelcome);
                    transaction.commit();

                }else {
                    AlertDialog alertDialog = new AlertDialog.Builder(loginActivity).create();
                    alertDialog.setTitle("Uyarı");
                    alertDialog.setMessage("Uyarı");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();


                }





            }

            @Override
            public void onFailure(retrofit2.Call<ResponseRegister> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(loginActivity).create();
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Uyarı");
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
