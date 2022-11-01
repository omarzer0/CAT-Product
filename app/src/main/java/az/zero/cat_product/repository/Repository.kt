package az.zero.cat_product.repository

import az.zero.cat_product.network.ApiService

class Repository(
    private val api: ApiService
) {
    suspend fun getAllProducts() = api.getAllProducts()

}