package uz.turgunboyevjurabek.saxovat.model.di.networkModule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.turgunboyevjurabek.saxovat.model.network.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Network module of Dagger2 -hilt
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl():String="https://crm.ziyodev.uz/api/"

    @Provides
    @Singleton
    fun provideRetrofit2(string: String):Retrofit{
        val okHttpClient=OkHttpClient().newBuilder()
            .connectTimeout(60,TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(string)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }



}