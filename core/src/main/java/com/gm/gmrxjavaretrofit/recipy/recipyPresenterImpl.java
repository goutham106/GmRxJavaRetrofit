package com.gm.gmrxjavaretrofit.recipy;

import com.gm.gmrxjavaretrofit.domain.model.Recipy;
import com.gm.gmrxjavaretrofit.util.Constants;
import com.gm.gmrxjavaretrofit.util.SchedulerProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.subscriptions.Subscriptions;

import static com.gm.gmrxjavaretrofit.util.Constants.CODE_OK;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public class recipyPresenterImpl implements recipyPresenter {

    @Inject
    recipyInteractor interactor;

    private recipyView view;
    private Subscription subscription = Subscriptions.empty();
    private SchedulerProvider scheduler;
    private Recipy recipyList = null;

    @Inject
    public recipyPresenterImpl(SchedulerProvider scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void bind(recipyView view) {
        this.view = view;
    }

    @Override
    public void doSearch(boolean isConnected, String query) {

        if (null != view) {
            view.showProgress();
        }

        subscription = interactor.loadrecipyResult(query)
                // check if result code is OK
                .map(charactersResponse -> {
                    if (CODE_OK == CODE_OK)
                        return charactersResponse;
                    else
                        throw Exceptions.propagate(new ApiResponseCodeException(201, "ERROR in API"));
                })
                // check if is there any result
                .map(charactersResponse -> {
                    if (charactersResponse.getResults().size() > 0)
                        return charactersResponse;
                    else
                        throw Exceptions.propagate(new NoSuchCharacterException());
                })
                .observeOn(scheduler.mainThread())
                .subscribe(character -> {
                            if (null != view) {
                                view.hideProgress();
                                view.showCharacter(character);
                                recipyList = character;
                                if (!isConnected)
                                    view.showOfflineMessage(false);
                            }
                        },
                        // handle exceptions
                        throwable -> {
                            if (null != view) {
                                view.hideProgress();

                                if (isConnected) {
                                    if (throwable instanceof ApiResponseCodeException)
                                        view.showServiceError((ApiResponseCodeException) throwable);
                                    else if (throwable instanceof NoSuchCharacterException)
                                        view.showQueryNoResult();
                                    else
                                        view.showRetryMessage(throwable);

                                } else {
                                    view.showOfflineMessage(true);
                                }
                            }
                        });

    }

    @Override
    public boolean isQueryValid(String query) {
        return null != query && !query.isEmpty();
    }

    @Override
    public void loadRecipyData() {
        if (null != view)
            try {
                view.setRecipyData(recipyList);
            } catch (Exception e) {
                view.showError(e);
            }
    }

    @Override
    public void unbind() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();

        interactor.unbind();

        view = null;
    }
}
