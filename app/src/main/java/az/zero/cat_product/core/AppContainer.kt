package az.zero.cat_product.core

import az.zero.cat_product.BuildConfig
import az.zero.cat_product.network.ApiService
import az.zero.cat_product.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val client = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
    }.build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    val repository = Repository(apiService)

}