package org.nagarro.todolist.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.nagarro.todolist.network.GetDataService;
import org.nagarro.todolist.network.RetrofitClientInstance;

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter{
    Context context;
    public HomeFragmentPresenter(Context context) {
        this.context = context;
    }

    @Override
    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable();
    }

    @Override
    public GetDataService getDataService(){
        return RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    }
}
