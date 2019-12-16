package com.abbaqus.reddit.server;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse<T> {

   private  String kind;
   private Data<T> data;


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data<T> getData() {
        return data;
    }

    public void setData(Data<T> data) {
        this.data = data;
    }

    public static class Data<RS>{

       String modhash;
       @SerializedName("dist")
       int listCount;
       String after;
       String before;
       @SerializedName("children")
       List<RS> resultList;

        public String getModhash() {
            return modhash;
        }

        public void setModhash(String modhash) {
            this.modhash = modhash;
        }

        public int getListCount() {
            return listCount;
        }

        public void setListCount(int listCount) {
            this.listCount = listCount;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public List<RS> getResultList() {
            return resultList;
        }

        public void setResultList(List<RS> resultList) {
            this.resultList = resultList;
        }
    }

}
