package com.abbaqus.reddit.popular.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

@Entity(tableName = "popular")
public class PopularModel {

    @ColumnInfo
    private  String title;
    @ColumnInfo
    private String subreddit;
    @ColumnInfo
    private String domain;
    @PrimaryKey(autoGenerate = true)
    private int key;

    @ColumnInfo
    private String id;
    @ColumnInfo
    private String url;
    @ColumnInfo
    private long score;
    @SerializedName("num_comments")
    @ColumnInfo
    private int commentsCount;
    @SerializedName("created_utc")
    @ColumnInfo
    private long createdDateTime;
    @ColumnInfo
    private String thumbnail;
    @ColumnInfo
    @SerializedName("thumbnail_width")
    private int thumbnailWidth;
    @ColumnInfo
    @SerializedName("thumbnail_height")
    private int thumbnailHeight;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommentsCountStr() {
        return String.valueOf(commentsCount);
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getScoreStr() {
        float scoreFloat = score/1000;
        return String.format("%.1fk",scoreFloat);
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public long getScore() {
        return score;
    }
}
