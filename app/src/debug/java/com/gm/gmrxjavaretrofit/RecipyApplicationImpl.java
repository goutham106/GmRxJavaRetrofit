package com.gm.gmrxjavaretrofit;

import com.gm.glog.library.GLog;

import timber.log.Timber;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public class RecipyApplicationImpl extends RecipyApplication {
    @Override
    public void initApplication() {
        // initialize GmLog in debug version
        GLog.init(BuildConfig.DEBUG);

        // initialize Timber in debug version to log
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                // adding line number to logs
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }
}
