package com.example.kotlinlogin.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Articles {
    @SerializedName("source")
    @Expose
    var source: Source? = null

    //    @SerializedName("name")
    //    @Expose
    //    private String name;
    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null

    //    public String getName() {
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null
}