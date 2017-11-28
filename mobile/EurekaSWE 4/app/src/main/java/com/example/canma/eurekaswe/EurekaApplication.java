package com.example.canma.eurekaswe;

import android.app.Application;


import com.example.canma.eurekaswe.interfaces.DaggerNetComponent;
import com.example.canma.eurekaswe.interfaces.NetComponent;
import com.example.canma.eurekaswe.modules.AppModule;
import com.example.canma.eurekaswe.modules.NetModule;
import com.tumblr.remember.Remember;

/**

 */

public class EurekaApplication extends Application {



    private NetComponent mNetComponent;


    @Override
    public void onCreate() {
        super.onCreate();


        Remember.init(getApplicationContext(), "com.example.canma.eurekaswe");

        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule("http://10.0.2.2:8000/api/"))
                .build();




    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }




}
