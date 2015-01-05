package fr.utt.if26.resto;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import fr.utt.if26.resto.Model.User;

/**
 * Created by soedjede on 20/12/14 for Resto
 */
public class Resto extends Application {
    private static Context appContext;

    public static User user = null;
    public static String server_address = "https://if26-server.herokuapp.com";

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
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
}
