package com.frommetoyou.data.di

import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.data.data_source.MapApi
import com.frommetoyou.data.repository.MapRepositoryImpl
import com.frommetoyou.domain.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CityDataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        val loggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideMapApi(httpClient: OkHttpClient): MapApi {
        return Retrofit.Builder()
            .baseUrl("BuildConfig.BASE_URL")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MapApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCityRepository(
        api: MapApi,
        dispatcherProvider: CoroutinesDispatcherProvider
    ): MapRepository {
        return MapRepositoryImpl(api, dispatcherProvider)
    }
}