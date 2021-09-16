package com.example.newsapi.api.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface webServices {
    @GET("sources")
      fun getSources():Call<SourceResponse>
    @GET("everything")
   fun getNews(@Query("sources")sources:String):Call<NewsResponse>
}