package com.rkpattanaik.social.di

import com.rkpattanaik.social.core.adapter.FlowResultCallAdapterFactory
import com.rkpattanaik.social.data.network.DummyJsonApi
import com.rkpattanaik.social.data.network.ReqResApi
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
        .addCallAdapterFactory(FlowResultCallAdapterFactory.create())

    @Singleton
    @Provides
    fun provideDummyJsonApi(retrofitBuilder: Builder): DummyJsonApi = retrofitBuilder
        .baseUrl("https://dummyjson.com/")
        .build()
        .create(DummyJsonApi::class.java)

    @Singleton
    @Provides
    fun provideReqResApi(retrofitBuilder: Builder): ReqResApi = retrofitBuilder
        .baseUrl("https://reqres.in/api/")
        .build()
        .create(ReqResApi::class.java)
}