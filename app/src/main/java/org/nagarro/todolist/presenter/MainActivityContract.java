package org.nagarro.todolist.presenter;

import android.view.MenuItem;

public interface MainActivityContract {
    interface View {
        void onError();
    }

    interface Presenter {
        //        boolean checkNetwork();
        void applyTheme();

        void applyDarkTheme(boolean check);

        void exitApp(MenuItem item);
    }
}
