package com.gm.gmrxjavaretrofit;

import com.gm.gmrxjavaretrofit.activity.MainActivity;
import com.gm.gmrxjavaretrofit.activity.RecipyDescriptionActivity;
import com.gm.gmrxjavaretrofit.activity.Splash;
import com.gm.gmrxjavaretrofit.domain.ApiModule;
import com.gm.gmrxjavaretrofit.domain.ClientModule;
import com.gm.gmrxjavaretrofit.recipy.RecipySubComponent;
import com.gm.gmrxjavaretrofit.recipy.recipyModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ClientModule.class
})
public interface ApplicationComponent {

    void inject(Splash activity);

    void inject(MainActivity activity);

    void inject(RecipyDescriptionActivity characterActivity);

    RecipySubComponent plus(recipyModule module);


}