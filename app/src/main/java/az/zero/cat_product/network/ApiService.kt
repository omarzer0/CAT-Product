package az.zero.cat_product.network

import az.zero.cat_product.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("products")
    suspend fun getAllProducts(): Response<ProductResponse>
}