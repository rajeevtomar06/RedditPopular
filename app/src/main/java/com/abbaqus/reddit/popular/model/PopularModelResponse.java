package com.abbaqus.reddit.popular.model;

import com.google.gson.annotations.SerializedName;

public class PopularModelResponse {

    private String kind;
    @SerializedName("data")
    private PopularModel popularModel;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public PopularModel getPopularModel() {
        return popularModel;
    }

    public void setPopularModel(PopularModel popularModel) {
        this.popularModel = popularModel;
    }
}
