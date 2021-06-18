package org.nagarro.todolist.presenter;

import org.nagarro.todolist.network.GetDataService;

public interface HomeFragmentContract {
    interface View {
        void onError();
    }

    interface Presenter {
        boolean checkNetwork();
        GetDataService getDataService();
    }

}
