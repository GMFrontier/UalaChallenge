package com.frommetoyou.core.di

import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideDispatchers(): CoroutinesDispatcherProvider {
        return CoroutinesDispatcherProvider()
    }

}