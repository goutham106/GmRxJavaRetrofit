package com.gm.gmrxjavaretrofit.recipy;

import dagger.Subcomponent;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
@recipy
@Subcomponent(modules = {
        recipyModule.class
})
public interface RecipySubComponent {
    void inject(RecipyFragment fragment);
}
