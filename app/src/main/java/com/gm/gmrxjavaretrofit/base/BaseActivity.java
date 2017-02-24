package com.gm.gmrxjavaretrofit.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gm.gmrxjavaretrofit.ApplicationComponent;
import com.gm.gmrxjavaretrofit.RecipyApplication;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies(RecipyApplication.get(this), RecipyApplication.getComponent());

        // can be used for general purpose in all Activities of Application
    }

    protected abstract void injectDependencies(RecipyApplication application, ApplicationComponent component);

    @Override
    public void finish() {
        super.finish();

        releaseSubComponents(RecipyApplication.get(this));
    }

    protected abstract void releaseSubComponents(RecipyApplication application);

}
