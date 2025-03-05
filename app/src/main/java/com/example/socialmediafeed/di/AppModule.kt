package com.example.socialmediafeed.di

import com.example.socialmediafeed.data.remote.ApiService
import com.example.socialmediafeed.data.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
/**
 * @Author: Jijo
 * @Date: 05-03-2025
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val ACCESS_KEY = "ccVdquafya6OGlisLfgPJT8mZAuSWuV3TirP1vCuBxY"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val newRequest = original.newBuilder()
                    .header("Authorization", "Client-ID $ACCESS_KEY")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideImageRepository(apiService: ApiService): ImageRepository {
        return ImageRepository(apiService)
    }

}