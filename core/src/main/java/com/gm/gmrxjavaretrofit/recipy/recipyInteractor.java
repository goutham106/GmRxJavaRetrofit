package com.gm.gmrxjavaretrofit.recipy;

import com.gm.gmrxjavaretrofit.base.BaseInteractor;
import com.gm.gmrxjavaretrofit.domain.model.Recipy;
import com.gm.gmrxjavaretrofit.domain.model.Result;

import rx.Observable;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public interface recipyInteractor extends BaseInteractor {
    Observable<Recipy> loadrecipyResult(String query);

}
