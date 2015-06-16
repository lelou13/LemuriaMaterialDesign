package androidhive.info.materialdesign;

import android.app.Application;
import android.content.Context;

/**
 * Created by Khusnan on 6/14/15.
 */
public class MyApplication extends Application {

    private static MyApplication sIntance;

    @Override
    public void onCreate() {
        super.onCreate();
        sIntance = this;
    }

    public static MyApplication getsIntance(){
        return sIntance;
    }

    public static Context getAppContext(){
        return sIntance.getApplicationContext();
    }
}
