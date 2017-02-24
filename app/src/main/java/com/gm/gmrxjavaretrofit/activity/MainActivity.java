package com.gm.gmrxjavaretrofit.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.gm.gmrxjavaretrofit.ApplicationComponent;
import com.gm.gmrxjavaretrofit.R;
import com.gm.gmrxjavaretrofit.RecipyApplication;
import com.gm.gmrxjavaretrofit.base.BaseActivity;
import com.gm.gmrxjavaretrofit.domain.model.Recipy;
import com.gm.gmrxjavaretrofit.recipy.RecipyFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    public static final String TAG_SEARCH_FRAGMENT = "search_fragment";

    // injecting dependencies via Dagger
    @Inject
    Context context;

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    CompositeSubscription subscriptions;
    private RecipyFragment searchFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        setupToolbar();

        if (null == savedInstanceState) {
            searchFragment = RecipyFragment.newInstance();
            attachFragments();
        } else {
            searchFragment = (RecipyFragment) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH_FRAGMENT);
        }

        Timber.d("Main Activity Created");


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle(R.string.title_activity_main);
    }

    private void attachFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, searchFragment, TAG_SEARCH_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null == subscriptions || subscriptions.isUnsubscribed())
            subscriptions = new CompositeSubscription();

        subscriptions.addAll(
                searchFragment.characterObservable()
                        .subscribe(this::showCharacter),
                searchFragment.messageObservable()
                        .subscribe(this::showMessage),
                searchFragment.offlineObservable()
                        .subscribe(this::showOfflineMessage)
        );
    }

    public void showMessage(String message) {
        Timber.d("Showing Message: %s", message);

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showOfflineMessage(boolean isCritical) {
        Timber.d("Showing Offline Message");

        Snackbar.make(toolbar, "Your app is went to Offline", Snackbar.LENGTH_LONG)
                .setAction("go to online", v -> startActivity(new Intent(
                        Settings.ACTION_WIFI_SETTINGS)))
                .setActionTextColor(Color.GREEN)
                .show();
    }

    public void showCharacter(Recipy character) {
//        List<Recipy> recipies = new ArrayList<>();
//        recipies.add(character);
        Gson gson=new Gson();
        String s = gson.toJson(character);
       // startActivity(RecipyDescriptionActivity.newIntent(this, s));
    }

    @Override
    protected void onPause() {
        super.onPause();

        subscriptions.unsubscribe();
    }

    @Override
    protected void injectDependencies(RecipyApplication application, ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void releaseSubComponents(RecipyApplication application) {
        application.releaseRecipySubComponent();
    }

}
