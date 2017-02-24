package com.gm.gmrxjavaretrofit.recipy;

import com.gm.gmrxjavaretrofit.base.BasePresenter;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public interface recipyPresenter extends BasePresenter<recipyView> {

    void doSearch(boolean isConnected, String query);

    boolean isQueryValid(String query);

    void loadRecipyData();

}
