package com.gm.gmrxjavaretrofit.recipy;

import com.gm.gmrxjavaretrofit.domain.client.TestApi;
import com.gm.gmrxjavaretrofit.domain.model.Recipy;
import com.gm.gmrxjavaretrofit.domain.model.Result;
import com.gm.gmrxjavaretrofit.util.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
@recipy
public class recipyInteractorImpl implements recipyInteractor {

    private TestApi api;
    private SchedulerProvider scheduler;

    private ReplaySubject<Recipy> recipySubject;
    private Subscription characterSubscription;

    @Inject
    recipyInteractorImpl(TestApi api, SchedulerProvider scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<Recipy> loadrecipyResult(String query) {
        if (characterSubscription == null || characterSubscription.isUnsubscribed()) {
            recipySubject = ReplaySubject.create();

            characterSubscription = api.getRecipyBySearch(query)
                    .subscribeOn(scheduler.backgroundThread())
                    .subscribe(recipySubject);
        }

        return recipySubject.asObservable();
    }

    @Override
    public void unbind() {
        if (characterSubscription != null && !characterSubscription.isUnsubscribed())
            characterSubscription.unsubscribe();
    }
}
