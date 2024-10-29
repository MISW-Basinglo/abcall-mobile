package co.uniandes.abcall.di

import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.AuthApi
import co.uniandes.abcall.networking.AuthInterceptor
import co.uniandes.abcall.networking.LoggerInterceptor
import co.uniandes.abcall.storage.LocalStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.90.128.236"
    //private const val BASE_URL = "http://34.49.98.208"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    @Named("Abcall")
    fun provideAbcallOkHttpClient(localStorage: LocalStorage, @Named("AuthApi") authApi: AuthApi): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(localStorage, authApi))
            .addInterceptor(LoggerInterceptor())
            .build()
    }

    @Provides
    @Singleton
    @Named("Auth")
    fun provideAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoggerInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideAbcallApi(gson: Gson, @Named("Abcall") okHttpClient: OkHttpClient): AbcallApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AbcallApi::class.java)
    }

    @Provides
    @Singleton
    @Named("AuthApi")
    fun provideAuthApi(gson: Gson, @Named("Auth") okHttpClient: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AuthApi::class.java)
    }

}
