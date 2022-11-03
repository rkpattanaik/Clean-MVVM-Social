package com.rkpattanaik.social.di

import com.rkpattanaik.social.core.retrofit.factory.FlowResultCallAdapterFactory
import com.rkpattanaik.social.data.network.service.DummyJsonApi
import com.rkpattanaik.social.data.network.service.ReqResApi
import com.rkpattanaik.social.data.network.model.error.DummyJsonApiError
import com.rkpattanaik.social.data.network.model.error.ReqResApiError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Builder = Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideDummyJsonApi(retrofitBuilder: Builder): DummyJsonApi = retrofitBuilder
        .baseUrl("https://dummyjson.com/")
        .addCallAdapterFactory(
            FlowResultCallAdapterFactory.create(
            defaultApiErrorClass = DummyJsonApiError::class,
            isAsyncByDefault = true
        ))
        .build()
        .create(DummyJsonApi::class.java)

    @Singleton
    @Provides
    fun provideReqResApi(retrofitBuilder: Builder): ReqResApi = retrofitBuilder
        .baseUrl("https://reqres.in/api/")
        .addCallAdapterFactory(
            FlowResultCallAdapterFactory.create(
            defaultApiErrorClass = ReqResApiError::class,
            isAsyncByDefault = true
        ))
        .build()
        .create(ReqResApi::class.java)
}