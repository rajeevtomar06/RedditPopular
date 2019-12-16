package com.abbaqus.reddit.data.remote;

import com.abbaqus.reddit.popular.model.PopularModelResponse;
import com.abbaqus.reddit.server.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedditPopularService {

    @GET("/r/popular/.json")
    Single<BaseResponse<PopularModelResponse>> getPopularList(@Query("limit") int limit,
                                                              @Query("after") String after);
}
