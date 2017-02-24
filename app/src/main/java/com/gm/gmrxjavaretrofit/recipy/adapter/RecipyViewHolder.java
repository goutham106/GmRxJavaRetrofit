package com.gm.gmrxjavaretrofit.recipy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gm.glog.library.GLog;
import com.gm.gmrxjavaretrofit.R;
import com.gm.gmrxjavaretrofit.domain.model.Result;
import com.gm.grecyclerview.SimpleViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Name       : Gowtham
 * Created on : 21/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public class RecipyViewHolder extends SimpleViewHolder {

    @BindView(R.id.img_thumbnail)
    ImageView img_thumbnail;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_desc)
    TextView txt_desc;

    public RecipyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Context context, Result result) {
        try {
            txt_title.setText(result.getTitle());
            txt_desc.setText(result.getHref());

            if (!TextUtils.isEmpty(result.getThumbnail())) {
                Picasso.with(context)
                        .load(result.getThumbnail())
                        .fit()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(img_thumbnail);
            }else{
                Picasso.with(context)
                        .load(R.mipmap.ic_launcher)
                        .fit()
                        .into(img_thumbnail);
            }
        } catch (Exception e) {
            GLog.e(e.getMessage());
        }
    }
}
