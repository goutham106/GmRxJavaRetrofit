package com.gm.gmrxjavaretrofit.recipy;

import dagger.Module;
import dagger.Provides;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
@Module
public class recipyModule {
    @Provides
    @recipy
    public recipyPresenter providePresenter(recipyPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @recipy
    public recipyInteractor provideInteractor(recipyInteractorImpl interactor) {
        return interactor;
    }

}
