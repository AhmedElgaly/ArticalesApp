package com.example.articalesapp.Data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class dataresponse implements Parcelable{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sortBy")
    @Expose
    private String sortBy;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;
    public final static Parcelable.Creator<dataresponse> CREATOR = new Creator<dataresponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public dataresponse createFromParcel(Parcel in) {
            return new dataresponse(in);
        }

        public dataresponse[] newArray(int size) {
            return (new dataresponse[size]);
        }

    };

    protected dataresponse(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.sortBy = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.articles, (com.example.articalesapp.Data.models.Article.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public dataresponse() {
    }

    /**
     *
     * @param sortBy
     * @param source
     * @param articles
     * @param status
     */
    public dataresponse(String status, String source, String sortBy, List<Article> articles) {
        super();
        this.status = status;
        this.source = source;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(source);
        dest.writeValue(sortBy);
        dest.writeList(articles);
    }

    public int describeContents() {
        return 0;
    }
}
