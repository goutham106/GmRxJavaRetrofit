package com.gm.gmrxjavaretrofit.domain.client;

import com.gm.gmrxjavaretrofit.domain.model.Recipy;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Name       : Gowtham
 * Created on : 20/2/17.
 * Email      : goutham.gm11@gmail.com
 * GitHub     : https://github.com/goutham106
 */
public interface TestApi {
    String NAME = "q";
    String PAGE = "p";

    // http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3
//    i : comma delimited ingredients
//    q : normal search query
//    p : page
    @GET("api")
    Observable<Recipy> getRecipy(
            @Query(NAME) String query
            );

    @GET("api")
    Observable<Recipy> getRecipyBySearch(@Query(NAME) String query);

    @GET("api")
    Observable<Recipy> getRecipyBySearchAndPaginate(
            @Query(NAME) String query,
            @Query(PAGE) String pageNo
    );

}
