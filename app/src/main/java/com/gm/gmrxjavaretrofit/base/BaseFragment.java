package com.gm.gmrxjavaretrofit.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.gm.gmrxjavaretrofit.RecipyApplication;


public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        injectDependencies(RecipyApplication.get(getContext()));

        // can be used for general purpose in all Fragments of Application
    }

    protected abstract void injectDependencies(RecipyApplication application);

}