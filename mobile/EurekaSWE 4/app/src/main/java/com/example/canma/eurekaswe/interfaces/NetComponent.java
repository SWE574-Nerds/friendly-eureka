package com.example.canma.eurekaswe.interfaces;

import com.example.canma.eurekaswe.LoginActivity;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.fragments.LoginSignIn;
import com.example.canma.eurekaswe.fragments.LoginSignUp;
import com.example.canma.eurekaswe.modules.AppModule;
import com.example.canma.eurekaswe.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(LoginSignUp fragment);
    void inject(LoginSignIn fragment);
    // void inject(MyService service);
}