package com.gm.gmrxjavaretrofit.recipy;

import com.gm.gmrxjavaretrofit.base.BaseView;
import com.gm.gmrxjavaretrofit.domain.model.Recipy;
import com.gm.gmrxjavaretrofit.domain.model.Result;

import java.util.List;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public interface recipyView extends BaseView {

    void showError(Throwable throwable);

    void showProgress();

    void hideProgress();

    void setRecipyData(Recipy recipyList);

    void showQueryError(Throwable throwable);

    void showCharacter(Recipy character);

    void showRetryMessage(Throwable throwable);

    void showQueryNoResult();

    void showServiceError(ApiResponseCodeException throwable);

}
