package com.gm.gmrxjavaretrofit;

import android.app.Application;
import android.content.Context;

import com.gm.gmrxjavaretrofit.recipy.RecipySubComponent;
import com.gm.gmrxjavaretrofit.recipy.recipyModule;


public abstract class RecipyApplication extends Application {

    private static ApplicationComponent component;
    private RecipySubComponent searchSubComponent;

    public static ApplicationComponent getComponent() {
        return component;
    }

    public static RecipyApplication get(Context context) {
        return (RecipyApplication) context.getApplicationContext();
    }
    

    public RecipySubComponent getRecipySubComponent() {
        if (null == searchSubComponent)
            createRecipySubComponent();

        return searchSubComponent;
    }

    public RecipySubComponent createRecipySubComponent() {
        searchSubComponent = component.plus(new recipyModule());
        return searchSubComponent;
    }

    public void releaseRecipySubComponent() {
        searchSubComponent = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        component = createComponent();
    }

    public ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public abstract void initApplication();

}
