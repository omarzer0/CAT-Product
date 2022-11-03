package az.zero.cat_product.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ProductResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)

@Entity
data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
) {
    val productImage
        get() = if (images.isEmpty()) "" else images[0]
}