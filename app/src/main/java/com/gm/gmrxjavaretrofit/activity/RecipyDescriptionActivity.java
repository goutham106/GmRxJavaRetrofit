package com.gm.gmrxjavaretrofit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gm.gmrxjavaretrofit.ApplicationComponent;
import com.gm.gmrxjavaretrofit.R;
import com.gm.gmrxjavaretrofit.RecipyApplication;
import com.gm.gmrxjavaretrofit.base.BaseActivity;
import com.gm.gmrxjavaretrofit.domain.model.Recipy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecipyDescriptionActivity extends BaseActivity {

    private static final String ARG_CHARACTER = "characterModel";

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<Recipy> recipyList;

    public static Intent newIntent(Context context, String character) {
        Intent intent = new Intent(context, RecipyDescriptionActivity.class);
        intent.putExtra(ARG_CHARACTER, character);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipy_description);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        if (null == getIntent() || null == getIntent().getExtras() || null == getIntent().getExtras().getSerializable(ARG_CHARACTER)) {
            finish();
            return;
        }

        // get args
        String s = getIntent().getStringExtra(ARG_CHARACTER);
        Gson gson = new Gson();
        recipyList = gson.fromJson(s, new TypeToken<List<Recipy>>() {
        }.getType());

        setupToolbar(recipyList.get(0).getTitle());

        Timber.d("Character Activity Created");


    }

    private void setupToolbar(String characterName) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle(characterName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void injectDependencies(RecipyApplication application, ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void releaseSubComponents(RecipyApplication application) {

    }

}
