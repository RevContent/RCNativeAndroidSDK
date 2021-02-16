package com.revcontent.rcnativeandroidsdk.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class ConnectionMonitor {

    private Context context;

    public ConnectionMonitor(Context context){
        this.context = context;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;

            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            if (actNw == null) return false;
            if ( actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ) return true;
            if ( actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ) return true;
            if ( actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ) return true;
            return false;
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            if (nwInfo == null) return false;
            return nwInfo.isConnected();
        }
    }
}
