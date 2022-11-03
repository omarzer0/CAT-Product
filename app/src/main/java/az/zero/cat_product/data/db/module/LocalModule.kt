package az.zero.cat_product.data.db.module

import android.content.Context
import androidx.room.Room
import az.zero.cat_product.core.DATABASE_NAME
import az.zero.cat_product.data.db.AppDatabase
import az.zero.cat_product.data.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase = Room
        .databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun provideNoteDao(
        appDatabase: AppDatabase
    ): NoteDao = appDatabase.getNoteDao()


}