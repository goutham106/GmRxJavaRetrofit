package com.gm.gmrxjavaretrofit.recipy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gm.glog.library.GLog;
import com.gm.gmrxjavaretrofit.R;
import com.gm.gmrxjavaretrofit.RecipyApplication;
import com.gm.gmrxjavaretrofit.base.BaseFragment;
import com.gm.gmrxjavaretrofit.domain.model.Recipy;
import com.gm.gmrxjavaretrofit.domain.model.Result;
import com.gm.gmrxjavaretrofit.recipy.adapter.RecipyAdapter;
import com.gm.gmrxjavaretrofit.recipy.adapter.RecipyViewHolder;
import com.gm.grecyclerview.GmRecyclerView;
import com.gm.grecyclerview.SimpleCell;
import com.google.gson.Gson;
import com.mirhoseini.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import rx.Observable;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public class RecipyFragment extends BaseFragment implements recipyView {


    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;

    @Inject
    recipyPresenter presenter;

    // injecting views via ButterKnife
    @BindView(R.id.character)
    AutoCompleteTextView character;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.recyclerView)
    GmRecyclerView recyclerView;

    ProgressDialog progressDialog;

    PublishSubject<Recipy> notifyCharacter = PublishSubject.create();
    PublishSubject<String> notifyMessage = PublishSubject.create();
    PublishSubject<Boolean> notifyOffline = PublishSubject.create();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipyFragment() {
    }

    public static RecipyFragment newInstance() {
        RecipyFragment fragment = new RecipyFragment();
        return fragment;
    }

    @OnEditorAction(R.id.character)
    boolean onEditorAction(EditText v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            onShowClick(v);
            return true;
        }
        return false;
    }

    @OnClick(R.id.show)
    void onShowClick(View view) {
        character.setError(null);
        Utils.hideKeyboard(context, character);

        String query = character.getText().toString().trim();

        if (presenter.isQueryValid(query)) {
            recyclerView.removeAllCells();
            presenter.doSearch(Utils.isConnected(context), query);
        } else {
            character.setError("Error on Input");
            character.requestFocus();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter.bind(this);
    }

    @Override
    protected void injectDependencies(RecipyApplication application) {
        application
                .getRecipySubComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipy, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadRecipyData();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        presenter.unbind();
    }


    @Override
    public void showMessage(String message) {
        notifyMessage.onNext(message);
    }

    @Override
    public void showOfflineMessage(boolean isCritical) {
        notifyOffline.onNext(isCritical);
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable, "Error!");

        showRetryMessage(throwable);
    }

    @Override
    public void showProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Loading... \nPlease wait");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    @Override
    public void setRecipyData(Recipy recipyList) {
//        character.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, recipyList));
        Gson gson = new Gson();
        GLog.json("Fragment", gson.toJson(recipyList));


    }

    @Override
    public void showQueryError(Throwable throwable) {
        Timber.e(throwable, "Query error!");

        showRetryMessage(throwable);
    }

    @Override
    public void showCharacter(Recipy character) {
        ///////
        if (character.getResults().size() > 0) {
            List<Result> results = character.getResults();

            List<RecipyAdapter> recipyAdapters = new ArrayList<>();

            for(Result result:results) {
                RecipyAdapter adapter =new RecipyAdapter(result);

                adapter.setOnCellClickListener2(new SimpleCell.OnCellClickListener2<RecipyAdapter, RecipyViewHolder, Result>() {
                    @Override
                    public void onCellClicked(RecipyAdapter recipyAdapter, RecipyViewHolder viewHolder, Result item) {
                        Toast.makeText(context, "ShortPress -> " + item.getTitle(), Toast.LENGTH_SHORT).show();

                    }
                });

                adapter.setOnCellLongClickListener2(new SimpleCell.OnCellLongClickListener2<RecipyAdapter, RecipyViewHolder, Result>() {
                    @Override
                    public void onCellLongClicked(RecipyAdapter recipyAdapter, RecipyViewHolder viewHolder, Result item) {
                        Toast.makeText(context, "LongPress -> " + item.getIngredients(), Toast.LENGTH_SHORT).show();
                    }
                });
                recipyAdapters.add(adapter);
            }
            recyclerView.addCells(recipyAdapters);
        }

        ///////
        notifyCharacter.onNext(character);
    }

    @Override
    public void showRetryMessage(Throwable throwable) {
        Timber.e(throwable, "Retry error!");

        Snackbar.make(character, "Please Retry", Snackbar.LENGTH_LONG)
                .setAction("Retry", this::onShowClick)
                .show();
    }

    @Override
    public void showQueryNoResult() {
        showMessage("Entered query is Wrong");
    }

    @Override
    public void showServiceError(ApiResponseCodeException throwable) {
        Timber.e(throwable, "Service Error!");

        showRetryMessage(throwable);
    }

    public Observable<Recipy> characterObservable() {
        return notifyCharacter.asObservable();
    }

    public Observable<String> messageObservable() {
        return notifyMessage.asObservable();
    }

    public Observable<Boolean> offlineObservable() {
        return notifyOffline.asObservable();
    }


}
