package az.zero.cat_product.data.repository

import az.zero.cat_product.core.networkBoundResource
import az.zero.cat_product.data.db.NoteDao
import az.zero.cat_product.data.network.ApiService

class Repository(
    private val api: ApiService,
    private val dao: NoteDao
) {

    suspend fun getAllProducts() = networkBoundResource(
        query = { dao.getAllCachedProduct() },
        fetch = {
            api.getAllProducts()
        },
        saveFetchResult = {
            dao.saveProducts(it.products)
        }
    )

}