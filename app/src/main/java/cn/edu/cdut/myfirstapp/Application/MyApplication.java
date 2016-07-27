package cn.edu.cdut.myfirstapp.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/6/21 0021.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
