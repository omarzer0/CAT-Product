package az.zero.cat_product.data.network

import az.zero.cat_product.data.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("products")
    suspend fun getAllProducts(): ProductResponse
}