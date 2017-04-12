package com.gm.gmrxjavaretrofit.base;

/**
 * Name       : Gowtham
 * Created on : 20/10/2016.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */

public interface BasePresenter<T> {

    void bind(T view);

    void unbind();

}
