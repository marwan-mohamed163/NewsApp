package com.example.newsapi.api

import android.util.Log
import com.example.newsapi.api.model.webServices
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {
    companion object{
        private var retrofit:Retrofit?=null;
        private fun getInstance():Retrofit{
            val loggingInterceptor = HttpLoggingInterceptor(object :
                HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e("api", message)
                }
            })
            val langInterceptor= object :Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    val url = request.url.newBuilder()
                        .addQueryParameter("language", "en").build()
                    request = request.newBuilder().url(url).build()
                    return chain.proceed(request)
                }
            }
            val apiKeyInterceptor= object :Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    val url = request.url.newBuilder()
                        .addQueryParameter("apiKey", "99a68241af5d4fedbc192a52dfde6c5c").build()
                    request = request.newBuilder().url(url).build()
                    return chain.proceed(request)
                }
            }
            if (retrofit==null){
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client =OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(langInterceptor)
                    .addInterceptor(apiKeyInterceptor)
                    .build()
                retrofit=Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofit!!;
        }
        fun getApis():webServices{
            return getInstance().create(webServices::class.java)
        }
    }
}