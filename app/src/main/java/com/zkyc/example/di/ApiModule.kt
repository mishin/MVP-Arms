package com.zkyc.example.di

import com.zkyc.example.model.http.LoginApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ActivityComponent::class)
object ApiModule {

    @Provides
    fun provideLoginApis(retrofit: Retrofit) = retrofit.create<LoginApis>()
}