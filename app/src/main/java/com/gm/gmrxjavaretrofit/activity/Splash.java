package com.gm.gmrxjavaretrofit.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gm.gmrxjavaretrofit.ApplicationComponent;
import com.gm.gmrxjavaretrofit.R;
import com.gm.gmrxjavaretrofit.RecipyApplication;
import com.gm.gmrxjavaretrofit.base.BaseActivity;
import com.gm.gmrxjavaretrofit.util.AppConstants;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

public class Splash extends BaseActivity {

    // injecting dependencies via Dagger
    @Inject
    Context context;

    // Thread to process splash screen events
    private Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        // The thread to wait for splash screen events
        splashThread = new Thread() {
            @Override
            public void run() {

                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(AppConstants.SPLASH_TIMEOUT_SEC);
                    }
                } catch (InterruptedException ex) {
                    Timber.e(ex, "Splash thread interrupted!");
                }

                finish();

                Intent mainActivityIntent = new Intent();
                mainActivityIntent.setClass(context, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        };

        splashThread.start();

    }

    @Override
    protected void injectDependencies(RecipyApplication application, ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void releaseSubComponents(RecipyApplication application) {

    }
}
