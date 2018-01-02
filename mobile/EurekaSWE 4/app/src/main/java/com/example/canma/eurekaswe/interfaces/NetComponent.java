package com.example.canma.eurekaswe.interfaces;

import com.example.canma.eurekaswe.LoginActivity;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.fragments.Detail;
import com.example.canma.eurekaswe.fragments.DetailMapFragment;
import com.example.canma.eurekaswe.fragments.ListoryCreateMapFragment;
import com.example.canma.eurekaswe.fragments.LoginSignIn;
import com.example.canma.eurekaswe.fragments.LoginSignUp;
import com.example.canma.eurekaswe.fragments.MainCreate;
import com.example.canma.eurekaswe.fragments.MainList;
import com.example.canma.eurekaswe.modules.AppModule;
import com.example.canma.eurekaswe.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
    void inject(ListoryCreateMapFragment activity);
    void inject(LoginActivity fragment);
    void inject(LoginSignIn fragment);
    void inject(LoginSignUp fragment);
    void inject(MainList fragment);
    void inject(MainCreate fragment);
    void inject(Detail fragment);
    void inject(DetailMapFragment fragment);
    // void inject(MyService service);
}