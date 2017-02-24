package com.gm.gmrxjavaretrofit.recipy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.gm.gmrxjavaretrofit.R;
import com.gm.gmrxjavaretrofit.domain.model.Result;
import com.gm.grecyclerview.SimpleCell;
import com.gm.grecyclerview.Updatable;

import java.util.List;

/**
 * Name       : Gowtham
 * Created on : 21/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public class RecipyAdapter extends SimpleCell<Result,RecipyViewHolder> implements Updatable<Result> {

    public RecipyAdapter(Result item) {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.recipy_cell;
    }

    @NonNull
    @Override
    protected RecipyViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new RecipyViewHolder(cellView);
    }

    @Override
    protected void onBindViewHolder(RecipyViewHolder holder, int position, Context context, Object payload) {
        holder.onBind(context,getItem());
    }

    @Override
    protected long getItemId() {
        return 0;
    }

    @Override
    public boolean areContentsTheSame(Result newItem) {
        return getItem().getTitle().equals(newItem.getTitle());
    }

    @Override
    public Object getChangePayload(Result newItem) {
        return null;
    }
}
