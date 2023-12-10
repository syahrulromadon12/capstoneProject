package com.example.terrestrial.data.api

import com.example.terrestrial.utils.Constant.BASE_URL
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object ApiConfig {
//    fun getApiService(token: String): ApiService {
//        val loggingInterceptor =
//            if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) } else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }
//        val authInterceptor = Interceptor { chain ->
//            val req = chain.request()
//            val requestHeaders = req.newBuilder()
//                .addHeader("Authorization", "Bearer $token")
//                .build()
//            chain.proceed(requestHeaders)
//        }
//        val client = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor(authInterceptor)
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://d17a94b21d94b1.lhr.life/api")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//        return retrofit.create(ApiService::class.java)
//    }
//}

class ApiConfig {
    companion object{
        private val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://7a0ada740c1453.lhr.life/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val apiInstance = retrofit.create(ApiService::class.java)
    }
}
