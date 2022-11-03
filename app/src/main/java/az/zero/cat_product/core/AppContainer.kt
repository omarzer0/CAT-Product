package az.zero.cat_product.core

import android.content.Context
import androidx.room.Room
import az.zero.cat_product.BuildConfig
import az.zero.cat_product.data.db.AppDatabase
import az.zero.cat_product.data.network.ApiService
import az.zero.cat_product.data.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

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

    private val db = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .build()

    private val noteDao = db.getNoteDao()

    val repository = Repository(apiService, noteDao)

}