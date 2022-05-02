package com.githukudenis.androidtesting.di

import android.content.Context
import androidx.room.Room
import com.githukudenis.androidtesting.other.Constants
import com.githukudenis.androidtesting.data.local.ShoppingDao
import com.githukudenis.androidtesting.data.local.ShoppingItemDatabase
import com.githukudenis.androidtesting.data.remote.PixaBayApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ): ShoppingItemDatabase = Room.databaseBuilder(
        context,
        ShoppingItemDatabase::class.java,
        Constants.DATABASE_NAME
    )
        .build()


    @Singleton
    @Provides
    fun providePixaBayApi(): PixaBayApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(PixaBayApi::class.java)


    @Singleton
    @Provides
    fun provideShoppingItemDao(
        database: ShoppingItemDatabase
    ): ShoppingDao = database.shoppingItemDao()
}
