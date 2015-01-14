package fr.utt.if26.resto;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import fr.utt.if26.resto.Model.Position;
import fr.utt.if26.resto.Model.User;

/**
 * Created by soedjede on 20/12/14 for Resto
 */
public class Resto extends Application {
    private static Context appContext;
    public static Position userPosition = null;

    public static User user = null;
    public static String server_address = "https://if26-server.herokuapp.com";

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(1500000)
                .discCacheSize(50000000)
                .httpReadTimeout(100000)
                .denyCacheImageMultipleSizesInMemory()
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().enableLogging();
    }

    public static Context getContext() {
        return appContext;
    }

    public static boolean isInternetconnected() {
        boolean connected = false;
        //get the connectivity manager object to identify the network state.
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Check if the manager object is NULL, this check is required. to prevent crashes in few devices.
        if (connectivityManager != null) {
            //Check Mobile data or Wifi net is present
            //we are connected to a network
            connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

        }
        return connected;
    }

    /*public static void menuActions(Activity context, MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.closeButton:
            case android.R.id.home:
                context.finish();
                context.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
            case R.id.action_menu_login_register:
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.action_menu_profile:
                Intent intent2 = new Intent(context, AccountActivity.class);
                context.startActivity(intent2);
                context.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.action_menu_about:
                // about
                break;
            case R.id.action_menu_help:
                // help action
                break;
        }
    }*/
}
