package az.zero.cat_product.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import az.zero.cat_product.data.db.helpers.AppTypeConverters
import az.zero.cat_product.data.models.Product

@Database(
    entities = [Product::class],
    version = 1
)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}