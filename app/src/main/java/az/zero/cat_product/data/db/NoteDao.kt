package az.zero.cat_product.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import az.zero.cat_product.data.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Product")
    fun getAllCachedProduct(): Flow<List<Product>>

    @Insert(onConflict = REPLACE)
    suspend fun saveProducts(products: List<Product>)

}