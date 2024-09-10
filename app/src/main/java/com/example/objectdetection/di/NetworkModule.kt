package com.example.objectdetection.di

import com.example.data.network.DictionaryApi
import com.example.data.network.SheetApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideDictionaryApi(): com.example.data.network.DictionaryApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DICTIONARY_URL)
            .build()
            .create(com.example.data.network.DictionaryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSheetApi(): com.example.data.network.SheetApi {
        return Retrofit.Builder()
            .baseUrl(SHEET_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.data.network.SheetApi::class.java)
    }

    private const val SHEET_URL = "https://sheetdb.io/"
    private const val DICTIONARY_URL = "https://api.dictionaryapi.dev/"
}